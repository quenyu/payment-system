<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Transfer Funds - PaySystem</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
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
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
    }
    body {
      background-color: var(--background-color);
      color: var(--text-color);
      font-family: 'Inter', sans-serif;
    }
    .navbar {
      background: var(--primary-color);
      padding: 1rem 2rem;
      border-bottom: 1px solid var(--secondary-color);
    }
    .navbar-brand {
      color: var(--text-color);
      font-weight: 600;
    }
    .auth-buttons {
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 15px;
    }
    .auth-buttons .btn {
      padding: 0.4rem 0.75rem;
      font-size: 0.85rem;
      border-width: 2px;
      border-radius: 4px;
      transition: background-color 0.3s, border-color 0.3s;
      white-space: nowrap;
    }
    .auth-buttons .btn.btn-outline-light {
      background-color: transparent;
      border-color: var(--muted-text-color);
      color: var(--muted-text-color);
    }
    .auth-buttons .btn.btn-outline-light:hover {
      background-color: var(--secondary-color);
      border-color: var(--accent-color);
      color: var(--text-color);
    }
    .auth-buttons .btn.btn-primary {
      background-color: var(--accent-color);
      border-color: var(--accent-color);
      color: var(--text-color);
    }
    .auth-buttons .btn.btn-primary:hover {
      background-color: var(--button-hover-color);
      border-color: var(--button-hover-color);
    }
    .auth-section {
      display: flex;
      align-items: center;
      gap: 1.5rem;
    }
    .btn-outline-light {
      border-color: var(--accent-color);
      color: var(--accent-color);
      transition: all 0.3s ease;
    }
    .btn-outline-light:hover {
      background: var(--accent-color);
      color: var(--text-color);
      transform: translateY(-1px);
    }
    .btn-primary {
      background: var(--accent-color);
      transition: all 0.3s ease;
    }
    .btn-primary:hover {
      background: var(--button-hover-color);
      transform: translateY(-1px);
    }
    .sidebar {
      background: var(--primary-color);
      min-height: calc(100vh - 100px);
      padding: 2rem 1rem;
      border-right: 1px solid var(--secondary-color);
    }
    .nav-icon {
      width: 40px;
      height: 40px;
      border-radius: 8px;
      background: var(--secondary-color);
      display: flex;
      align-items: center;
      justify-content: center;
      margin-right: 0.75rem;
    }
    .sidebar a {
      color: var(--text-color);
      padding: 0.75rem;
      border-radius: 6px;
      transition: background 0.2s;
    }
    .sidebar a:hover {
      background: var(--secondary-color);
    }
    .main-content {
      padding: 2rem;
    }
    .card {
      background: var(--primary-color);
      border: none;
      border-radius: 10px;
      box-shadow: 0 4px 12px rgba(0,0,0,0.05);
      transition: transform 0.2s ease;
    }
    .card:hover {
      transform: translateY(-3px);
    }
    .card-body {
      color: var(--text-color);
    }
    .transfer-form .form-control, .transfer-form .form-select {
      border: 1px solid var(--secondary-color);
      color: var(--text-color);
      background-color: var(--primary-color);
      transition: all 0.25s cubic-bezier(0.4, 0, 0.2, 1);
    }
    .transfer-form .form-control:focus, .transfer-form .form-select:focus {
      border-color: var(--accent-color);
      box-shadow: 0 4px 12px rgba(224, 122, 95, 0.15);
    }
    .input-group-text {
      background: var(--secondary-color) !important;
      border-color: var(--accent-color) !important;
      color: var(--text-color) !important;
    }
    .text-muted {
      color: var(--muted-text-color) !important;
    }
  </style>
</head>
<body>
<%@include file="partials/translations.jsp"%>

<c:if test="${empty sessionScope.user}">
  <c:redirect url="/login"/>
</c:if>

<%@include file="partials/header.jsp"%>

<div class="container-fluid">
  <div class="row">
    <%@include file="partials/sidebar.jsp"%>
    <div class="col-lg-10 col-xl-10 main-content">
      <div class="mb-5">
        <h1 class="fs-3 fw-bold mb-3"><fmt:message key="transfer.page_title"/></h1>
        <p class="text-muted mb-4" style="font-size: 1.1rem;"><fmt:message key="transfer.subtitle"/></p>
      </div>
      <div class="card">
        <div class="card-header bg-transparent border-0 py-4">
          <h3 class="mb-0"><fmt:message key="transfer.title"/></h3>
        </div>
        <div class="card-body">
          <c:if test="${not empty requestScope.errors}">
            <div class="alert alert-danger">
              <ul>
                <c:forEach items="${requestScope.errors}" var="error">
                  <li>${error.message}'|' ${error.code}</li>
                </c:forEach>
              </ul>
            </div>
          </c:if>

          <form class="transfer-form" action="${pageContext.request.contextPath}/transfers" method="post">
            <div class="row g-3">
              <div class="col-md-6">
                <label class="form-label text-muted"><fmt:message key="transfer.from_account"/></label>
                <select class="form-select" name="fromAccount" required>
                  <c:forEach items="${requestScope.accounts}" var="account">
                    <option value="${account.id}">
                        ${account.accountNumber}
                      (<fmt:formatNumber value="${account.balance}"
                                         type="currency"
                                         currencyCode="${account.currency}"/>)
                    </option>
                  </c:forEach>
                </select>
              </div>
              <div class="col-md-6">
                <label class="form-label text-muted"><fmt:message key="transfer.to_account"/></label>
                <input type="text"
                       class="form-control"
                       name="toAccountNumber"
                       pattern="[A-Za-z]{2}\d{18}"
                       title="Account number format: AA1234567890123456"
                       placeholder="Enter account number"
                       required>
              </div>
              <div class="col-12">
                <label class="form-label text-muted"><fmt:message key="transfer.amount"/></label>
                <div class="input-group">
                  <span class="input-group-text"><i class="fas fa-dollar-sign"></i></span>
                  <input type="number"
                         class="form-control"
                         name="amount"
                         step="0.01"
                         min="0.01"
                         max="1000000"
                         required>
                </div>
              </div>
              <div class="col-12">
                <button type="submit" class="btn btn-primary w-100 py-2">
                  <fmt:message key="transfer.submit"/>
                </button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>

<%@include file="partials/footer.jsp"%>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>