import React, { useState, useEffect } from 'react';
import { Button, TextField, Typography, Container, Paper } from '@mui/material';
import ApiService from '../api/api.js';

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [user, setUser] = useState(null);
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    // Load user information from localStorage on component mount
    useEffect(() => {
        const storedUser = localStorage.getItem('user');
        const storedIsLoggedIn = localStorage.getItem('isLoggedIn') === 'true';

        if (storedUser && storedIsLoggedIn) {
            setUser(JSON.parse(storedUser));
            setIsLoggedIn(true);
        }
    }, []);

    // Save user information to localStorage when it changes
    useEffect(() => {
        if (user) {
            localStorage.setItem('user', JSON.stringify(user));
            localStorage.setItem('isLoggedIn', 'true');
        } else {
            localStorage.removeItem('user');
            localStorage.removeItem('isLoggedIn');
        }
    }, [user]);

    const handleLogin = async () => {
        try {
            const response = await ApiService.get('/osebe/login', { params: { username, password } });
            console.log(response);
            if (response && response.id) {
                console.log('Login successful:', response);
                setUser(response);
                setIsLoggedIn(true);
            } else {
                console.error('Login failed:', 'User not found');
            }
        } catch (error) {
            console.error('Login failed:', error.message);
        }
    };

    const handleLogout = () => {
        setUser(null);
        setIsLoggedIn(false);
    };

    return (
        <Container component="main" maxWidth="xs">
            <Paper elevation={3} style={{ padding: '20px', display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                {!isLoggedIn ? (
                    <div>
                        <Typography variant="h5">Login</Typography>
                        <TextField
                            variant="outlined"
                            margin="normal"
                            fullWidth
                            label="Username"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                        />
                        <TextField
                            variant="outlined"
                            margin="normal"
                            fullWidth
                            label="Password"
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                        <Button variant="contained" color="primary" onClick={handleLogin} style={{ marginTop: '20px' }}>
                            Login
                        </Button>
                    </div>
                ) : (
                    <div>
                        <Typography variant="h5" style={{ marginBottom: '20px' }}>
                            Welcome, {user && `${user.name} ${user.surname}`}
                        </Typography>
                        <Button variant="contained" color="secondary" onClick={handleLogout}>
                            Logout
                        </Button>
                    </div>
                )}
            </Paper>
        </Container>
    );
};

export default Login;
