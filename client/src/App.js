import { useState, useCallback } from 'react';
import Header from './components/Header';
import Metrics from './components/Metrics';
import History from './components/History';
import CrudForm from './components/CrudForm';
import { useWebSocket } from './hooks/useWebSocket';
import { fetchHistory } from './services/api';

function App() {
    const [userId, setUserId] = useState('');
    const [metrics, setMetrics] = useState({});

    const handleSocketMessage = useCallback((data) => {
        setMetrics(prev => ({
            ...prev,
            [data.metric]: data
        }));
    }, []);

    const handleConnect = () => {
        const now = Date.now();
        const start = now - 24 * 60 * 60 * 1000;
        fetchHistory(userId, start, now)
            .then(history => {
                const latest = {};
                history.forEach(h => latest[h.metric] = h);
                setMetrics(latest);
            });
    };

    useWebSocket(userId, handleSocketMessage);

    return (
        <div className="p-6 font-sans">
            <h1 className="text-2xl font-bold mb-4">Актуальные данные пользователя</h1>
            <Header userId={userId} setUserId={setUserId} onConnect={handleConnect} />
            <Metrics metrics={metrics} />
            <History userId={userId} />
            <CrudForm />
        </div>
    );
}

export default App;
