<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PaySystem | Payments</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
<%--    <style></style>--%>
</head>
<body>
<%@include file="partials/translations.jsp"%>

<c:if test="${empty sessionScope.user}">
    <c:redirect url="/WEB-INF/jsp/index.jsp"/>
</c:if>

<%@include file="partials/header.jsp"%>

<div class="container-fluid">
    <div class="row">
        <%@include file="partials/sidebar.jsp"%>

        <div class="col-lg-10 col-xl-10 main-content">
            <div class="mb-5">
                <h1 class="fs-3 fw-bold mb-3">
                    <fmt:message key="welcome.title">
                        <fmt:param value="${sessionScope.user.firstName}"/>
                    </fmt:message>
                </h1>
                <p class="text-muted mb-4" style="font-size: 1.1rem;">
                    <fmt:message key="welcome.subtitle"/>
                </p>
            </div>

            <div class="row g-4 mb-5">
                <div class="col-12 col-md-6">
                    <div class="card stat-card">
                        <div class="card-body">
                            <div class="d-flex align-items-center mb-4">
                                <div class="bg-secondary p-3 rounded-circle me-3">
                                    <i class="fas fa-wallet fa-2x text-accent"></i>
                                </div>
                                <div>
                                    <h5 class="card-title text-muted mb-1">
                                        <fmt:message key="card.balance"/>
                                    </h5>
                                    <h2 class="mb-0 fw-bold">
                                        <fmt:formatNumber value="${requestScope.totalBalance}"
                                                          type="currency"
                                                          currencyCode="USD"/>
                                    </h2>
                                </div>
                            </div>
                            <div class="d-flex justify-content-between align-items-center">
                                <span class="text-success">
                                    <i class="fas fa-arrow-up me-2"></i>
                                    <fmt:message key="percentage.change">
                                        <fmt:param value="2.5"/>
                                    </fmt:message>
                                </span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-12 col-md-6">
                    <div class="card stat-card">
                        <div class="card-body">
                            <div class="d-flex align-items-center mb-4">
                                <div class="bg-secondary p-3 rounded-circle me-3">
                                    <i class="fas fa-credit-card fa-2x text-accent"></i>
                                </div>
                                <div>
                                    <h5 class="card-title text-muted mb-1">
                                        <fmt:message key="card.credit"/>
                                    </h5>
                                    <div class="d-flex align-items-center">
                                        <h4 class="mb-0 fw-bold me-3">•••• ${requestScope.creditCard.last4}</h4>
                                        <c:if test="${requestScope.creditCard.blocked}">
                                            <span class="badge bg-danger"><fmt:message key="status.blocked"/></span>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                            <div class="text-muted">
                                <fmt:message key="card.expires">
                                    <fmt:param value="${requestScope.creditCard.expiryDate}"/>
                                </fmt:message>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="card">
                <div class="card-header bg-transparent border-0 py-4">
                    <h3 class="mb-0"><fmt:message key="transactions.recent"/></h3>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table transaction-table">
                            <thead class="text-muted">
                            <tr>
                                <th><fmt:message key="transactions.date"/></th>
                                <th><fmt:message key="transactions.description"/></th>
                                <th><fmt:message key="transactions.amount"/></th>
                                <th><fmt:message key="transactions.status"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${requestScope.transactions}" var="transaction">
                                <tr class="align-middle">
                                    <td class="py-3">
                                        <span class="fw-bold text-light">
                                            <fmt:formatDate value="${transaction.paymentDate}" pattern="dd.MM.yyyy HH:mm"/>
                                        </span>
                                    </td>
                                    <td class="py-3">
                                        <div class="d-flex flex-column">
                                            <span class="text-muted small">
                                                <fmt:message key="transactions.from"/>
                                                ${transaction.fromAccountNumber}
                                            </span>
                                            <span class="text-muted small">
                                            <fmt:message key="transactions.to"/>
                                            ${transaction.toAccountNumber}
                                        </span>
                                        </div>
                                    </td>
                                    <td class="py-3 fw-bold ${transaction.outgoing ? 'text-danger' : 'text-success'}">
                                        <c:if test="${transaction.outgoing}">-</c:if>
                                        <fmt:formatNumber value="${transaction.amount}"
                                                          type="currency"
                                                          currencyCode="${transaction.currency}"/>
                                    </td>
                                    <td>
                                        <span class="badge ${transaction.status == 'COMPLETED' ? 'bg-success' :
                                                          transaction.status == 'PENDING' ? 'bg-warning text-dark' : 'bg-danger'}">
                                            <fmt:message key="status.${fn:toLowerCase(transaction.status)}"/>
                                        </span>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <%@include file="partials/footer.jsp"%>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
