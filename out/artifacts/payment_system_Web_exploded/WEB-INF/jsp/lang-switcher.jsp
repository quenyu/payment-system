<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
  .language-switcher {
    background: var(--secondary-color);
    color: var(--text-color);
    border: 2px solid var(--accent-color);
    border-radius: 6px;
    padding: 0.25rem 0.5rem;
    font-size: 0.9rem;
    cursor: pointer;
    transition: all 0.3s ease;
    white-space: nowrap;
  }

  .language-switcher:focus {
    outline: none;
    border-color: var(--success-color);
  }
</style>

<script>
  function changeLanguage() {
    const lang = document.querySelector('.language-switcher').value;
    window.location.href = '<%= request.getContextPath() %>/locale?lang=' + lang;
  }
</script>

<%@include file="partials/translations.jsp"%>
<select class="language-switcher" onchange="changeLanguage()">
  <option value="en_US" ${sessionScope.lang == 'en_US' ? 'selected' : ''}>English</option>
  <option value="ru_RU" ${sessionScope.lang == 'ru_RU' ? 'selected' : ''}>Русский</option>
</select>