<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Profile</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap" rel="stylesheet">
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
            background-color: var(--background-color);
            color: var(--text-color);
            font-family: 'Inter', sans-serif;
        }

        .custom-bg-dark {
            background-color: var(--primary-color) !important;
            border-color: var(--secondary-color) !important;
        }

        .custom-btn-primary {
            background-color: var(--accent-color) !important;
            border-color: var(--accent-color) !important;
        }

        .custom-btn-primary:hover {
            background-color: var(--button-hover-color) !important;
            border-color: var(--button-hover-color) !important;
        }

        .profile-card {
            max-width: 800px;
            margin: 0 auto;
        }

        .action-buttons {
            display: flex;
            justify-content: center;
            gap: 1rem;
            margin-top: 2rem;
        }

        .btn-home {
            background-color: var(--success-color) !important;
            border-color: var(--success-color) !important;
        }

        .btn-home:hover {
            background-color: #6B9C87 !important;
            border-color: #6B9C87 !important;
        }

        .account-item {
            background: var(--secondary-color);
            transition: transform 0.2s;
        }

        .account-item:hover {
            transform: translateY(-2px);
            box-shadow: 0 3px 10px rgba(0,0,0,0.1);
        }

        .btn-close-account {
            border: 1px solid var(--danger-color);
            color: var(--danger-color);
        }

        .btn-close-account:hover {
            background: var(--danger-color);
            color: white;
        }

        .account-item {
            transition: all 0.25s cubic-bezier(0.4, 0, 0.2, 1);
            cursor: pointer;
        }

        .account-item:hover {
            transform: translateY(-2px);
            border-color: var(--accent-color) !important;
            box-shadow: 0 4px 12px rgba(224, 122, 95, 0.15);
        }

        .text-accent {
            color: var(--accent-color) !important;
        }

        .small {
            font-size: 0.875rem;
            color: var(--muted-text-color);
        }

        .font-monospace {
            letter-spacing: 0.05em;
        }
    </style>
</head>
<body>
<%@include file="partials/translations.jsp"%>
<div class="container py-5">
    <div class="card custom-bg-dark border-secondary profile-card">
        <div class="card-header border-secondary p-3">
            <h2 class="text-center mb-0" style="color: var(--accent-color);">
                ${sessionScope.user.firstName} ${sessionScope.user.lastName}
            </h2>
        </div>

        <div class="card-body text-center">
            <div class="position-relative d-inline-block mb-4">
                <img src="${not empty sessionScope.user.image ?
                           pageContext.request.contextPath.concat('/images/web/static/uploads/').concat(sessionScope.user.image) :
                           pageContext.request.contextPath.concat('/images/web/static/default-avatar.png')}"
                     class="rounded-circle border border-4"
                     style="width: 160px; height: 160px; border-color: var(--accent-color) !important;"
                     alt="<fmt:message key="profile.avatar.alt"/>">

                <%@include file="upload-avatar.jsp"%>
            </div>

            <div class="card custom-bg-dark border-secondary mb-4">
                <div class="card-header border-secondary p-3">
                    <h5 class="mb-0 text-accent">
                        <i class="bi bi-wallet2 me-2"></i>
                        <fmt:message key="profile.accounts"/>
                    </h5>
                </div>
                <div class="card-body p-4">
                    <div class="row g-3">
                        <c:forEach items="${requestScope.accounts}" var="account">
                            <div class="col-12">
                                <div class="account-item d-flex justify-content-between align-items-center p-3 rounded-3"
                                     style="background: var(--primary-color); border: 1px solid var(--secondary-color);">
                                    <div class="d-flex flex-column">
                                        <span class="text-muted mb-1 small"><fmt:message key="account.number"/></span>
                                        <h5 class="mb-0 font-monospace">${account.accountNumber}</h5>
                                    </div>

                                    <div class="d-flex flex-column text-end">
                                        <span class="text-muted small"><fmt:message key="account.balance"/></span>
                                        <h4 class="mb-0" style="color: var(--success-color);">
                                            <fmt:formatNumber value="${account.balance}" type="currency"/>
                                        </h4>
                                    </div>

                                    <button class="btn btn-link text-danger p-2"
                                            onclick="confirmCloseAccount(${account.id})"
                                            data-bs-toggle="tooltip"
                                            title="<fmt:message key="account.close.tooltip"/>">
                                        <i class="bi bi-x-circle-fill fs-5"></i>
                                    </button>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>

            <div class="row mt-4 g-3">
                <div class="col-md-6">
                    <div class="card custom-bg-dark border-secondary h-100">
                        <div class="card-header border-secondary p-3">
                            <h5 class="mb-0"><fmt:message key="profile.basic_info"/></h5>
                        </div>
                        <div class="card-body">
                            <dl class="row mb-0">
                                <dt class="col-sm-5 text-muted"><fmt:message key="profile.id"/></dt>
                                <dd class="col-sm-7">#${sessionScope.user.id}</dd>

                                <dt class="col-sm-5 text-muted"><fmt:message key="profile.email"/></dt>
                                <dd class="col-sm-7">${sessionScope.user.email}</dd>

                                <dt class="col-sm-5 text-muted"><fmt:message key="profile.language"/></dt>
                                <dd class="col-sm-7">
                                    <fmt:message key="language.${sessionScope.user.preferredLanguage}"/>
                                </dd>
                            </dl>
                        </div>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="card custom-bg-dark border-secondary h-100">
                        <div class="card-header border-secondary p-3">
                            <h5 class="mb-0"><fmt:message key="profile.financial_info"/></h5>
                        </div>
                        <div class="card-body">
                            <dl class="row mb-0">
                                <dt class="col-sm-5 text-muted"><fmt:message key="profile.total_balance"/></dt>
                                <dd class="col-sm-7">
                                    <fmt:formatNumber value="${requestScope.totalBalance}"
                                                      type="currency"
                                                      currencyCode="RUB"/>
                                </dd>

                                <dt class="col-sm-5 text-muted"><fmt:message key="profile.active_cards"/></dt>
                                <dd class="col-sm-7">${requestScope.activeCardsCount}</dd>

                                <dt class="col-sm-5 text-muted"><fmt:message key="profile.last_payment"/></dt>
                                <dd class="col-sm-7">
                                    <c:if test="${not empty requestScope.lastPayment}">
                                        <fmt:formatDate value="${requestScope.lastPayment.paymentDate}"
                                                        dateStyle="medium"/>
                                    </c:if>
                                </dd>
                            </dl>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Кнопки действий -->
            <div class="action-buttons">
                <a href="${pageContext.request.contextPath}/"
                   class="btn btn-home d-flex align-items-center gap-2">
                    <i class="bi bi-house"></i>
                    <fmt:message key="profile.home"/>
                </a>

                <a href="${pageContext.request.contextPath}/account-settings"
                   class="btn custom-btn-primary d-flex align-items-center gap-2">
                    <i class="bi bi-gear"></i>
                    <fmt:message key="profile.settings"/>
                </a>

                <form action="${pageContext.request.contextPath}/logout" method="post" class="m-0">
                    <button type="submit" class="btn btn-danger d-flex align-items-center gap-2">
                        <i class="bi bi-box-arrow-right"></i>
                        <fmt:message key="profile.logout"/>
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css">
<script>
    function confirmCloseAccount(accountId) {
        if (confirm('<fmt:message key="account.close.confirm"/>')) {
            window.location = '${pageContext.request.contextPath}/account-settings?action=delete&id=' + accountId;
        }
    }
</script>
</body>
</html>