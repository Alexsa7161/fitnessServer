// reportWebVitals.test.js
import reportWebVitals from './reportWebVitals';

jest.mock('web-vitals', () => ({
  getCLS: jest.fn(),
  getFID: jest.fn(),
  getFCP: jest.fn(),
  getLCP: jest.fn(),
  getTTFB: jest.fn(),
}));

describe('reportWebVitals', () => {
  const webVitals = require('web-vitals');

  beforeEach(() => {
    jest.clearAllMocks();
  });

  test('не вызывает web-vitals, если аргумент не функция', async () => {
    await reportWebVitals(); // без аргумента
    expect(webVitals.getCLS).not.toHaveBeenCalled();
    expect(webVitals.getFID).not.toHaveBeenCalled();
    expect(webVitals.getFCP).not.toHaveBeenCalled();
    expect(webVitals.getLCP).not.toHaveBeenCalled();
    expect(webVitals.getTTFB).not.toHaveBeenCalled();
  });

  test('вызывает все функции из web-vitals при передаче callback', async () => {
    const callback = jest.fn();

    // подменяем динамический import на возврат промиса с моками
    await reportWebVitals(callback);

    // Проверяем, что каждая функция была вызвана с callback
    expect(webVitals.getCLS).toHaveBeenCalledWith(callback);
    expect(webVitals.getFID).toHaveBeenCalledWith(callback);
    expect(webVitals.getFCP).toHaveBeenCalledWith(callback);
    expect(webVitals.getLCP).toHaveBeenCalledWith(callback);
    expect(webVitals.getTTFB).toHaveBeenCalledWith(callback);
  });
});
