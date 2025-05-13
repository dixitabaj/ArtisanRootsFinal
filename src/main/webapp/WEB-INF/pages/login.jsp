<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">

</head>
<body>
	<div class="form-container">
		<h2>Sign in</h2>
		<div class="login">
		<c:if test="${not empty loginError}">
		<span class="error">${loginError}</span>
		</c:if>
			<form action="${pageContext.request.contextPath}/login"  method="post">
				<input class="email" type="text" name="email" placeholder="Email" value="${param.email}"required>
				<input class="password" type="password" name="password" placeholder="Password" value="${param.password}"
					required>

				<div class="options">
					<label><input type="checkbox"> Remember Me</label> <a
						href="#" class="forgot">Forgot password?</a>
				</div>

				<input class="login-btn" type="submit" value="Sign in">
			</form>

			<div class="divider">or continue with</div>
			<div class="social-icons">
				<a href="#"><img src="./resource/images/system/face.png"
					alt="Facebook" class="social-icon" width="50"></a> <a href="#"><img
					src="./resource/images/system/twitter.png" alt="X"
					class="social-icon" width="54"></a> <a href="#"><img
					src="./resource/images/system/instagram.png" alt="Instagram"
					class="social-icon" width="50"></a>
			</div>

			<div class="signup">
				<p>
					Don't have an account? <a href="${pageContext.request.contextPath}/registration2">Sign up now</a>
				</p>
			</div>
		</div>
	</div>

</body>
</html>