// src/components/__tests__/Header.test.jsx
import { render, screen, fireEvent } from '@testing-library/react';
import Header from '../Header';

test('Header отображает input и кнопку, корректно вызывает колбэки', () => {
    const setUserId = jest.fn();
    const onConnect = jest.fn();

    render(<Header userId="user_0" setUserId={setUserId} onConnect={onConnect} />);

    const input = screen.getByPlaceholderText('user_0');
    fireEvent.change(input, { target: { value: 'new_user' } });
    expect(setUserId).toHaveBeenCalledWith('new_user');

    const button = screen.getByText('Подключиться');
    fireEvent.click(button);
    expect(onConnect).toHaveBeenCalled();
});
