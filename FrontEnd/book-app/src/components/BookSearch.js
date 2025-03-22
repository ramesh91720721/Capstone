import React, { useState, useEffect ,useCallback} from 'react';
import axios from 'axios';
import { useLocation, useNavigate } from 'react-router-dom';

const BookSearch = () => {
  const [query, setQuery] = useState('');
  const [books, setBooks] = useState([]);
  const [message, setMessage] = useState('');
  const [borrowedBooks, setBorrowedBooks] = useState([]);

 // const [loading, setLoading] = useState(false);
  const location = useLocation();
  const navigate = useNavigate();

  // Retrieve the username from location.state
  const username = location.state?.username || 'User';
  const   userid = location.state?.userId || 1;


    const fetchAllBooks = async () => {
        console.log(" fetch all books ")
           // setLoading(true);
            try {
                const response = await axios.get("http://localhost:8080/api/books/all");
                setBooks(response.data);
            } catch (error) {
                console.error("Error fetching books:", error);
            }
           // setLoading(false);
        };

  // Function to fetch books
  const fetchBooks  = useCallback(async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/books/search', {
        params: { query },
        withCredentials: true,
      });
      setBooks(response.data);
    } catch (error) {
      console.error('Error fetching books:', error);
    }
  }, []); // no dependencies if fetchBooks doesn't use external values

  // Fetch all books initially
  useEffect(() => {
    fetchBooks();
  }, [fetchBooks]);

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
      fetchBorrowedBooks();
    } catch (error) {
      console.error('Error borrowing book:', error);
      setMessage('Error borrowing book');
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




  const handleLogout = async () => {
    try {
      await axios.post('http://localhost:8080/auth/logout', null, { withCredentials: true });
      navigate('/');
    } catch (err) {
      console.error("Logout failed", err);
    }
  };

//   const handleListALL = async () => {
//     try {
//        const response = await axios.post('http://localhost:8080/api/books/all', null, { withCredentials: true });
//       setMessage(response.data);
//     } catch (err) {
//       console.error("Listall  failed", err);
//     }
//   };

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
    borderRadius: '4px',
  };

  return (
    <div style={{ padding: '2rem' }}>
      <h2>
        Welcome, <strong>{username}</strong>!
      </h2>
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
              {borrowedBooks.map((borrowed) => (
                <li key={borrowed.id}>
                  {/* Customize this display as needed; here we assume borrowed book record includes a title and borrowedAt */}
                  <strong>{borrowed.title || `Book ID ${borrowed.bookId}`}</strong>
                  <br />
                  Borrowed on: {new Date(borrowed.borrowedAt).toLocaleString()}
                </li>
              ))}
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
