import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';

// Мокаем reportWebVitals, чтобы не вызывал реальные функции
jest.mock('./reportWebVitals', () => jest.fn());

describe('index.js', () => {
  test('рендерит App без ошибок', () => {
    // Создаём div для рендера (как document.getElementById('root'))
    const div = document.createElement('div');
    div.id = 'root';
    document.body.appendChild(div);

    const root = ReactDOM.createRoot(div);
    expect(() => {
      root.render(
        <React.StrictMode>
          <App />
        </React.StrictMode>
      );
    }).not.toThrow();
  });
});
