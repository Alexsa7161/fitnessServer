import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import App from './App';
import * as api from './services/api';

jest.mock('./services/api');

describe('App', () => {
  beforeEach(() => {
    api.fetchHistory.mockReset();
  });

  test('рендерит все компоненты', () => {
    render(<App />);
    // Ищем по id вместо data-testid
    expect(document.getElementById('metrics')).toBeInTheDocument();
    expect(screen.getByText('📜 История')).toBeInTheDocument();
    expect(screen.getByText(/Добавить запись/i)).toBeInTheDocument();
    expect(screen.getByText(/Подключиться/i)).toBeInTheDocument();
  });

  test('onConnect обновляет metrics', async () => {
    const fakeHistory = [
      { metric: 'heart_rate', value: 80, timestamp: '0' },
      { metric: 'steps', value: 1000, timestamp: '0' },
    ];
    api.fetchHistory.mockResolvedValue(fakeHistory);

    render(<App />);

    const connectBtn = screen.getByText(/Подключиться/i);
    fireEvent.click(connectBtn);

    await waitFor(() => {
      expect(api.fetchHistory).toHaveBeenCalled();
      // Проверяем, что metrics div содержит элементы
      const metricsDiv = document.getElementById('metrics');
      expect(metricsDiv).toBeInTheDocument();
      expect(metricsDiv.children.length).toBeGreaterThan(0);
    });
  });
});
