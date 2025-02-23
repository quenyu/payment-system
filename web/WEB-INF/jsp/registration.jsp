<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <title>Registration</title>
    <style>
        :root {
            --primary-color: #1A1A1A;
            --secondary-color: #2C2C2C;
            --accent-color: #E07A5F;
            --background-color: #121212;
            --text-color: #EDEDED;
            --success-color: #81B29A;
            --danger-color: #E07A5F;
            --muted-text-color: #9A9A9A;
            --button-hover-color: #C86A54;
        }
        body {
            font-family: 'Inter', sans-serif;
            background-color: var(--background-color);
            color: var(--text-color);
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        .grid-container {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 60px;
            padding: 30px;
            max-width: 1000px;
            width: 100%;
            background: var(--primary-color);
            border-radius: 12px;
            border: 1px solid var(--secondary-color);
            box-shadow: 0 4px 12px rgba(0,0,0,0.2);
        }

        .form-container h2 {
            margin-bottom: 30px;
            font-size: 32px;
            color: var(--text-color);
            text-align: center;
        }

        .form-group {
            margin-bottom: 1.5rem;
        }

        .form-group label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: 500;
            color: var(--muted-text-color);
        }

        .form-group input,
        .form-group select {
            font-size: 16px;
            width: 100%;
            padding: 0.75rem;
            background: var(--secondary-color);
            border: 2px solid var(--secondary-color);
            border-radius: 6px;
            color: var(--text-color);
            transition: border-color 0.3s ease;
        }

        .form-group input:focus,
        .form-group select:focus {
            outline: none;
            border-color: var(--accent-color);
        }

        .error-messages {
            background-color: rgba(224, 122, 95, 0.15);
            color: var(--danger-color);
            border: 1px solid var(--danger-color);
            border-radius: 6px;
            padding: 1rem;
            margin-bottom: 1.5rem;
        }

        .error-messages ul {
            padding-left: 20px;
            margin: 0;
        }

        button {
            font-size: 1.25rem;
            width: 100%;
            padding: 1rem;
            background: var(--accent-color);
            color: var(--text-color);
            border: none;
            border-radius: 6px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        button:hover {
            background: var(--button-hover-color);
            transform: translateY(-1px);
        }

        .login-link {
            text-align: center;
            margin-top: 1.5rem;
            color: var(--muted-text-color);
        }

        .login-link a {
            color: var(--accent-color);
            text-decoration: none;
            transition: color 0.3s ease;
        }

        .login-link a:hover {
            color: var(--button-hover-color);
        }

        .image-container {
            display: flex;
            justify-content: center;
            align-items: center;
            background: var(--secondary-color);
            border-radius: 12px;
            padding: 2rem;
        }

        .image-container img {
            max-width: 100%;
            height: auto;
            border-radius: 8px;
        }
    </style>
</head>
<body>
<%@include file="partials/translations.jsp"%>
<div class="grid-container">
    <div class="form-container">
        <h2>Create Account</h2>

        <c:if test="${not empty requestScope.errors}">
            <div class="error-messages">
                <strong>Registration errors:</strong>
                <ul>
                    <c:forEach var="error" items="${requestScope.errors}">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/registration" method="post">
            <div class="form-group">
                <label for="firstName">First Name</label>
                <input type="text" id="firstName" name="firstName" required>
            </div>

            <div class="form-group">
                <label for="lastName">Last Name</label>
                <input type="text" id="lastName" name="lastName" required>
            </div>

            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" required>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>
            </div>

            <div class="form-group">
                <label for="role">Role</label>
                <select id="role" name="role" required>
                    <c:forEach var="role" items="${requestScope.roles}">
                        <option value="${role}">${role}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="language">Preferred Language</label>
                <select id="language" name="language" required>
                    <option value="ru_RU">Русский</option>
                    <option value="en_US">English</option>
                </select>
            </div>

            <button type="submit">Register</button>
        </form>

        <div class="login-link">
            Already have an account? <a href="${pageContext.request.contextPath}/login">Sign in</a>
        </div>
    </div>

    <div class="image-container">
        <img src="${pageContext.request.contextPath}/images/web/static/banner.jpg" alt="Registration illustration">
    </div>
</div>
</body>
</html>