import { useEffect, useRef } from 'react';

export const useWebSocket = (userId, onMessage) => {
    const socketRef = useRef(null);

    useEffect(() => {
        if (!userId) return;

        const socket = new WebSocket('ws://localhost:8080/ws');
        socket.onopen = () => socket.send(userId);
        socket.onmessage = (e) => {
            try {
                const data = JSON.parse(e.data);
                onMessage(data);
            } catch (err) {
                console.error("Ошибка при разборе JSON:", e.data);
            }
        };
        socket.onerror = () => console.error("Ошибка WebSocket");

        socketRef.current = socket;

        return () => socket.close();
    }, [userId, onMessage]);

    return socketRef.current;
};
