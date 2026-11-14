document.addEventListener("DOMContentLoaded", () => {
    const currencyTable = document.querySelector(".currencies-table tbody");
    const ratesTable = document.querySelector(".exchange-rates-table tbody");
    const toast = bootstrap.Toast.getOrCreateInstance(document.querySelector("#api-toast"));
    const editModal = bootstrap.Modal.getOrCreateInstance(document.querySelector("#edit-exchange-rate-modal"));

    function showMessage(message, title = "Ошибка") {
        document.querySelector("#api-toast-title").textContent = title;
        document.querySelector("#api-toast-message").textContent = message;
        toast.show();
    }

    async function request(url, options = {}) {
        const response = await fetch(url, {
            ...options,
            headers: {
                ...(options.body ? { "Content-Type": "application/json" } : {}),
                ...options.headers
            }
        });

        if (!response.ok) {
            let message = `Ошибка сервера (${response.status})`;
            try {
                const error = await response.json();
                if (error.message) message = error.message;
            } catch {
                // Keep the HTTP status message when the server response is not JSON.
            }
            throw new Error(message);
        }

        if (response.status === 204 || response.headers.get("content-length") === "0") return null;
        const body = await response.text();
        return body ? JSON.parse(body) : null;
    }

    function fillCurrencySelect(select, currencies) {
        const currentValue = select.value;
        select.replaceChildren();

        currencies.forEach((currency) => {
            const option = document.createElement("option");
            option.value = currency.code;
            option.textContent = `${currency.code} — ${currency.fullName}`;
            select.append(option);
        });

        if (currencies.some((currency) => currency.code === currentValue)) {
            select.value = currentValue;
        }
    }

    async function loadCurrencies() {
        try {
            const currencies = await request("/currencies");
            currencyTable.replaceChildren();

            if (currencies.length === 0) {
                currencyTable.innerHTML = '<tr><td colspan="3" class="empty-state">Валют пока нет. Добавьте первую валюту ниже.</td></tr>';
            } else {
                currencies.forEach((currency) => {
                    const row = document.createElement("tr");
                    [currency.code, currency.fullName, currency.sign].forEach((value) => {
                        const cell = document.createElement("td");
                        cell.textContent = value;
                        row.append(cell);
                    });
                    currencyTable.append(row);
                });
            }

            [
                "#new-rate-base-currency",
                "#new-rate-target-currency",
                "#convert-base-currency",
                "#convert-target-currency",
                "#convert-bridge-currency"
            ].forEach((selector) => fillCurrencySelect(document.querySelector(selector), currencies));
        } catch (error) {
            currencyTable.innerHTML = '<tr><td colspan="3" class="empty-state">Не удалось загрузить валюты.</td></tr>';
            showMessage(error.message);
        }
    }

    async function loadExchangeRates() {
        try {
            const rates = await request("/exchange-rates");
            ratesTable.replaceChildren();

            if (rates.length === 0) {
                ratesTable.innerHTML = '<tr><td colspan="3" class="empty-state">Курсов пока нет. Добавьте первый курс ниже.</td></tr>';
                return;
            }

            rates.forEach((rate) => {
                const pair = `${rate.baseCurrency.code}${rate.targetCurrency.code}`;
                const row = document.createElement("tr");
                const pairCell = document.createElement("td");
                const rateCell = document.createElement("td");
                const actionCell = document.createElement("td");
                const editButton = document.createElement("button");

                pairCell.textContent = `${rate.baseCurrency.code} / ${rate.targetCurrency.code}`;
                rateCell.textContent = rate.rate;
                editButton.type = "button";
                editButton.className = "btn btn-sm btn-light";
                editButton.dataset.pair = pair;
                editButton.dataset.rate = rate.rate;
                editButton.dataset.base = rate.baseCurrency.code;
                editButton.dataset.target = rate.targetCurrency.code;
                editButton.textContent = "Изменить";
                actionCell.append(editButton);
                row.append(pairCell, rateCell, actionCell);
                ratesTable.append(row);
            });
        } catch (error) {
            ratesTable.innerHTML = '<tr><td colspan="3" class="empty-state">Не удалось загрузить курсы.</td></tr>';
            showMessage(error.message);
        }
    }

    document.querySelector("#add-currency").addEventListener("submit", async (event) => {
        event.preventDefault();
        const form = event.currentTarget;
        const data = Object.fromEntries(new FormData(form));

        try {
            await request("/currencies", {
                method: "POST",
                body: JSON.stringify(data)
            });
            form.reset();
            await loadCurrencies();
            showMessage("Валюта успешно добавлена.", "Готово");
        } catch (error) {
            showMessage(error.message);
        }
    });

    document.querySelector("#add-exchange-rate").addEventListener("submit", async (event) => {
        event.preventDefault();
        const form = event.currentTarget;
        const data = Object.fromEntries(new FormData(form));

        if (data.baseCurrencyCode === data.targetCurrencyCode) {
            showMessage("Выберите разные базовую и целевую валюты.");
            return;
        }

        try {
            await request("/exchange-rates", {
                method: "POST",
                body: JSON.stringify({ ...data, rate: Number(data.rate) })
            });
            form.reset();
            await loadExchangeRates();
            showMessage("Курс успешно добавлен.", "Готово");
        } catch (error) {
            showMessage(error.message);
        }
    });

    ratesTable.addEventListener("click", (event) => {
        const button = event.target.closest("button[data-pair]");
        if (!button) return;

        document.querySelector("#edit-rate-title").textContent =
            `Курс ${button.dataset.base} / ${button.dataset.target}`;
        document.querySelector("#edit-rate-pair").value = button.dataset.pair;
        document.querySelector("#exchange-rate-input").value = button.dataset.rate;
        editModal.show();
    });

    document.querySelector("#edit-exchange-rate").addEventListener("submit", async (event) => {
        event.preventDefault();
        const pair = document.querySelector("#edit-rate-pair").value;
        const rate = Number(document.querySelector("#exchange-rate-input").value);

        try {
            await request(`/exchange-rates/${encodeURIComponent(pair)}`, {
                method: "PATCH",
                body: JSON.stringify({ rate })
            });
            editModal.hide();
            await loadExchangeRates();
            showMessage("Курс успешно обновлён.", "Готово");
        } catch (error) {
            showMessage(error.message);
        }
    });

    document.querySelector("#convert").addEventListener("submit", async (event) => {
        event.preventDefault();
        const baseCode = document.querySelector("#convert-base-currency").value;
        const targetCode = document.querySelector("#convert-target-currency").value;
        const bridgeCode = document.querySelector("#convert-bridge-currency").value;
        const amount = document.querySelector("#convert-amount").value;
        const result = document.querySelector("#convert-result");

        if (baseCode === targetCode) {
            showMessage("Выберите разные исходную и целевую валюты.");
            return;
        }

        const params = new URLSearchParams({ baseCode, targetCode, bridgeCode, amount });
        try {
            const exchange = await request(`/exchange?${params}`);
            const converted = new Intl.NumberFormat("ru-RU", {
                maximumFractionDigits: 6
            }).format(exchange.convertedAmount);
            result.textContent =
                `${amount} ${exchange.baseCurrency.code} = ${converted} ${exchange.targetCurrency.code}`;
            result.classList.add("is-visible");
        } catch (error) {
            result.textContent = "";
            result.classList.remove("is-visible");
            showMessage(error.message);
        }
    });

    loadCurrencies();
    loadExchangeRates();
});
