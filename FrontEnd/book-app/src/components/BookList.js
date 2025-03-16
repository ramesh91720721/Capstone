import React, { useEffect, useState } from "react";
import axios from "axios";

const BookList = () => {
    const [books, setBooks] = useState([]);

    useEffect(() => {
        axios.get("http://localhost:8080/api/books")
            .then(response => setBooks(response.data))
            .catch(error => console.error("Error fetching books:", error));
    }, []);

    return (
        <div>
            <h2>Book List</h2>
            <table border="1">
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Author</th>
                        <th>ISBN</th>
                        <th>Quantity</th>
                        <th>Branch ID</th>
                        <th>Available</th>
                    </tr>
                </thead>
                <tbody>
                    {books.map(book => (
                        <tr key={book.id}>
                            <td>{book.title}</td>
                            <td>{book.author}</td>
                            <td>{book.isbn}</td>
                            <td>{book.qty}</td>
                            <td>{book.branch_id}</td>
                            <td>{book.available ? "Yes" : "No"}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default BookList;
