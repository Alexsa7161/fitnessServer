import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import AddRecordForm from './AddRecordForm';

describe('AddRecordForm', () => {
  beforeEach(() => {
    // Мокаем fetch глобально
    global.fetch = jest.fn();
  });

  afterEach(() => {
    jest.resetAllMocks();
  });

  test('рендерит все поля и кнопку', () => {
    render(<AddRecordForm />);
    expect(screen.getByLabelText(/User ID/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/Метрика/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/Значение/i)).toBeInTheDocument();
    expect(screen.getByRole('button', { name: /Добавить запись/i })).toBeInTheDocument();
  });

  test('показывает сообщение если поля пустые', () => {
    render(<AddRecordForm />);
    fireEvent.click(screen.getByRole('button', { name: /Добавить запись/i }));
    expect(screen.getByText(/Заполните все поля/i)).toBeInTheDocument();
  });

  test('успешное добавление записи', async () => {
    fetch.mockResolvedValueOnce({ ok: true });

    render(<AddRecordForm />);
    fireEvent.change(screen.getByLabelText(/User ID/i), { target: { value: 'user_1' } });
    fireEvent.change(screen.getByLabelText(/Метрика/i), { target: { value: 'heart_rate' } });
    fireEvent.change(screen.getByLabelText(/Значение/i), { target: { value: '75' } });

    fireEvent.click(screen.getByRole('button', { name: /Добавить запись/i }));

    await waitFor(() => {
      expect(screen.getByText(/Запись добавлена успешно/i)).toBeInTheDocument();
    });
  });

  test('ошибка сервера при добавлении', async () => {
    fetch.mockResolvedValueOnce({ ok: false });

    render(<AddRecordForm />);
    fireEvent.change(screen.getByLabelText(/User ID/i), { target: { value: 'user_1' } });
    fireEvent.change(screen.getByLabelText(/Метрика/i), { target: { value: 'heart_rate' } });
    fireEvent.change(screen.getByLabelText(/Значение/i), { target: { value: '75' } });

    fireEvent.click(screen.getByRole('button', { name: /Добавить запись/i }));

    await waitFor(() => {
      expect(screen.getByText(/Ошибка добавления записи/i)).toBeInTheDocument();
    });
  });

  test('ошибка сети при добавлении', async () => {
    fetch.mockRejectedValueOnce(new Error('Network Error'));

    render(<AddRecordForm />);
    fireEvent.change(screen.getByLabelText(/User ID/i), { target: { value: 'user_1' } });
    fireEvent.change(screen.getByLabelText(/Метрика/i), { target: { value: 'heart_rate' } });
    fireEvent.change(screen.getByLabelText(/Значение/i), { target: { value: '75' } });

    fireEvent.click(screen.getByRole('button', { name: /Добавить запись/i }));

    await waitFor(() => {
      expect(screen.getByText(/Ошибка сети при добавлении/i)).toBeInTheDocument();
    });
  });
});
