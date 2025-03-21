import React from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const WelcomePage = () => {
  const navigate = useNavigate();

  const handleLogout = async () => {
    try {
      await axios.post('http://localhost:8080/auth/logout', null, { withCredentials: true });
      navigate('/');
    } catch (err) {
      console.error("Logout failed", err);
    }
  };

  return (
    <div style={{ padding: '2rem' }}>
      <h2>Welcome!</h2>
      <p>You have successfully logged in.</p>
      <button onClick={handleLogout}>Logout</button>
    </div>
  );
};

export default WelcomePage;
