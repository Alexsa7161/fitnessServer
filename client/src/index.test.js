import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import reportWebVitals from './reportWebVitals';

// Мокаем reportWebVitals, но чтобы отслеживать вызов
jest.mock('./reportWebVitals', () => jest.fn());

describe('index.js', () => {
  let div;

  beforeEach(() => {
    div = document.createElement('div');
    div.id = 'root';
    document.body.appendChild(div);
  });

  afterEach(() => {
    document.body.removeChild(div);
    jest.clearAllMocks();
  });

  test('рендерит App и вызывает reportWebVitals', () => {
    const root = ReactDOM.createRoot(div);

    root.render(
      <React.StrictMode>
        <App />
      </React.StrictMode>
    );

    // Проверяем, что reportWebVitals был вызван
    expect(reportWebVitals).toHaveBeenCalled();
  });
});
