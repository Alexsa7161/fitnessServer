import React, { useState } from "react";

const AddRecordForm = () => {
  const [newUserId, setNewUserId] = useState("");
  const [newMetric, setNewMetric] = useState("");
  const [newValue, setNewValue] = useState("");
  const [message, setMessage] = useState("");

  const handleAdd = () => {
    if (!newUserId || !newMetric || !newValue) {
      setMessage("❗ Заполните все поля для добавления!");
      return;
    }
    setMessage("");
    const payload = {
      userId: newUserId,
      metric: newMetric,
      value: parseFloat(newValue),
      timestamp: Date.now().toString(),
    };
    fetch('/api/data', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    })
      .then(res => res.ok ? setMessage("✅ Запись добавлена успешно!") : setMessage("❗ Ошибка добавления записи."))
      .catch(() => setMessage("❗ Ошибка сети при добавлении."));
  };

  return (
    <div id="crudBlock" data-testid="crud-form">
      <h2>Добавить новую запись</h2>

      <label htmlFor="newUserId">User ID:</label>
      <input
        id="newUserId"
        type="text"
        value={newUserId}
        onChange={(e) => setNewUserId(e.target.value)}
        placeholder="user_0"
      />

      <label htmlFor="newMetric">Метрика:</label>
      <input
        id="newMetric"
        type="text"
        value={newMetric}
        onChange={(e) => setNewMetric(e.target.value)}
        placeholder="heart_rate"
      />

      <label htmlFor="newValue">Значение:</label>
      <input
        id="newValue"
        type="number"
        value={newValue}
        onChange={(e) => setNewValue(e.target.value)}
        placeholder="85"
      />

      <button onClick={handleAdd}>Добавить запись</button>

      {message && <div>{message}</div>}
    </div>
  );
};

export default AddRecordForm;
