<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Account Settings</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
    <style>
        .text-light-60 { color: rgba(255,255,255,0.6); }

        .bg-dark-gradient {
            background: linear-gradient(45deg, #1a1a1a 0%, #2d2d2d 100%);
        }

        .hover-shadow {
            transition: transform 0.2s, box-shadow 0.2s;
        }

        .hover-shadow:hover {
            transform: translateY(-2px);
            box-shadow: 0 0.5rem 1rem rgba(0,0,0,0.15);
        }

        @keyframes shine {
            0% { transform: translateX(-100%) rotate(45deg); }
            100% { transform: translateX(100%) rotate(45deg); }
        }
    </style>
</head>
<body>
<%@include file="partials/translations.jsp"%>
<%@include file="partials/header.jsp"%>

<div class="container-fluid">
    <div class="row">
        <%@include file="partials/sidebar.jsp"%>

        <div class="col-lg-10 col-xl-10 main-content">
            <!-- Карточка создания счета -->
            <div class="card custom-bg-dark border-secondary mb-4">
                <div class="card-header border-secondary bg-dark-gradient">
                    <h3 class="text-accent mb-0 p-md-2" style="font-size: 1.75rem;">
                        <i class="fas fa-wallet me-2"></i>
                        <fmt:message key="account.create.title"/>
                    </h3>
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/account-settings" method="post">
                        <div class="row g-3 align-items-center">
                            <!-- Выбор валюты -->
                            <div class="col-md-8">
                                <div class="input-group">
                        <span class="input-group-text bg-dark border-secondary text-accent">
                            <i class="fas fa-coins me-2"></i>
                            <fmt:message key="account.currency"/>
                        </span>
                                    <select class="form-select custom-bg-dark border-secondary text-light"
                                            name="currency"
                                            required
                                            style="cursor: pointer;">
                                        <option value="USD" class="text-light">
                                            <i class="fas fa-dollar-sign me-2"></i> USD - United States Dollar
                                        </option>
                                        <option value="EUR" class="text-light">
                                            <i class="fas fa-euro-sign me-2"></i> EUR - Euro
                                        </option>
                                        <option value="RUB" class="text-light">
                                            <i class="fas fa-ruble-sign me-2"></i> RUB - Russian Ruble
                                        </option>
                                    </select>
                                </div>
                            </div>

                            <!-- Кнопка создания -->
                            <div class="col-md-4">
                                <button type="submit"
                                        class="btn btn-success w-100 py-2 fw-bold border-0"
                                        style="
                                        background: linear-gradient(145deg, #28a745, #218838);
                                        box-shadow: 0 4px 6px rgba(0,0,0,0.1);
                                        transition: all 0.3s ease;
                                        position: relative;
                                ">
                                    <i class="fas fa-plus-circle me-2"></i>
                                    <fmt:message key="account.create.button"/>
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

            <!-- Уведомления -->
            <c:if test="${not empty param.error}">
                <div class="alert alert-danger mt-3 border-danger shadow-sm">
                    <i class="fas fa-exclamation-circle me-2"></i>
                        ${param.error}
                </div>
            </c:if>

            <c:if test="${not empty param.success}">
                <div class="alert alert-success mt-3 border-success shadow-sm">
                    <i class="fas fa-check-circle me-2"></i>
                    <fmt:message key="account.create.success"/>
                </div>
            </c:if>

            <!-- Список счетов -->
            <div class="card custom-bg-dark border-secondary">
                <div class="card-header border-secondary bg-dark-gradient">
                    <h3 class="text-accent mb-0 p-2" style="font-size: 1.5rem;">
                        <i class="fas fa-list-ul me-2"></i>
                        <fmt:message key="account.existing.title"/>
                    </h3>
                </div>
                <div class="card-body">
                    <div class="row g-4">
                        <c:forEach items="${requestScope.accounts}" var="account">
                            <div class="col-md-6">
                                <div class="card account-item custom-bg-dark border-secondary hover-shadow">
                                    <div class="card-body">
                                        <div class="d-flex justify-content-between align-items-center">
                                            <div>
                                                <h5 class="font-monospace text-light mb-0">
                                                        ${account.accountNumber}
                                                </h5>
                                            </div>
                                            <div class="text-end">
                                                <h4 class="text-success mb-0">
                                                    <fmt:formatNumber value="${account.balance}"
                                                                      type="currency"
                                                                      currencyCode="${account.currency}"/>
                                                </h4>
                                                <small class="text-light-60">
                                                    <fmt:message key="account.created"/>
                                                    <fmt:formatDate value="${account.createdAt}"
                                                                    pattern="dd.MM.yyyy"/>
                                                </small>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="partials/footer.jsp"%>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>