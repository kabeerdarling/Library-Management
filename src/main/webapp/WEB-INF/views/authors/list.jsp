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
        .container { max-width: 1100px; margin: 40px auto; padding: 0 20px; }
        h1 { margin-bottom: 20px; color: #2c3e50; }
        .toolbar { display: flex; justify-content: space-between; align-items: center; margin-bottom: 16px; }
        .btn { padding: 9px 18px; border-radius: 6px; text-decoration: none;
               font-size: 14px; font-weight: 600; color: white; }
        .btn-green { background: #27ae60; }
        .btn-green:hover { background: #219a52; }
        .btn-blue { background: #3498db; }
        .btn-blue:hover { background: #2980b9; }
        .alert-success { background: #d4edda; border: 1px solid #c3e6cb;
                         color: #155724; padding: 12px 18px; border-radius: 6px; margin-bottom: 16px; }
        table { width: 100%; border-collapse: collapse; background: white;
                border-radius: 8px; overflow: hidden; box-shadow: 0 2px 10px rgba(0,0,0,0.07); }
        th { background: #2c3e50; color: white; padding: 13px 15px; text-align: left; }
        td { padding: 12px 15px; border-bottom: 1px solid #ecf0f1; }
        tr:last-child td { border-bottom: none; }
        tr:hover td { background: #f8f9fa; }
        .action-link { color: #3498db; text-decoration: none; font-weight: 500; }
        .action-link:hover { text-decoration: underline; }
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
    <div class="toolbar">
        <h1>👤 ${pageTitle}</h1>
        <a href="/authors/new" class="btn btn-green">+ Add Author</a>
    </div>

    <c:if test="${not empty successMessage}">
        <div class="alert-success">${successMessage}</div>
    </c:if>

    <table>
        <thead>
            <tr>
                <th>#</th>
                <th>Name</th>
                <th>Email</th>
                <th>Nationality</th>
                <th>Birth Year</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="author" items="${authors}" varStatus="status">
                <tr>
                    <td>${status.count}</td>
                    <td>${author.name}</td>
                    <td>${author.email}</td>
                    <td>${author.nationality}</td>
                    <td>${author.birthYear}</td>
                    <td>
                        <a href="/authors/edit/${author.id}" class="action-link">✏️ Edit</a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty authors}">
                <tr><td colspan="6" style="text-align:center;color:#999;">No authors found.</td></tr>
            </c:if>
        </tbody>
    </table>
</div>
</body>
</html>
