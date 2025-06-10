// src/components/__tests__/Metrics.test.jsx
import { render, screen } from '@testing-library/react';
import Metrics from '../Metrics';

test('Metrics отображает метрики', () => {
    const metrics = {
        steps: { timestamp: Date.now().toString(), value: 100 },
        'heart-rate': { timestamp: Date.now().toString(), value: 80 },
    };

    render(<Metrics metrics={metrics} />);

    expect(screen.getByText(/steps/)).toBeInTheDocument();
    expect(screen.getByText(/Значение: 100/)).toBeInTheDocument();
    expect(screen.getByText(/heart-rate/)).toBeInTheDocument();
});
