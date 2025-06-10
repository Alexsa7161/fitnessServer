// src/services/api.test.js
import * as api from './api';

beforeEach(() => {
    global.fetch = jest.fn();
});
afterEach(() => {
    jest.resetAllMocks();
});

test('fetchHistory делает правильный запрос и возвращает json', async () => {
    const mockResponse = [{ id: 1 }];
    global.fetch.mockResolvedValue({ ok: true, json: () => Promise.resolve(mockResponse) });

    const data = await api.fetchHistory('user1', 1000, 2000);
    expect(global.fetch).toHaveBeenCalledWith(
        'http://localhost:9090/api/history?userId=user1&startTimestamp=1000&endTimestamp=2000'
    );
    expect(data).toEqual(mockResponse);
});

test('addRecord делает POST запрос', async () => {
    global.fetch.mockResolvedValue({});
    const record = { userId: 'user1', metric: 'steps', value: 100, timestamp: '123' };

    await api.addRecord(record);
    expect(global.fetch).toHaveBeenCalledWith(
        'http://localhost:9090/api/data',
        expect.objectContaining({
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(record),
        })
    );
});

test('deleteRecord делает DELETE запрос', async () => {
    global.fetch.mockResolvedValue({});
    await api.deleteRecord(123);
    expect(global.fetch).toHaveBeenCalledWith('http://localhost:9090/api/data/123', { method: 'DELETE' });
});

test('updateRecord делает PATCH запрос', async () => {
    global.fetch.mockResolvedValue({});
    await api.updateRecord(123, 42);
    expect(global.fetch).toHaveBeenCalledWith(
        'http://localhost:8080/api/data/123',
        expect.objectContaining({
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ value: 42 }),
        })
    );
});
