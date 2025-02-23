<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<head>
  <title><fmt:message key="admin.block_card.title"/></title>
  <%@include file="partials/head-includes.jsp"%>
</head>
<body>
<%@include file="partials/header.jsp"%>

<div class="container-fluid">
  <div class="row">
    <%@include file="partials/sidebar.jsp"%>

    <div class="col-lg-10 col-xl-10 main-content">
      <div class="card custom-bg-dark border-secondary mb-4">
        <div class="card-header border-secondary">
          <h3 class="text-accent mb-0">
            <i class="fas fa-lock me-2"></i>
            <fmt:message key="admin.block_card.title"/>
          </h3>
        </div>

        <div class="card-body">
          <c:if test="${not empty requestScope.error}">
            <div class="alert alert-danger d-flex align-items-center">
              <i class="fas fa-exclamation-circle me-2"></i>
                ${requestScope.errors}
            </div>
          </c:if>

          <c:if test="${not empty param.success}">
            <div class="alert alert-success d-flex align-items-center">
              <i class="fas fa-check-circle me-2"></i>
              <fmt:message key="admin.block_card.success"/>
            </div>
          </c:if>

          <form method="post" class="needs-validation" novalidate>
            <div class="row g-3">
              <div class="col-md-6">
                <label class="form-label text-muted">
                  <fmt:message key="admin.block_card.select_user"/>
                </label>
                <select name="userId" class="form-select custom-bg-dark text-light" required>
                  <option value="" disabled selected>
                    <fmt:message key="admin.block_card.choose_user"/>
                  </option>
                  <c:forEach items="${requestScope.users}" var="user">
                    <option value="${user.id}">
                        ${user.email} (${user.firstName} ${user.lastName})
                    </option>
                  </c:forEach>
                </select>
              </div>

              <div class="col-md-6">
                <label class="form-label text-muted">
                  <fmt:message key="admin.block_card.select_card"/>
                </label>
                <select name="cardId" class="form-select custom-bg-dark text-light" required>
                  <option value="" disabled selected>
                    <fmt:message key="admin.block_card.choose_card"/>
                  </option>
                  <c:forEach items="${requestScope.cards}" var="card">
                    <c:choose>
                      <c:when test="${card.status == 'BLOCKED'}">
                        <option value="${card.id}"
                                disabled
                                class="bg-danger bg-opacity-10">
                          •••• ${card.last4Digits}
                          <i class="fas fa-lock ms-2 text-danger"></i>
                          <small class="text-muted">
                            (${card.userEmail})
                          </small>
                        </option>
                      </c:when>
                      <c:otherwise>
                        <option value="${card.id}">
                          •••• ${card.last4Digits}
                          <span class="text-success ms-2">
                            <i class="fas fa-unlock"></i>
                            (${card.userEmail})
                          </span>
                        </option>
                      </c:otherwise>
                    </c:choose>
                  </c:forEach>
                </select>
              </div>

              <div class="col-12">
                <label class="form-label text-muted">
                  <fmt:message key="admin.block_card.reason"/>
                </label>
                <textarea name="reason"
                          class="form-control custom-bg-dark text-light"
                          rows="3"
                          placeholder="<fmt:message key="admin.block_card.reason_placeholder"/>"
                          required></textarea>
              </div>

              <div class="col-12">
                <button type="submit"
                        class="btn btn-danger w-100 py-2"
                        data-bs-toggle="tooltip"
                        title="<fmt:message key="admin.block_card.warning"/>">
                  <i class="fas fa-lock me-2"></i>
                  <fmt:message key="admin.block_card.submit"/>
                </button>
              </div>
            </div>
          </form>

          <div class="mt-5">
            <h5 class="text-muted mb-4">
              <i class="fas fa-chart-pie me-2"></i>
              <fmt:message key="admin.block_card.statistics"/>
            </h5>
            <div class="row">
              <div class="col-md-4 mb-3">
                <div class="card stat-card bg-dark">
                  <div class="card-body">
                    <h5 class="text-muted">
                      <fmt:message key="admin.block_card.total_blocked"/>
                    </h5>
                    <h2 class="text-danger">${requestScope.totalBlockedCards}</h2>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<%@include file="partials/footer.jsp"%>
<%@include file="partials/scripts-includes.jsp"%>
<script>
  const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]');
  const tooltipList = [...tooltipTriggerList].map(t => new bootstrap.Tooltip(t));
</script>
</body>
</html>