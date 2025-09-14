import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import App from './App';
import * as api from './services/api';

// Мокаем fetchHistory
jest.mock('./services/api', () => ({
  fetchHistory: jest.fn(),
}));

describe('App', () => {
  test('рендерит все компоненты', () => {
    render(<App />);
    expect(screen.getByTestId('metrics')).toBeInTheDocument();
    expect(screen.getByTestId('history')).toBeInTheDocument();
    expect(screen.getByTestId('crud-form')).toBeInTheDocument();
    expect(screen.getByTestId('connect-btn')).toBeInTheDocument();
  });

  test('onConnect обновляет metrics', async () => {
    const fakeHistory = [
      { metric: 'heart_rate', value: 80, timestamp: '123' },
      { metric: 'steps', value: 1000, timestamp: '124' },
    ];
    api.fetchHistory.mockResolvedValueOnce(fakeHistory);

    render(<App />);
    fireEvent.click(screen.getByTestId('connect-btn'));

    await waitFor(() => {
      expect(api.fetchHistory).toHaveBeenCalled();
      expect(screen.getByTestId('metrics')).toBeInTheDocument();
    });
  });
});
