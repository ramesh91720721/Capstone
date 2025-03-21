import React, { useState } from "react";
import axios from "axios";

const BorrowBook = () => {
  const [bookId, setBookId] = useState("");
  const [message, setMessage] = useState("");

  const handleBorrow = () => {
    axios.post(`http://localhost:8080/books/borrow/${bookId}`)
      .then(response => {
        setMessage(response.data);
      })
      .catch(error => {
        setMessage("Error borrowing book!");
        console.error(error);
      });
  };

  return (
    <div>
      <h2>Borrow a Book</h2>
      <input
        type="text"
        value={bookId}
        onChange={(e) => setBookId(e.target.value)}
        placeholder="Enter book ID"
      />
      <button onClick={handleBorrow}>Borrow Book</button>
      {message && <p>{message}</p>}
    </div>
  );
};

export default BorrowBook;
