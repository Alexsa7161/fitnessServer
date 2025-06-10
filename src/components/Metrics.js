export default function Metrics({ metrics }) {
    return (
        <div className="grid gap-4" id="metrics">
            {Object.entries(metrics).map(([metric, { timestamp, value }]) => (
                <div key={metric} className="metric border p-4 rounded bg-gray-100">
                    <h3>📊 {metric}</h3>
                    <p>🕒 {new Date(Number(timestamp)).toLocaleString()}</p>
                    <p>🔢 Значение: {value}</p>
                </div>
            ))}
        </div>
    );
}