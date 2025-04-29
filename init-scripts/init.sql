CREATE TABLE IF NOT EXISTS fitness_data (
    id BIGINT PRIMARY KEY,
    user_id VARCHAR(255),
    metric VARCHAR(255),
    value DOUBLE PRECISION,
    timestamp VARCHAR(255)
);
INSERT INTO fitness_data (id, user_id, metric, value, timestamp)
VALUES
(69696, 'user_1', 'distance', 93.30350821192472, '1744402456511'),
(87788, 'user_1', 'battery', 83, '1744402460267'),
(94534, 'user_1', 'battery', 83, '1744402461670'),
(100483, 'user_1', 'distance', 93.30350821192472, '1744402462762'),
(187476, 'user_1', 'heartRate', 100, '1744402549234'),
(202513, 'user_1', 'battery', 100, '1744402555968'),
(216401, 'user_1', 'heartRate', 100, '1744402551150'),
(219247, 'user_1', 'heartRate', 100, '1744402553053'),
(222314, 'user_1', 'heartRate', 100, '1744402556941'),
(223371, 'user_1', 'heartRate', 100, '1744402549234');
