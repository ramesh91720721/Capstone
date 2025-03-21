import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const LoginPage = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // Send login request to Spring Boot back end.
      const response = await axios.post('http://localhost:8080/auth/login', null, {
        params: { username, password },
        withCredentials: true
      });
      if (response.data === 'Login successful') {
        navigate('/api/books');
      }
    } catch (err) {
      setError('Invalid username or password');
    }
  };
// const handleSubmit = async (e) => {
//   e.preventDefault();
//   try {
//     const response = await axios.post('http://localhost:8080/auth/login', null, {
//       params: { username, password },
//       withCredentials: true
//     });
//     if (response.data === 'Login successful') {
//       navigate('/welcome');
//     }
//   } catch (err) {
//     setError('Invalid username or password');
//   }
// };

  return (
    <div style={{ padding: '2rem' }}>
      <h2>Login</h2>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      <form onSubmit={handleSubmit}>
        <div>
          <input 
            type="text" 
            placeholder="Username" 
            value={username} 
            onChange={(e) => setUsername(e.target.value)} 
            required 
          />
        </div>
        <br />
        <div>
          <input 
            type="password" 
            placeholder="Password" 
            value={password} 
            onChange={(e) => setPassword(e.target.value)} 
            required 
          />
        </div>
        <br />
        <button type="submit">Login</button>
      </form>
    </div>
  );
};

export default LoginPage;
