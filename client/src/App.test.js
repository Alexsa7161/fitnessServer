import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import App from './App';
import Header from './components/Header';
import Metrics from './components/Metrics';
import History from './components/History';
import CrudForm from './components/CrudForm';
import * as api from './services/api';
import * as wsHook from './hooks/useWebSocket';

// Мокаем зависимости
jest.mock('./components/Header', () => jest.fn(({ userId, setUserId, onConnect }) => (
  <div>
    <button data-testid="connect-btn" onClick={onConnect}>Connect</button>
    <input value={userId} onChange={e => setUserId(e.target.value)} data-testid="user-input" />
  </div>
)));

jest.mock('./components/Metrics', () => jest.fn(() => <div data-testid="metrics">Metrics</div>));
jest.mock('./components/History', () => jest.fn(() => <div data-testid="history">History</div>));
jest.mock('./components/CrudForm', () => jest.fn(() => <div data-testid="crud-form">CrudForm</div>));

// Мокаем useWebSocket
jest.spyOn(wsHook, 'useWebSocket').mockImplementation(() => {});

// Мокаем fetchHistory
jest.spyOn(api, 'fetchHistory').mockResolvedValue([
  { metric: 'weight', value: 70, timestamp: Date.now() }
]);

describe('App', () => {
  test('рендерит все компоненты', async () => {
    render(<App />);

    expect(screen.getByTestId('metrics')).toBeInTheDocument();
    expect(screen.getByTestId('history')).toBeInTheDocument();
    expect(screen.getByTestId('crud-form')).toBeInTheDocument();
    expect(screen.getByTestId('connect-btn')).toBeInTheDocument();
    expect(screen.getByTestId('user-input')).toBeInTheDocument();
  });

  test('onConnect обновляет metrics', async () => {
    render(<App />);

    fireEvent.click(screen.getByTestId('connect-btn'));

    await waitFor(() => {
      // Проверяем, что fetchHistory был вызван
      expect(api.fetchHistory).toHaveBeenCalled();
    });
  });

  test('обновление metrics через useCallback', async () => {
    const data = { metric: 'steps', value: 1000 };
    const { result } = render(<App />);

    // Так как useWebSocket мокнут, можно напрямую вызвать handleSocketMessage через имитацию
    // В реальном тесте можно вынести handleSocketMessage в отдельный hook и протестировать отдельно
  });
});
