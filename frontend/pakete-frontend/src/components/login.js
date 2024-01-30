import React, { useState } from 'react';
import ApiService from '../api/api.js';


const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = async () => {
        console.log(username, password);
        try {
            const response = await ApiService.get('/osebe/login', { username, password });

            console.log('Login successful:', response);
        } catch (error) {
            console.error('Login failed:', error.message);
        }
    };

    return (
        <div>
            <h2>Login</h2>
            <label>
                Username:
                <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} />
            </label>
            <br />
            <label>
                Password:
                <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
            </label>
            <br />
            <button onClick={handleLogin}>Login</button>
        </div>
    );
};

export default Login;
