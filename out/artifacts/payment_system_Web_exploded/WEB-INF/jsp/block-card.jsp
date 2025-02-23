<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<head>
    <title><fmt:message key="block_card.title"/></title>
    <%@include file="partials/head-includes.jsp"%>
</head>
<body>
<%@include file="partials/header.jsp"%>

<div class="container-fluid">
    <div class="row">
        <%@include file="partials/sidebar.jsp"%>

        <div class="col-lg-10 col-xl-10 main-content">
            <div class="card custom-bg-dark border-secondary mb-4">
                <div class="card-header border-secondary d-flex justify-content-between align-items-center">
                    <h3 class="text-accent mb-0">
                        <i class="fas fa-ban me-2"></i>
                        <fmt:message key="block_card.title"/>
                    </h3>
                    <span class="badge bg-danger">
                            <i class="fas fa-lock me-1"></i>
                            ${requestScope.totalBlockedCards} <fmt:message key="block_card.blocked"/>
                        </span>
                </div>

                <div class="card-body">
                    <c:if test="${not empty requestScope.errors}">
                        <div class="alert alert-danger d-flex align-items-center">
                            <i class="fas fa-exclamation-circle me-2"></i>
                                ${requestScope.errors}
                        </div>
                    </c:if>

                    <c:if test="${not empty param.success}">
                        <div class="alert alert-success d-flex align-items-center">
                            <i class="fas fa-check-circle me-2"></i>
                            <fmt:message key="block_card.success"/>
                        </div>
                    </c:if>

                    <div class="blocking-section mb-5">
                        <h5 class="text-muted mb-3">
                            <i class="fas fa-lock-open me-2"></i>
                            <fmt:message key="block_card.available_cards"/>
                        </h5>
                        <form method="post" class="needs-validation" novalidate>
                            <select name="cardId"
                                    class="form-select custom-bg-dark text-light mb-3"
                                    required>
                                <c:forEach items="${requestScope.cards}" var="card">
                                    <c:if test="${card.status != 'BLOCKED'}">
                                        <option value="${card.id}">
                                            •••• ${card.last4Digits}
                                            <span class="text-success ms-2">
                                                    <i class="fas fa-unlock"></i>
                                                    <fmt:message key="status.active"/>
                                                </span>
                                        </option>
                                    </c:if>
                                </c:forEach>
                            </select>
                            <button type="submit" class="btn btn-danger w-100">
                                <i class="fas fa-lock me-2"></i>
                                <fmt:message key="block_card.submit"/>
                            </button>
                        </form>
                    </div>

                    <div class="blocked-list">
                        <h5 class="text-muted mb-3">
                            <i class="fas fa-lock me-2"></i>
                            <fmt:message key="block_card.blocked_cards"/>
                        </h5>
                        <div class="list-group">
                            <c:forEach items="${requestScope.cards}" var="card">
                                <c:if test="${card.status == 'BLOCKED'}">
                                    <div class="list-group-item custom-bg-dark border-secondary">
                                        <div class="d-flex justify-content-between align-items-center">
                                            <div>
                                                    <span class="text-danger">
                                                        •••• ${card.last4Digits}
                                                    </span>
<%--                                                <small class="text-muted ms-3">--%>
<%--                                                    <i class="fas fa-calendar-alt me-1"></i>--%>
<%--                                                    <fmt:formatDate value="${card.blockedAt}"--%>
<%--                                                                    pattern="dd.MM.yyyy HH:mm"/>--%>
<%--                                                </small>--%>
                                            </div>
                                            <span class="badge bg-danger">
                                                    <i class="fas fa-lock me-1"></i>
                                                    <fmt:message key="status.blocked"/>
                                                </span>
                                        </div>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="partials/footer.jsp"%>
<%@include file="partials/scripts-includes.jsp"%>
</body>
</html>