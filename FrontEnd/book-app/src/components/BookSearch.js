import React, { useState, useEffect } from "react";
import axios from "axios";

const BookSearch = () => {
    const [query, setQuery] = useState("");
    const [books, setBooks] = useState([]);
    const [loading, setLoading] = useState(false);
////   const response = await axios.post('http://localhost:8080/auth/login', null, {
    // Fetch all books
    const fetchAllBooks = async () => {
        setLoading(true);
        try {
            const response = await axios.get("http://localhost:8080/api/books/all");
            setBooks(response.data);
        } catch (error) {
            console.error("Error fetching books:", error);
        }
        setLoading(false);
    };

    // Fetch searched books
    const searchBooks = async () => {
        if (!query.trim()) {
            fetchAllBooks();
            return;
        }

        setLoading(true);
        try {
            const response = await axios.get(`http://localhost:8080/api/books/search?query=${query}`);
            setBooks(response.data);
        } catch (error) {
            console.error("Error searching books:", error);
        }
        setLoading(false);
    };

    // Add selected book to borrowed list
    const borrowBook = async (bookId) => {
        try {
            const response = await axios.post("http://localhost:8080/api/books/borrow", { bookId });
            if (response.data.success) {
                alert("Book borrowed successfully!");
            }
        } catch (error) {
            console.error("Error borrowing book:", error);
            alert("There was an error borrowing the book.");
        }
    };

    useEffect(() => {
        fetchAllBooks();
    }, []);

    return (
        <div style={{ padding: "20px", maxWidth: "600px", margin: "auto" }}>
            <h2>Library Book Search</h2>
            <input
                type="text"
                value={query}
                onChange={(e) => setQuery(e.target.value)}
                placeholder="Search by title, author, or ISBN..."
                style={{ width: "70%", padding: "10px", marginRight: "10px" }}
            />
            <button onClick={searchBooks} style={{ padding: "10px" }}>Search</button>
            <button onClick={fetchAllBooks} style={{ padding: "10px", marginLeft: "10px" }}>List All</button>

            {loading ? <p>Loading...</p> : (
                <table border="1" style={{ marginTop: "20px", width: "100%" }}>
                    <thead>
                        <tr>
                            <th>Title</th>
                            <th>Author</th>
                            <th>ISBN</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {books.length > 0 ? (
                            books.map((book) => (
                                <tr key={book.id}>
                                    <td>{book.title}</td>
                                    <td>{book.author}</td>
                                    <td>{book.isbn}</td>
                                    <td>
                                        <button onClick={() => borrowBook(book.id)} style={{ padding: "5px" }}>
                                            Borrow
                                        </button>
                                    </td>
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan="4" style={{ textAlign: "center" }}>No books found</td>
                            </tr>
                        )}
                    </tbody>
                </table>
            )}
        </div>
    );
};

  export default BookSearch;
// import React, { useState, useEffect } from 'react';
// import axios from 'axios';
// import { useNavigate } from 'react-router-dom';

// const BookSearch = () => {
//   const [query, setQuery] = useState('');
//   const [books, setBooks] = useState([]);
//   const [message, setMessage] = useState('');
//   const navigate = useNavigate();

//   // Fetch books based on the query.
//   const fetchBooks = async () => {
//     try {
//       const response = await axios.get('http://localhost:8080/api/books/search', {
//         params: { query },
//         withCredentials: true,
//       });
//       setBooks(response.data);
//     } catch (error) {
//       console.error('Error fetching books:', error);
//     }
//   };

//   // Fetch all books initially
//   useEffect(() => {
//     fetchBooks();
//   }, []);

//   const handleSearch = (e) => {
//     e.preventDefault();
//     fetchBooks();
//   };

//   // Borrow a book by ID
//   const handleBorrow = async (id) => {
//     try {
//       const response = await axios.post(`http://localhost:8080/books/borrow/${id}`, null, {
//         withCredentials: true,
//       });
//       setMessage(response.data);
//       fetchBooks(); // refresh book list after borrowing
//     } catch (error) {
//       console.error('Error borrowing book:', error);
//       setMessage('Error borrowing book');
//     }
//   };

//   // Logout function (optional)
//   const handleLogout = async () => {
//     try {
//       await axios.post('http://localhost:8080/auth/logout', null, { withCredentials: true });
//       navigate('/');
//     } catch (err) {
//       console.error("Logout failed", err);
//     }
//   };

//   return (
//     <div style={{ padding: '2rem' }}>
//       <h2>Book Search</h2>
//       <button onClick={handleLogout}>Logout</button>
//       <form onSubmit={handleSearch} style={{ marginTop: '1rem' }}>
//         <input
//           type="text"
//           placeholder="Search by title or author"
//           value={query}
//           onChange={(e) => setQuery(e.target.value)}
//         />
//         <button type="submit">Search</button>
//       </form>
//       {message && <p>{message}</p>}
//       <table border="1" cellPadding="5" style={{ marginTop: '1rem' }}>
//         <thead>
//           <tr>
//             <th>Title</th>
//             <th>Author</th>
//             <th>ISBN</th>
//             <th>Available</th>
//             <th>Action</th>
//           </tr>
//         </thead>
//         <tbody>
//           {books.length > 0 ? (
//             books.map((book) => (
//               <tr key={book.id}>
//                 <td>{book.title}</td>
//                 <td>{book.author}</td>
//                 <td>{book.isbn}</td>
//                 <td>{book.available ? 'Yes' : 'No'}</td>
//                 <td>
//                   {book.available ? (
//                     <button onClick={() => handleBorrow(book.id)}>Borrow</button>
//                   ) : (
//                     'Unavailable'
//                   )}
//                 </td>
//               </tr>
//             ))
//           ) : (
//             <tr>
//               <td colSpan="5">No books found</td>
//             </tr>
//           )}
//         </tbody>
//       </table>
//     </div>
//   );
// };

// export default BookSearch;
