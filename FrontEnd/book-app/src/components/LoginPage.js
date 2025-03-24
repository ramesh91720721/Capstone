import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const LoginPage = () => {
  const [isSignUp, setIsSignUp] = useState(false); // Toggle between login and signup
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  // Toggle mode between login and sign up
  const toggleMode = () => {
    setIsSignUp(prev => !prev);
    setError('');
  };

  // Handle login or signup form submission
  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    
    try {
      if (isSignUp) {
        // Signup mode: call the signup endpoint to add the new user
        const response = await axios.post('http://localhost:8080/auth/signup', null, {
          params: { username, password },
          withCredentials: true
        });
        if (response.data.success) {
          // Optionally, automatically log in the user after signup
          // or display a message and switch back to login mode.
          navigate('/books', { state: { username: response.data.username, userId: response.data.userid } });
        } else {
          setError(response.data.message || 'Signup failed');
        }
      } else {
        // Login mode: call the login endpoint
        const response = await axios.post('http://localhost:8080/auth/login', null, {
          params: { username, password },
          withCredentials: true
        });
        if (response.data.success) {
          navigate('/books', { state: { username: response.data.username, userId: response.data.userid } });
        } else {
          setError(response.data.message || 'Invalid username or password');
        }
      }
    } catch (err) {
      console.error(err);
      setError(isSignUp ? 'Signup failed' : 'Invalid username or password');
    }
  };

  return (
    <div style={{ padding: '2rem' }}>
      <h2>{isSignUp ? 'Sign Up' : 'Login'}</h2>
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
        <button type="submit">{isSignUp ? 'Sign Up' : 'Login'}</button>
      </form>
      <br />
      <button onClick={toggleMode}>
        {isSignUp ? 'Already have an account? Login' : "Don't have an account? Sign Up"}
      </button>
    </div>
  );
};

export default LoginPage;
