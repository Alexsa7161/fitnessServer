import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import AddRecordForm from './AddRecordForm';

// Мокаем fetch
beforeEach(() => {
  global.fetch = jest.fn();
});

afterEach(() => {
  jest.resetAllMocks();
});

describe('AddRecordForm', () => {
  test('показывает сообщение об ошибке, если поля пустые', () => {
    render(<AddRecordForm />);
    fireEvent.click(screen.getByText('Добавить запись'));
    expect(screen.getByText('❗ Заполните все поля для добавления!')).toBeInTheDocument();
  });

  test('успешная отправка данных', async () => {
    fetch.mockResolvedValueOnce({ ok: true });

    render(<AddRecordForm />);
    fireEvent.change(screen.getByPlaceholderText('user_0'), { target: { value: 'user1' } });
    fireEvent.change(screen.getByPlaceholderText('heart_rate'), { target: { value: 'pulse' } });
    fireEvent.change(screen.getByPlaceholderText('85'), { target: { value: '90' } });

    fireEvent.click(screen.getByText('Добавить запись'));

    await waitFor(() => {
      expect(fetch).toHaveBeenCalledWith('/api/data', expect.objectContaining({
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
      }));
      expect(screen.getByText('✅ Запись добавлена успешно!')).toBeInTheDocument();
    });
  });

  test('ошибка при добавлении записи', async () => {
    fetch.mockResolvedValueOnce({ ok: false });

    render(<AddRecordForm />);
    fireEvent.change(screen.getByPlaceholderText('user_0'), { target: { value: 'user1' } });
    fireEvent.change(screen.getByPlaceholderText('heart_rate'), { target: { value: 'pulse' } });
    fireEvent.change(screen.getByPlaceholderText('85'), { target: { value: '90' } });

    fireEvent.click(screen.getByText('Добавить запись'));

    await waitFor(() => {
      expect(screen.getByText('❗ Ошибка добавления записи.')).toBeInTheDocument();
    });
  });

  test('ошибка сети при fetch', async () => {
    fetch.mockRejectedValueOnce(new Error('Network Error'));

    render(<AddRecordForm />);
    fireEvent.change(screen.getByPlaceholderText('user_0'), { target: { value: 'user1' } });
    fireEvent.change(screen.getByPlaceholderText('heart_rate'), { target: { value: 'pulse' } });
    fireEvent.change(screen.getByPlaceholderText('85'), { target: { value: '90' } });

    fireEvent.click(screen.getByText('Добавить запись'));

    await waitFor(() => {
      expect(screen.getByText('❗ Ошибка сети при добавлении.')).toBeInTheDocument();
    });
  });
});
