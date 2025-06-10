const BASE_URL = 'http://localhost:8080/api';

export const fetchHistory = async (userId, startTimestamp, endTimestamp) => {
    // Если startTimestamp или endTimestamp не заданы, подставляем дефолтные значения
    const now = Date.now();
    const start = startTimestamp ?? (now - 365 * 24 * 60 * 60 * 1000);  // год назад
    const end = endTimestamp ?? now;

    const response = await fetch(
        `${BASE_URL}/history?userId=${encodeURIComponent(userId)}&startTimestamp=${start}&endTimestamp=${end}`
    );
    if (!response.ok) {
        throw new Error("Ошибка при получении истории");
    }
    return await response.json();
};


export const addRecord = (record) =>
    fetch(`${BASE_URL}/data`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(record),
    });

export const deleteRecord = (id) =>
    fetch(`${BASE_URL}/data/${id}`, { method: 'DELETE' });

export const updateRecord = (id, value) =>
    fetch(`${BASE_URL}/data/${id}`, {
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ value }),
    });
