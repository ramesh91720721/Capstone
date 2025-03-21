import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useLocation, useNavigate } from 'react-router-dom';

const BookSearch = () => {
  const [query, setQuery] = useState('');
  const [books, setBooks] = useState([]);
  const [message, setMessage] = useState('');
  const location = useLocation();
  const navigate = useNavigate();

  // Retrieve the username from location.state
  const username = location.state?.username || 'User';

  // Function to fetch books
  const fetchBooks = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/books/search', {
        params: { query },
        withCredentials: true,
      });
      setBooks(response.data);
    } catch (error) {
      console.error('Error fetching books:', error);
    }
  };

  // Fetch all books initially
  useEffect(() => {
    fetchBooks();
  }, []);

  const handleSearch = (e) => {
    e.preventDefault();
    fetchBooks();
  };

  const handleBorrow = async (id) => {
    try {
         const cleanId = id.toString().replace(/['"]/g, '');
      const response = await axios.post(`http://localhost:8080/api/books/borrow/${cleanId}`, null, {
        withCredentials: true,
      });
      setMessage(response.data);
      fetchBooks(); // refresh book list after borrowing
    } catch (error) {
      console.error('Error borrowing book:', error);
      setMessage('Error borrowing book');
    }
  };

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
      <h2>
        Welcome, <strong>{username}</strong>!
      </h2>
      <h3>Book Search</h3>
      <button onClick={handleLogout}>Logout</button>
      <form onSubmit={handleSearch} style={{ marginTop: '1rem' }}>
        <input
          type="text"
          placeholder="Search by title or author"
          value={query}
          onChange={(e) => setQuery(e.target.value)}
        />
        <button type="submit">Search</button>
      </form>
      {message && <p>{message}</p>}
      <table border="1" cellPadding="5" style={{ marginTop: '1rem' }}>
        <thead>
          <tr>
            <th>Title</th>
            <th>Author</th>
            <th>ISBN</th>
            <th>Available</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {books.length > 0 ? (
            books.map((book) => (
              <tr key={book.book_id}>
                <td>{book.title}</td>
                <td>{book.author}</td>
                <td>{book.isbn}</td>
                <td>{book.available}</td>
                <td>
                   
                    <button onClick={() => handleBorrow(book.book_id)}>Borrow</button>
                   
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="5">No books found</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};

export default BookSearch;
