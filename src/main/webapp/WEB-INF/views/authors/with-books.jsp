<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${pageTitle} - LibraryApp</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', sans-serif; background: #f0f4f8; color: #333; }
        nav { background: #2c3e50; padding: 14px 30px; display: flex; align-items: center; gap: 20px; }
        nav a { color: #ecf0f1; text-decoration: none; font-size: 15px; padding: 6px 14px; border-radius: 4px; }
        nav a:hover { background: #34495e; }
        nav .brand { color: #f1c40f; font-weight: bold; font-size: 18px; margin-right: auto; }
        .container { max-width: 1000px; margin: 40px auto; padding: 0 20px; }
        h1 { margin-bottom: 20px; color: #2c3e50; }
        table { width: 100%; border-collapse: collapse; background: white;
                border-radius: 8px; overflow: hidden; box-shadow: 0 2px 10px rgba(0,0,0,0.07); }
        th { background: #2c3e50; color: white; padding: 13px 15px; text-align: left; }
        td { padding: 12px 15px; border-bottom: 1px solid #ecf0f1; }
        tr:last-child td { border-bottom: none; }
        tr:hover td { background: #f8f9fa; }
    </style>
</head>
<body>
<nav>
    <span class="brand">📚 LibraryApp</span>
    <a href="/">Home</a>
    <a href="/authors">Authors</a>
    <a href="/books">Books</a>
</nav>
<div class="container">
    <h1>🔗 Authors Who Have Books</h1>
    <table>
        <thead>
            <tr><th>#</th><th>Author</th><th>Email</th><th>Nationality</th></tr>
        </thead>
        <tbody>
            <c:forEach var="a" items="${authors}" varStatus="s">
                <tr>
                    <td>${s.count}</td>
                    <td>${a.name}</td>
                    <td>${a.email}</td>
                    <td>${a.nationality}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
