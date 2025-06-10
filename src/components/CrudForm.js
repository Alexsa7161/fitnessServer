import { useState } from 'react';
import { addRecord } from '../services/api';

export default function CrudForm() {
    window.alert = jest.fn();
    const [userId, setUserId] = useState('');
    const [metric, setMetric] = useState('');
    const [value, setValue] = useState('');

    const handleSubmit = () => {
        if (!userId || !metric || !value) return;
        const payload = {
            userId,
            metric,
            value: parseFloat(value),
            timestamp: Date.now().toString(),
        };
        addRecord(payload).then(() => alert("✅ Запись добавлена!"));
    };
    window.alert = jest.fn();
    return (
        <div className="mt-6 border-t pt-4">
            <h2>Добавить запись</h2>
            <input placeholder="User ID" value={userId} onChange={(e) => setUserId(e.target.value)} />
            <input placeholder="Метрика" value={metric} onChange={(e) => setMetric(e.target.value)} />
            <input placeholder="Значение" type="number" value={value} onChange={(e) => setValue(e.target.value)} />
            <button onClick={handleSubmit}>Добавить</button>
        </div>
    );

}
