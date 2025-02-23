<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
    .avatar-edit input[type="file"] {
        display: none;
    }
    .avatar-edit label {
        position: absolute;
        right: 0;
        bottom: 0;
        background-color: var(--accent-color);
        padding: 8px;
        border-radius: 50%;
        cursor: pointer;
        transition: background-color 0.3s ease;
        z-index: 1000;
    }
    .avatar-edit label:hover {
        background-color: var(--button-hover-color);
    }
</style>

<%@include file="partials/translations.jsp"%>
<form action="${pageContext.request.contextPath}/upload/avatar"
      method="post"
      enctype="multipart/form-data"
      class="position-absolute bottom-0 end-0">
    <label class="btn btn-sm custom-btn-primary rounded-circle p-2">
        <i class="bi bi-camera fs-5"></i>
        <input type="file"
               name="avatar"
               accept="image/*"
               class="d-none"
               onchange="this.form.submit()">
    </label>
</form>

<script>
    (function() {
        const avatarInput = document.getElementById('avatarInput');
        if(avatarInput) {
            avatarInput.addEventListener('change', function(e) {
                const reader = new FileReader();
                reader.onload = function(event) {
                    const profileAvatar = document.querySelector('.profile-avatar');
                    if (profileAvatar) {
                        profileAvatar.src = event.target.result;
                    }
                };
                if (e.target.files[0]) {
                    reader.readAsDataURL(e.target.files[0]);
                }
            });
        }
    })();
</script>
