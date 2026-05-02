<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        .container { max-width: 1200px; margin: 40px auto; padding: 0 20px; }
        h1 { margin-bottom: 20px; color: #2c3e50; }
        .toolbar { display: flex; justify-content: space-between; align-items: center; margin-bottom: 16px; }
        .btn { padding: 9px 18px; border-radius: 6px; text-decoration: none; font-size: 14px; font-weight: 600; color: white; }
        .btn-green { background: #27ae60; }
        .btn-green:hover { background: #219a52; }
        .alert-success { background: #d4edda; border: 1px solid #c3e6cb;
                         color: #155724; padding: 12px 18px; border-radius: 6px; margin-bottom: 16px; }
        table { width: 100%; border-collapse: collapse; background: white;
                border-radius: 8px; overflow: hidden; box-shadow: 0 2px 10px rgba(0,0,0,0.07); }
        th { background: #2c3e50; color: white; padding: 13px 15px; text-align: left; font-size: 13px; }
        td { padding: 12px 15px; border-bottom: 1px solid #ecf0f1; font-size: 14px; }
        tr:last-child td { border-bottom: none; }
        tr:hover td { background: #f8f9fa; }
        .genre-badge { display: inline-block; padding: 3px 10px; border-radius: 12px;
                       font-size: 12px; font-weight: 600; background: #eaf4ff; color: #2980b9; }
        .action-link { color: #3498db; text-decoration: none; font-weight: 500; }
        .action-link:hover { text-decoration: underline; }
        .note { color: #777; font-size: 13px; margin-bottom: 10px; }
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
        <h1>📖 ${pageTitle}</h1>
        <a href="/books/new" class="btn btn-green">+ Add Book</a>
    </div>

    <c:if test="${not empty successMessage}">
        <div class="alert-success">${successMessage}</div>
    </c:if>

    <p class="note">ℹ️ This view uses an INNER JOIN between Books and Authors tables.</p>

    <table>
        <thead>
            <tr>
                <th>#</th>
                <th>Title</th>
                <th>Genre</th>
                <th>Year</th>
                <th>Price ($)</th>
                <th>ISBN</th>
                <th>Author</th>
                <th>Nationality</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="b" items="${booksWithAuthors}" varStatus="s">
                <tr>
                    <td>${s.count}</td>
                    <td><strong>${b.bookTitle}</strong></td>
                    <td><span class="genre-badge">${b.genre}</span></td>
                    <td>${b.publishedYear}</td>
                    <td><fmt:formatNumber value="${b.price}" pattern="#,##0.00" /></td>
                    <td>${b.isbn}</td>
                    <td>${b.authorName}</td>
                    <td>${b.nationality}</td>
                    <td>
                        <a href="/books/edit/${b.bookId}" class="action-link">✏️ Edit</a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty booksWithAuthors}">
                <tr><td colspan="9" style="text-align:center;color:#999;">No books found.</td></tr>
            </c:if>
        </tbody>
    </table>
</div>
</body>
</html>
