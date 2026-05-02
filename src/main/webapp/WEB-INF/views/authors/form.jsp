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
        .container { max-width: 600px; margin: 40px auto; padding: 0 20px; }
        .card { background: white; border-radius: 10px; padding: 35px;
                box-shadow: 0 4px 16px rgba(0,0,0,0.08); }
        h1 { margin-bottom: 24px; color: #2c3e50; }
        .form-group { margin-bottom: 18px; }
        label { display: block; font-weight: 600; margin-bottom: 6px; font-size: 14px; color: #555; }
        input[type="text"], input[type="email"], input[type="number"] {
            width: 100%; padding: 10px 14px; border: 1px solid #ddd;
            border-radius: 6px; font-size: 15px; }
        input:focus { outline: none; border-color: #3498db; box-shadow: 0 0 0 3px rgba(52,152,219,0.15); }
        .error { color: #e74c3c; font-size: 13px; margin-top: 4px; }
        .alert-error { background: #f8d7da; border: 1px solid #f5c6cb;
                       color: #721c24; padding: 12px 18px; border-radius: 6px; margin-bottom: 16px; }
        .btn { padding: 10px 24px; border-radius: 6px; font-size: 15px;
               font-weight: 600; border: none; cursor: pointer; }
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
        <h1>👤 ${pageTitle}</h1>

        <c:if test="${not empty errorMessage}">
            <div class="alert-error">${errorMessage}</div>
        </c:if>

        <c:choose>
            <c:when test="${author.id != null}">
                <form:form action="/authors/update/${author.id}" method="post" modelAttribute="author">
            </c:when>
            <c:otherwise>
                <form:form action="/authors/save" method="post" modelAttribute="author">
            </c:otherwise>
        </c:choose>

            <div class="form-group">
                <label>Name *</label>
                <form:input path="name" placeholder="e.g. Jane Austen" />
                <form:errors path="name" cssClass="error" />
            </div>
            <div class="form-group">
                <label>Email *</label>
                <form:input path="email" placeholder="e.g. jane@example.com" />
                <form:errors path="email" cssClass="error" />
            </div>
            <div class="form-group">
                <label>Nationality</label>
                <form:input path="nationality" placeholder="e.g. British" />
                <form:errors path="nationality" cssClass="error" />
            </div>
            <div class="form-group">
                <label>Birth Year</label>
                <form:input path="birthYear" type="number" placeholder="e.g. 1775" />
                <form:errors path="birthYear" cssClass="error" />
            </div>

            <div class="actions">
                <button type="submit" class="btn btn-primary">
                    <c:choose>
                        <c:when test="${author.id != null}">Update Author</c:when>
                        <c:otherwise>Save Author</c:otherwise>
                    </c:choose>
                </button>
                <a href="/authors" class="btn-cancel">Cancel</a>
            </div>

        </form:form>
    </div>
</div>
</body>
</html>
