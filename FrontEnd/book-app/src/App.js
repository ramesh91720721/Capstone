// import React from "react";
// import BookSearch from "./components/BookSearch";

// function App() {
//     return (
//         <div>
//             <BookSearch />
//         </div>
//     );
// }
// import React from "react";
// import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
// import BookList from "./components/BookSearch";
// import AddBook from "./components/AddBook";
// import BorrowBook from "./components/BorrowBook";
// import Login from "./components/Login";  // Import the Login component

// function App() {
//   return (
//     <Router>
//       <div>
//         <h1>Library Management System</h1>
//         <nav>
//           <ul>
//             <li><a href="/">Home</a></li>
//             <li><a href="/add-book">Add Book</a></li>
//             <li><a href="/borrow-book">Borrow Book</a></li>
//             <li><a href="/login">Login</a></li>  {/* Add link for Login */}
//           </ul>
//         </nav>

//         <Routes>
//           <Route path="/" element={<BookList />} />
//           <Route path="/add-book" element={<AddBook />} />
//           <Route path="/borrow-book" element={<BorrowBook />} />
//           <Route path="/login" element={<Login />} />  {/* Define route for Login */}
//         </Routes>
//       </div>
//     </Router>
//   );
// }

// // export default App;
// import React, { useState } from 'react';
// import LoginPage from "./components/LoginPage";
// import BookSearch from "./components/BookSearch";

// // import BookList from "./components/BookSearch";
// // import AddBook from "./components/AddBook";
// // import BorrowBook from "./components/BorrowBook";

// function App() {
//   const [loggedIn, setLoggedIn] = useState(false);

//   const handleLogin = () => setLoggedIn(true);
//   const handleLogout = () => setLoggedIn(false);

//   return (
//     <div>
//       { loggedIn ? (
//           <BookSearch onLogout={handleLogout} />
//         ) : (
//           <LoginPage onLogin={handleLogin} />
//         )
//       }
//     </div>
//   );
// }

// export default App;

import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import LoginPage from './components/LoginPage';
import BookSearch from './components/BookSearch';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/books" element={<BookSearch />} />
      </Routes>
    </Router>
  );
}

export default App;
