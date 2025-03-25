import React, { useState, useEffect, useCallback } from 'react';
import axios from 'axios';
import { useLocation, useNavigate } from 'react-router-dom';

const BookSearch = () => {
  const [query, setQuery] = useState('');
  const [books, setBooks] = useState([]);
  const [message, setMessage] = useState('');
  const [borrowedBooks, setBorrowedBooks] = useState([]);
  const [currentTime, setCurrentTime] = useState(new Date());

  const location = useLocation();
  const navigate = useNavigate();

  // Retrieve the username from location.state
  const username = location.state?.username || 'User';
  const userid = location.state?.userId || 1;

  // Live clock: update current time every second
  useEffect(() => {
    const intervalId = setInterval(() => {
      setCurrentTime(new Date());
    }, 1000);
    return () => clearInterval(intervalId);
  }, []);

  const fetchAllBooks = async () => {
    console.log("fetch all books");
    try {
      const response = await axios.get("http://localhost:8080/api/books/all");
      setBooks(response.data);
    } catch (error) {
      console.error("Error fetching books:", error);
    }
  };

  const searchBooks = async () => {
    if (!query.trim()) {
      fetchAllBooks();
      return;
    }
    try {
      const response = await axios.get(`http://localhost:8080/api/books/search?query=${query}`);
      setBooks(response.data);
    } catch (error) {
      console.error("Error searching books:", error);
    }
  };

  useEffect(() => {
    fetchAllBooks();
  }, []);

  // Function to fetch books
  const fetchBooks = useCallback(async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/books/search', {
        params: { query },
        withCredentials: true,
      });
      setBooks(response.data);
    } catch (error) {
      console.error('Error fetching books:', error);
    }
  }, [query]);

  // Fetch all books initially
  useEffect(() => {
    fetchBooks();
  }, [fetchBooks]);

  const handleSearch = (e) => {
    e.preventDefault();
    searchBooks();
  };

  const handleBorrow = async (id) => {
    try {
      const cleanId = id.toString().replace(/['"]/g, '');
      const response = await axios.post(`http://localhost:8080/api/books/borrow/${cleanId}`, null, {
        withCredentials: true,
      });
      setMessage(response.data);
      fetchBooks(); // refresh book list after borrowing
      fetchBorrowedBooks();
    } catch (error) {
      console.error('Error borrowing book:', error);
      setMessage(error.response.data);
    }
  };

  const fetchBorrowedBooks = useCallback(async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/borrowed-books', {
        params: { user_id: userid },
        withCredentials: true,
      });
      setBorrowedBooks(response.data);
    } catch (error) {
      console.error('Error fetching borrowed books:', error);
    }
  }, [userid]);

  // Run both fetches on component mount
  useEffect(() => {
    fetchBooks();
    fetchBorrowedBooks();
  }, [fetchBooks, fetchBorrowedBooks]);

  // Auto-refresh borrowed books every 30 seconds
  useEffect(() => {
    const intervalId = setInterval(() => {
      fetchBorrowedBooks();
    }, 30000); // adjust the interval as needed
    return () => clearInterval(intervalId);
  }, [fetchBorrowedBooks]);

  const handleReturn = async (borrowedBook) => {
    try {
      const response = await axios.post(
        `http://localhost:8080/api/books/return/${borrowedBook.title}`,
        null,
        { withCredentials: true }
      );
      setMessage(response.data);
      fetchBooks();
      fetchBorrowedBooks();
    } catch (error) {
      console.error('Error returning book:', error);
      setMessage('Error returning book');
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

  // Container style with flex layout
  const containerStyle = {
    display: 'flex',
    gap: '1rem',
    padding: '1rem',
  };

  // Left panel (Book search area) style
  const leftPanelStyle = {
    flex: 3,
  };

  // Right panel (Borrowed books area) style with light yellow background
  const rightPanelStyle = {
    flex: 1,
    backgroundColor: '#ffffe0',
    padding: '1rem',
    borderRadius: '1px',
  };

  return (
    <div style={{ padding: '5rem' }}>
      <h2>
        Welcome, <strong>{username}</strong>!
      </h2>
      <p>Current Time: {currentTime.toLocaleTimeString()}</p>
      <button onClick={handleLogout}>Logout</button>
      <div style={containerStyle}>
        {/* Left Panel - Book Search */}
        <div style={leftPanelStyle}>
          <h3>Book Search</h3>
          <form onSubmit={handleSearch} style={{ marginBottom: '1rem' }}>
            <input
              type="text"
              placeholder="Search by title or author"
              value={query}
              onChange={(e) => setQuery(e.target.value)}
            />
            <button type="submit">Search</button>
          </form>
          {message && <p>{message}</p>}
          <table border="1" cellPadding="5" style={{ width: '100%' }}>
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
                    <td>{book.avilable}</td>
                    <td>
                      {book.avilable > 0 ? (
                        <button onClick={() => handleBorrow(book.book_id)}>Borrow</button>
                      ) : (
                        'Unavailable'
                      )}
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

        {/* Right Panel - Borrowed Books Details */}
        <div style={rightPanelStyle}>
          <h3>Your Borrowed Books</h3>
          {borrowedBooks.length > 0 ? (
            <ul>
              {borrowedBooks.map((borrowed) => {
                // Calculate the difference in minutes between now and when the book was borrowed
                const borrowedTime = new Date(borrowed.borrowedAt);
                const currentTime = new Date();
                const diffInMinutes = (currentTime - borrowedTime) / (1000 * 60);
                const isOverdue = diffInMinutes > 1; // Overdue if more than 1 minute

                return (
                  <li key={borrowed.id} style={{ color: isOverdue ? 'red' : 'inherit' }}>
                    <strong>{borrowed.title}</strong>
                    <br />
                    Borrowed on: {borrowedTime.toLocaleString()}
                    <br />
                    {borrowed.fine > 0 && (
                      <span style={{ marginLeft: '10px', fontWeight: 'bold' }}>
                        Overdue Fine: ${borrowed.fine.toFixed(2)}
                        <br />
                      </span>
                    )}
                    <button onClick={() => handleReturn(borrowed)}>Return</button>
                    <br /><br />
                  </li>
                );
              })}
            </ul>
          ) : (
            <p>No borrowed books</p>
          )}
        </div>
      </div>
    </div>
  );
};

export default BookSearch;
