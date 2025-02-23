<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="partials/translations.jsp"%>
<form action="${pageContext.request.contextPath}/logout" method="post" class="btn btn-outline-light m-0">
  <button type="submit"
          class="btn btn-danger d-flex align-items-center gap-2">
    <i class="fas fa-sign-out-alt me-2"></i>
    <span style="font-size: 16px; font-weight: 600"><fmt:message key="profile.logout"/></span>
  </button>
</form>