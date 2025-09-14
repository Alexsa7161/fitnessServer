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
    // Шпионим за reportWebVitals
    const spy = jest.spyOn(reportWebVitalsModule, 'default').mockImplementation(() => {});

    // Импорт index.js динамически, чтобы вызов reportWebVitals сработал
    const indexModule = require('./index.js');

    expect(spy).toHaveBeenCalled();
  });
});
