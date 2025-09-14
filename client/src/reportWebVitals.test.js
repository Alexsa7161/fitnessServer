import reportWebVitals from './reportWebVitals';
import * as webVitals from 'web-vitals';

// Мокаем все функции web-vitals
jest.mock('web-vitals', () => ({
  getCLS: jest.fn(),
  getFID: jest.fn(),
  getFCP: jest.fn(),
  getLCP: jest.fn(),
  getTTFB: jest.fn(),
}));

describe('reportWebVitals', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  test('не вызывает web-vitals, если аргумент не функция', () => {
    reportWebVitals();
    expect(webVitals.getCLS).not.toHaveBeenCalled();
    expect(webVitals.getFID).not.toHaveBeenCalled();
    expect(webVitals.getFCP).not.toHaveBeenCalled();
    expect(webVitals.getLCP).not.toHaveBeenCalled();
    expect(webVitals.getTTFB).not.toHaveBeenCalled();
  });

  test('вызывает все функции из web-vitals при передаче callback', () => {
    const callback = jest.fn();
    reportWebVitals(callback);

    expect(webVitals.getCLS).toHaveBeenCalledWith(callback);
    expect(webVitals.getFID).toHaveBeenCalledWith(callback);
    expect(webVitals.getFCP).toHaveBeenCalledWith(callback);
    expect(webVitals.getLCP).toHaveBeenCalledWith(callback);
    expect(webVitals.getTTFB).toHaveBeenCalledWith(callback);
  });
});
