import { renderHook } from '@testing-library/react';
import { useWebSocket } from './useWebSocket';

describe('useWebSocket', () => {
  let wsMock;

  beforeEach(() => {
    wsMock = {
      send: jest.fn(),
      close: jest.fn(),
      onopen: null,
      onmessage: null,
      onerror: null,
    };
    global.WebSocket = jest.fn(() => wsMock);
    jest.spyOn(console, 'error').mockImplementation(() => {});
  });

  afterEach(() => {
    jest.restoreAllMocks();
  });

  test('не создаёт WebSocket, если нет userId', () => {
    const { result } = renderHook(() => useWebSocket(null, jest.fn()));
    expect(result.current).toBeNull();
    expect(global.WebSocket).not.toHaveBeenCalled();
  });

  test('создаёт WebSocket и обрабатывает события', () => {
    const onMessage = jest.fn();
    const { result, unmount } = renderHook(() => useWebSocket('user_1', onMessage));

    // Проверяем, что WebSocket создан
    expect(global.WebSocket).toHaveBeenCalledWith('ws://localhost:8080/ws');

    // Симулируем открытие соединения
    wsMock.onopen();
    expect(wsMock.send).toHaveBeenCalledWith('user_1');

    // Симулируем получение корректного JSON-сообщения
    wsMock.onmessage({ data: JSON.stringify({ metric: 'heart_rate', value: 80 }) });
    expect(onMessage).toHaveBeenCalledWith({ metric: 'heart_rate', value: 80 });

    // Симулируем получение некорректного JSON
    wsMock.onmessage({ data: 'invalid json' });
    expect(console.error).toHaveBeenCalledWith("Ошибка при разборе JSON:", 'invalid json');

    // Симулируем ошибку WebSocket
    wsMock.onerror();
    expect(console.error).toHaveBeenCalledWith("Ошибка WebSocket");

    // Проверяем возврат socketRef.current
    expect(result.current).toBe(wsMock);

    // Проверяем закрытие при размонтировании
    unmount();
    expect(wsMock.close).toHaveBeenCalled();
  });
});
