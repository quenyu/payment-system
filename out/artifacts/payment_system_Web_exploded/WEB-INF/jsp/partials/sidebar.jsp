<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="col-lg-2 col-xl-2 d-none d-lg-block sidebar">
    <div class="d-flex flex-column gap-2">
        <a href="${pageContext.request.contextPath}/payments" class="d-flex align-items-center text-decoration-none">
            <div class="nav-icon">
                <i class="fas fa-receipt"></i>
            </div>
            <span><fmt:message key="menu.payments"/></span>
        </a>
        <a href="${pageContext.request.contextPath}/transfers" class="d-flex align-items-center text-decoration-none">
            <div class="nav-icon">
                <i class="fas fa-exchange-alt"></i>
            </div>
            <span><fmt:message key="menu.transfers"/></span>
        </a>
        <a href="${pageContext.request.contextPath}/block-card" class="d-flex align-items-center text-decoration-none">
            <div class="nav-icon">
                <i class="fas fa-ban"></i>
            </div>
            <span><fmt:message key="menu.block_card"/></span>
        </a>
        <a href="${pageContext.request.contextPath}/account-settings" class="d-flex align-items-center text-decoration-none">
            <div class="nav-icon">
                <i class="fas fa-cog"></i>
            </div>
            <span><fmt:message key="menu.account_settings"/></span>
        </a>
<%--        <a href="${pageContext.request.contextPath}/close-account" class="d-flex align-items-center text-decoration-none">--%>
<%--            <div class="nav-icon">--%>
<%--                <i class="fas fa-times-circle"></i>--%>
<%--            </div>--%>
<%--            <span><fmt:message key="menu.close_account"/></span>--%>
<%--        </a>--%>
        <c:if test="${sessionScope.user.role == 'ADMIN'}">
            <a href="${pageContext.request.contextPath}/admin-block-card" class="d-flex align-items-center text-decoration-none">
                <div class="nav-icon">
                    <i class="fas fa-lock"></i>
                </div>
                <span><fmt:message key="menu.block_card_admin"/></span>
            </a>
        </c:if>
        <a href="${pageContext.request.contextPath}/download-report" class="d-flex align-items-center text-decoration-none">
            <div class="nav-icon">
                <i class="fas fa-download"></i>
            </div>
            <span><fmt:message key="menu.download_report"/></span>
        </a>
    </div>
</div>