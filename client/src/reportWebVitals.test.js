import reportWebVitals from './reportWebVitals';

describe('reportWebVitals', () => {
  const webVitalsMock = {
    getCLS: jest.fn(),
    getFID: jest.fn(),
    getFCP: jest.fn(),
    getLCP: jest.fn(),
    getTTFB: jest.fn(),
  };

  beforeEach(() => {
    jest.resetAllMocks();

    // Мокаем динамический импорт
    jest.spyOn(global, 'import').mockImplementation(() => Promise.resolve(webVitalsMock));
  });

  afterEach(() => {
    jest.restoreAllMocks();
  });

  test('не вызывает web-vitals, если аргумент не функция', async () => {
    await reportWebVitals(); // без аргумента
    expect(webVitalsMock.getCLS).not.toHaveBeenCalled();
    expect(webVitalsMock.getFID).not.toHaveBeenCalled();
    expect(webVitalsMock.getFCP).not.toHaveBeenCalled();
    expect(webVitalsMock.getLCP).not.toHaveBeenCalled();
    expect(webVitalsMock.getTTFB).not.toHaveBeenCalled();
  });

  test('вызывает все функции из web-vitals при передаче callback', async () => {
    const callback = jest.fn();
    await reportWebVitals(callback);

    expect(webVitalsMock.getCLS).toHaveBeenCalledWith(callback);
    expect(webVitalsMock.getFID).toHaveBeenCalledWith(callback);
    expect(webVitalsMock.getFCP).toHaveBeenCalledWith(callback);
    expect(webVitalsMock.getLCP).toHaveBeenCalledWith(callback);
    expect(webVitalsMock.getTTFB).toHaveBeenCalledWith(callback);
  });
});
