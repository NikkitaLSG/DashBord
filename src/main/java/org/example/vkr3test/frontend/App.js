import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { LineChart, Line, XAxis, YAxis, Tooltip, CartesianGrid, Legend } from 'recharts';

function App() {
    const [data, setData] = useState([]);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);
    const [metricType, setMetricType] = useState('cpu');

    const fetchData = async (type) => {
        try {
            setLoading(true);
            const res = await axios.get('http://localhost:8080/api/metrics', {
                params: {
                    type: type,
                    from: new Date(Date.now() - 24 * 60 * 60 * 1000).toISOString(),
                    to: new Date().toISOString()
                }
            });
            setData(res.data.map(item => ({
                ...item,
                timestamp: new Date(item.timestamp).toLocaleTimeString()
            })));
        } catch (err) {
            setError(err.response?.data?.message || err.message);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchData(metricType);
    }, [metricType]);

    if (loading) return <div className="loading">Loading metrics...</div>;
    if (error) return <div className="error">Error: {error}</div>;

    return (
        <div className="dashboard">
            <h2>System Metrics Dashboard</h2>
            <div className="controls">
                <select
                    value={metricType}
                    onChange={(e) => setMetricType(e.target.value)}
                >
                    <option value="cpu">CPU Usage</option>
                    <option value="gpu">GPU Usage</option>
                    <option value="memory">Memory Usage</option>
                </select>
            </div>

            <LineChart width={1000} height={500} data={data}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="timestamp" />
                <YAxis label={{ value: 'Usage %', angle: -90, position: 'insideLeft' }}/>
                <Tooltip />
                <Legend />
                <Line
                    type="monotone"
                    dataKey="value"
                    stroke="#8884d8"
                    activeDot={{ r: 8 }}
                    name={metricType.toUpperCase()}
                />
            </LineChart>
        </div>
    );
}

export default App;