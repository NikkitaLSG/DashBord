import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { LineChart, Line, XAxis, YAxis, Tooltip, CartesianGrid } from 'recharts';

function App() {
    const [data, setData] = useState([]);

    useEffect(() => {
        // Пример запроса: /api/metrics?type=cpu&from=2024-04-09T00:00:00&to=2025-04-10T23:59:59
        axios.get('http://localhost:8080/api/metrics', {
            params: {
                type: 'cpu',
                from: '2024-04-09T00:00:00',
                to: '2025-04-10T23:59:59'
            }
        })
            .then(res => setData(res.data))
            .catch(err => console.error(err));
    }, []);

    return (
        <div>
            <h2>CPU Usage</h2>
            <LineChart width={800} height={400} data={data}>
                <XAxis dataKey="timestamp" />
                <YAxis />
                <Tooltip />
                <CartesianGrid stroke="#ccc" />
                <Line type="monotone" dataKey="value" stroke="#8884d8" />
            </LineChart>
        </div>
    );
}

export default App;
