<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
        .container { max-width: 620px; margin: 40px auto; padding: 0 20px; }
        .card { background: white; border-radius: 10px; padding: 35px;
                box-shadow: 0 4px 16px rgba(0,0,0,0.08); }
        h1 { margin-bottom: 24px; color: #2c3e50; }
        .form-group { margin-bottom: 18px; }
        label { display: block; font-weight: 600; margin-bottom: 6px; font-size: 14px; color: #555; }
        input[type="text"], input[type="number"], select {
            width: 100%; padding: 10px 14px; border: 1px solid #ddd;
            border-radius: 6px; font-size: 15px; background: white; }
        input:focus, select:focus {
            outline: none; border-color: #3498db;
            box-shadow: 0 0 0 3px rgba(52,152,219,0.15); }
        .error { color: #e74c3c; font-size: 13px; margin-top: 4px; }
        .alert-error { background: #f8d7da; border: 1px solid #f5c6cb;
                       color: #721c24; padding: 12px 18px; border-radius: 6px; margin-bottom: 16px; }
        .btn { padding: 10px 24px; border-radius: 6px; font-size: 15px; font-weight: 600; border: none; cursor: pointer; }
        .btn-primary { background: #3498db; color: white; }
        .btn-primary:hover { background: #2980b9; }
        .btn-cancel { background: #ecf0f1; color: #555; text-decoration: none;
                      padding: 10px 24px; border-radius: 6px; font-size: 15px; }
        .actions { display: flex; gap: 12px; margin-top: 8px; }
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
    <div class="card">
        <h1>📖 ${pageTitle}</h1>

        <c:if test="${not empty errorMessage}">
            <div class="alert-error">${errorMessage}</div>
        </c:if>

        <c:choose>
            <c:when test="${book.id != null}">
                <form:form action="/books/update/${book.id}" method="post" modelAttribute="book">
            </c:when>
            <c:otherwise>
                <form:form action="/books/save" method="post" modelAttribute="book">
            </c:otherwise>
        </c:choose>

            <div class="form-group">
                <label>Title *</label>
                <form:input path="title" placeholder="e.g. Pride and Prejudice" />
                <form:errors path="title" cssClass="error" />
            </div>
            <div class="form-group">
                <label>Genre *</label>
                <form:input path="genre" placeholder="e.g. Romance, Sci-Fi, Drama" />
                <form:errors path="genre" cssClass="error" />
            </div>
            <div class="form-group">
                <label>Published Year *</label>
                <form:input path="publishedYear" type="number" placeholder="e.g. 1813" />
                <form:errors path="publishedYear" cssClass="error" />
            </div>
            <div class="form-group">
                <label>Price ($)</label>
                <form:input path="price" type="number" placeholder="e.g. 12.99" />
                <form:errors path="price" cssClass="error" />
            </div>
            <div class="form-group">
                <label>ISBN</label>
                <form:input path="isbn" placeholder="e.g. ISBN-001" />
                <form:errors path="isbn" cssClass="error" />
            </div>
            <div class="form-group">
                <label>Author *</label>
                <select name="authorId">
                    <option value="">-- Select Author --</option>
                    <c:forEach var="author" items="${authors}">
                        <option value="${author.id}"
                            <c:if test="${author.id == selectedAuthorId}">selected</c:if>>
                            ${author.name}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="actions">
                <button type="submit" class="btn btn-primary">
                    <c:choose>
                        <c:when test="${book.id != null}">Update Book</c:when>
                        <c:otherwise>Save Book</c:otherwise>
                    </c:choose>
                </button>
                <a href="/books" class="btn-cancel">Cancel</a>
            </div>

        </form:form>
    </div>
</div>
</body>
</html>
