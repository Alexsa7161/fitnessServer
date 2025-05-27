import React, { useState } from 'react';
import Metrics from './Metrics';
import History from './History';
import AddRecord from './AddRecord';

function App() {
    const [userId, setUserId] = useState('');
    const [showHistory, setShowHistory] = useState(false);

    const handleConnect = () => {
        if (userId.trim()) {
            console.log(`Connecting for user: ${userId}`);
            // Тут твоя логика для подключения через WebSocket
        } else {
            console.log('❗ Введите User ID!');
        }
    };

    return (
        <div>
            <h1>Актуальные данные пользователя</h1>
            <input
                type="text"
                placeholder="Введите User ID"
                value={userId}
                onChange={(e) => setUserId(e.target.value)}
            />
            <button onClick={handleConnect}>Подключиться</button>
            <button onClick={() => setShowHistory(!showHistory)}>📜 История</button>

            <Metrics userId={userId} />
            {showHistory && <History userId={userId} />}
            <AddRecord userId={userId} />
        </div>
    );
}

export default App;
