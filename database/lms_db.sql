CREATE DATABASE LMS_DB;
use LMS_DB;

ALTER TABLE books MODIFY book_id BIGINT NOT NULL AUTO_INCREMENT;

CREATE TABLE books (
    book_id int primary key,
    title varchar(100) not null,
    author varchar(100) not null,
    ISBN varchar(15),
    qty int,
    branch_id int,
    avilable bool
);
ALTER TABLE books
ADD CONSTRAINT unique_isbn
UNIQUE (ISBN);

insert into books  values (1, "You can win", "Shiv" , "123456789012", 10, 1001, 10);
insert into books  values (2, '1984', 'George Orwell', '9780451524935', 3,1001, 3);

-- Insert 10 books into the 'books' table
INSERT INTO books  VALUES 
(3, 'The Great Gatsby', 'F. Scott Fitzgerald', '9780743273565', 4, 1001, 4),
(4, 'Pride and Prejudice', 'Jane Austen', '9780141439518', 6, 1001, 6),
(5, 'The Catcher in the Rye', 'J.D. Salinger', '9780316769488', 2, 1001, 2),
(6, 'The Hobbit', 'J.R.R. Tolkien', '9780261103344', 7, 1001, 7),
(7, 'Moby-Dick', 'Herman Melville', '9781851244422', 1, 1001, 1),
(8, 'War and Peace', 'Leo Tolstoy', '9781853260629', 4, 1001, 4),
(9, 'The Odyssey', 'Homer', '9780140268867', 10,1001, 10),
(10, 'The Lord of the Rings', 'J.R.R. Tolkien', '9780261103252', 3, 1001, 3);



CREATE TABLE userinfos (
    user_id int   AUTO_INCREMENT  primary key,
    user_name varchar(100) not null,
    pw varchar(100) not null,
    email varchar(100),
    contact int not null
);

ALTER TABLE users MODIFY user_id INT NOT NULL AUTO_INCREMENT;

INSERT INTO userinfos  VALUES  (1, "ramesh", "root", "myname@email.com", 12345); 

