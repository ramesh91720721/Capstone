import React, { useState } from "react";
import axios from "axios";

const AddBook = () => {
  const [bookData, setBookData] = useState({
    title: "",
    author: "",
    isbn: "",
    available: true,
  });

  const handleChange = (e) => {
    setBookData({ ...bookData, [e.target.name]: e.target.value });
  };

  const handleAddBook = () => {
    axios.post("http://localhost:8080/books/add", bookData)
      .then(response => {
        alert(response.data);
      })
      .catch(error => {
        alert("Error adding book!");
        console.error(error);
      });
  };

  return (
    <div>
      <h2>Add a Book</h2>
      <input
        type="text"
        name="title"
        value={bookData.title}
        onChange={handleChange}
        placeholder="Title"
      />
      <input
        type="text"
        name="author"
        value={bookData.author}
        onChange={handleChange}
        placeholder="Author"
      />
      <input
        type="text"
        name="isbn"
        value={bookData.isbn}
        onChange={handleChange}
        placeholder="ISBN"
      />
      <button onClick={handleAddBook}>Add Book</button>
    </div>
  );
};

export default AddBook;
