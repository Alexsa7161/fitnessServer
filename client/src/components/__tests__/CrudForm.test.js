// src/components/__tests__/CrudForm.test.jsx
import CrudForm from '../CrudForm';
import * as api from '../../services/api';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
jest.mock('../../services/api');

test('CrudForm валидирует и вызывает addRecord', async () => {
    api.addRecord.mockResolvedValue({});

    render(<CrudForm />);

    fireEvent.change(screen.getByPlaceholderText('User ID'), { target: { value: 'user1' } });
    fireEvent.change(screen.getByPlaceholderText('Метрика'), { target: { value: 'steps' } });
    fireEvent.change(screen.getByPlaceholderText('Значение'), { target: { value: '42' } });

    window.alert = jest.fn();

    fireEvent.click(screen.getByText('Добавить'));

    await waitFor(() => {
        expect(api.addRecord).toHaveBeenCalledWith(expect.objectContaining({
            userId: 'user1',
            metric: 'steps',
            value: 42,
        }));
        expect(window.alert).toHaveBeenCalledWith("✅ Запись добавлена!");
    });
});