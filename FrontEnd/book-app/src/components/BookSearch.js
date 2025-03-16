import React, { useState, useEffect } from "react";
import axios from "axios";

const BookSearch = () => {
    const [query, setQuery] = useState("");
    const [books, setBooks] = useState([]);
    const [loading, setLoading] = useState(false);

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
                        </tr>
                    </thead>
                    <tbody>
                        {books.length > 0 ? (
                            books.map((book) => (
                                <tr key={book.id}>
                                    <td>{book.title}</td>
                                    <td>{book.author}</td>
                                    <td>{book.isbn}</td>
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan="3" style={{ textAlign: "center" }}>No books found</td>
                            </tr>
                        )}
                    </tbody>
                </table>
            )}
        </div>
    );
};

export default BookSearch;
