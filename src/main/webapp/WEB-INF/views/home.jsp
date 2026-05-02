<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Library Management System</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', sans-serif; background: #f0f4f8; color: #333; }
        nav { background: #2c3e50; padding: 14px 30px; display: flex; align-items: center; gap: 20px; }
        nav a { color: #ecf0f1; text-decoration: none; font-size: 15px; padding: 6px 14px;
                border-radius: 4px; transition: background 0.2s; }
        nav a:hover { background: #34495e; }
        nav .brand { color: #f1c40f; font-weight: bold; font-size: 18px; margin-right: auto; }
        .hero { background: linear-gradient(135deg, #2c3e50, #3498db);
                color: white; text-align: center; padding: 80px 20px; }
        .hero h1 { font-size: 42px; margin-bottom: 12px; }
        .hero p  { font-size: 18px; opacity: 0.85; margin-bottom: 30px; }
        .cards { display: flex; gap: 30px; justify-content: center; padding: 50px 30px; flex-wrap: wrap; }
        .card { background: white; border-radius: 10px; padding: 30px; width: 280px;
                box-shadow: 0 4px 16px rgba(0,0,0,0.08); text-align: center; }
        .card h2 { font-size: 22px; margin-bottom: 10px; color: #2c3e50; }
        .card p  { color: #666; margin-bottom: 20px; }
        .btn { display: inline-block; padding: 10px 22px; border-radius: 6px;
               text-decoration: none; font-size: 14px; font-weight: 600;
               background: #3498db; color: white; transition: background 0.2s; }
        .btn:hover { background: #2980b9; }
        .btn-green { background: #27ae60; }
        .btn-green:hover { background: #219a52; }
    </style>
</head>
<body>
<nav>
    <span class="brand">📚 LibraryApp </span>
    <a href="/">Home</a>
    <a href="/authors">Authors</a>
    <a href="/books">Books</a>
    <a href="/books">Books + Authors (Join)</a>
</nav>

<div class="hero">
    <h1>📚 Library Management System</h1>
    <p>Manage your Books and Authors with ease</p>
    <a href="/books" class="btn">View All Books</a>
</div>

<div class="cards">
    <div class="card">
        <h2>👤 Authors</h2>
        <p>View, add, and update author records in the system.</p>
        <a href="/authors" class="btn">Manage Authors</a><br><br>
        <a href="/authors/new" class="btn btn-green">+ Add Author</a>
    </div>
    <div class="card">
        <h2>📖 Books</h2>
        <p>Browse the full catalogue of books with author details.</p>
        <a href="/books" class="btn">Manage Books</a><br><br>
        <a href="/books/new" class="btn btn-green">+ Add Book</a>
    </div>
</div>
</body>
</html>
