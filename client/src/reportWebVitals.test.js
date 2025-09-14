import reportWebVitals from './reportWebVitals';

jest.mock('web-vitals', () => ({
  getCLS: jest.fn(),
  getFID: jest.fn(),
  getFCP: jest.fn(),
  getLCP: jest.fn(),
  getTTFB: jest.fn(),
}));

describe('reportWebVitals', () => {
  afterEach(() => {
    jest.clearAllMocks();
  });

  test('не вызывает web-vitals, если аргумент не функция', async () => {
    expect(() => reportWebVitals()).not.toThrow();
    expect(require('web-vitals').getCLS).not.toHaveBeenCalled();
  });

  test('вызывает все функции из web-vitals при передаче callback', async () => {
    const callback = jest.fn();
    await reportWebVitals(callback);

    // Импорт динамический, нужно дождаться Promise
    const { getCLS, getFID, getFCP, getLCP, getTTFB } = require('web-vitals');

    // Проверяем, что каждая функция вызвана с callback
    expect(getCLS).toHaveBeenCalledWith(callback);
    expect(getFID).toHaveBeenCalledWith(callback);
    expect(getFCP).toHaveBeenCalledWith(callback);
    expect(getLCP).toHaveBeenCalledWith(callback);
    expect(getTTFB).toHaveBeenCalledWith(callback);
  });
});
