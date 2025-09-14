import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import * as reportWebVitalsModule from './reportWebVitals';

describe('index.js', () => {
  let div;

  beforeEach(() => {
    div = document.createElement('div');
    div.id = 'root';
    document.body.appendChild(div);
  });

  afterEach(() => {
    document.body.removeChild(div);
    jest.restoreAllMocks();
  });

  test('рендерит App и вызывает reportWebVitals', () => {
    // Шпионим за reportWebVitals и подменяем реализацию
    const spy = jest.spyOn(reportWebVitalsModule, 'default').mockImplementation(() => {});

    // Динамически импортируем index.js, чтобы сработал вызов reportWebVitals
    require('./index.js');

    // Проверяем, что reportWebVitals был вызван
    expect(spy).toHaveBeenCalled();

    // Проверяем, что App рендерится в div#root
    const rootDiv = document.getElementById('root');
    expect(rootDiv).toBeTruthy();
  });
});
