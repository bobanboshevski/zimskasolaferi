import React, { useState, useEffect } from 'react';
import { Button, TextField, Typography, Container, Paper, Grid, Card, CardContent } from '@mui/material';
import ApiService from '../api/api.js';

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [user, setUser] = useState(null);
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [pakete, setPakete] = useState([]);

    // Load user information from localStorage on component mount
    useEffect(() => {
        const storedUser = localStorage.getItem('user');
        const storedIsLoggedIn = localStorage.getItem('isLoggedIn') === 'true';

        if (storedUser && storedIsLoggedIn) {
            setUser(JSON.parse(storedUser));
            setIsLoggedIn(true);

            // Fetch all Paket entities after successful login
            fetchAllPakete();
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

    // Function to fetch all Paket entities
    const fetchAllPakete = async () => {
        try {
            const paketeResponse = await ApiService.get('/pakete');
            setPakete(paketeResponse);
            console.log('All Pakete:', paketeResponse);
        } catch (error) {
            console.error('Failed to fetch Pakete:', error.message);
        }
    };

    const handleLogin = async () => {
        try {
            const response = await ApiService.get('/osebe/login', { params: { username, password } });
            console.log(response);
            if (response && response.id) {
                console.log('Login successful:', response);
                setUser(response);
                setIsLoggedIn(true);

                // Fetch all Paket entities after successful login
                fetchAllPakete();
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
    const handleIzberiClick = (paketId) => {
        console.log(`Izberi clicked for Paket ID: ${paketId}`);
        // You can perform additional actions here if needed
    };
    const calculateGridSize = () => {
        const totalCards = pakete.length;
        // Set a default value (e.g., 4) to use when no cards are available
        let gridSize = 4;

        if (totalCards > 0) {
            // Calculate the dynamic xs value based on the total number of cards
            gridSize = Math.floor(12 / totalCards);
        }

        return gridSize;
    };

    return (
        <Container component="main" maxWidth="s">
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
                        <Typography variant="h6" style={{ marginBottom: '20px' }}>
                            Vas trenutni paket je: {user && user.paketIme} in velja do: {user.expirationDate}
                        </Typography>
                        <Button variant="contained" color="secondary" onClick={handleLogout}>
                            Logout
                        </Button>
                        {/*<h2>Izberite vas paket:</h2>*/}
                        <Typography variant="h6" style={{ marginTop: '20px' }}>
                            Izberite vaš paket:
                        </Typography>
                        {/* Display Pakete in a grid */}
                        <Grid container spacing={3} style={{ marginTop: '10px' }}>
                            {pakete.map((paket) => (
                                <Grid item key={paket.id} xs={calculateGridSize()}>
                                    <Card>
                                        <CardContent>
                                            <Typography variant="h6">{paket.imePaket}</Typography>
                                            <Typography>Cena: {paket.cenaPaket} €</Typography>
                                            <Button
                                                variant="contained"
                                                color="primary"
                                                onClick={() => handleIzberiClick(paket.id)}
                                                style={{ marginTop: '10px' }}
                                            >
                                                Izberi
                                            </Button>
                                        </CardContent>
                                    </Card>
                                </Grid>
                            ))}
                        </Grid>
                    </div>
                )}
            </Paper>
        </Container>
    );
};

export default Login;
