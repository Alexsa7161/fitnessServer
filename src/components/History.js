import { useState } from 'react';
import { fetchHistory, deleteRecord, updateRecord } from '../services/api';

export default function History({ userId }) {
    const [visible, setVisible] = useState(false);
    const [history, setHistory] = useState([]);
    const [startTime, setStartTime] = useState('');
    const [endTime, setEndTime] = useState('');

    const load = () => {
        if (!startTime || !endTime) return;
        fetchHistory(userId, new Date(startTime).getTime(), new Date(endTime).getTime())
            .then(setHistory);
    };

    const handleDelete = (id) => {
        deleteRecord(id).then(load);
    };

    const handleUpdate = (id, newValue) => {
        updateRecord(id, parseFloat(newValue)).then(load);
    };

    return (
        <div>
            <button onClick={() => setVisible(!visible)} className="mt-4">📜 История</button>
            {visible && (
                <div className="mt-4">
                    <h2>История</h2>
                    <input type="datetime-local" value={startTime} onChange={(e) => setStartTime(e.target.value)} />
                    <input type="datetime-local" value={endTime} onChange={(e) => setEndTime(e.target.value)} />
                    <button onClick={load}>Загрузить</button>

                    <div className="mt-4">
                        {history.length === 0 ? (
                            <p>Нет данных</p>
                        ) : history.map(entry => (
                            <div key={entry.id} className="history-item">
                                <strong>{entry.metric}</strong>: {entry.value} <br />
                                <small>{new Date(Number(entry.timestamp)).toLocaleString()}</small>
                                <div className="history-controls mt-2">
                                    <button onClick={() => handleDelete(entry.id)}>Удалить</button>
                                    <EditField entry={entry} onConfirm={handleUpdate} />
                                </div>
                            </div>
                        ))}
                    </div>
                </div>
            )}
        </div>
    );
}

function EditField({ entry, onConfirm }) {
    const [editing, setEditing] = useState(false);
    const [value, setValue] = useState(entry.value);

    return editing ? (
        <div>
            <input type="number" value={value} onChange={(e) => setValue(e.target.value)} />
            <button onClick={() => { onConfirm(entry.id, value); setEditing(false); }}>Принять</button>
        </div>
    ) : (
        <button onClick={() => setEditing(true)}>Изменить</button>
    );
}
