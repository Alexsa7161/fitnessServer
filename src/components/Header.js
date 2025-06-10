export default function Header({ userId, setUserId, onConnect }) {
    return (
        <div className="flex items-center gap-2 mb-4">
            <input
                className="border rounded p-2"
                placeholder="user_0"
                value={userId}
                onChange={(e) => setUserId(e.target.value)}
            />
            <button onClick={onConnect} className="bg-blue-500 text-white px-4 py-2 rounded">Подключиться</button>
        </div>
    );
}
