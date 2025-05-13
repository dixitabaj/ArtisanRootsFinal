<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/registration1.css">



</head>

<body>
<div class="login-form">
<div class="heading">
<p>Registration form</p>
</div>
<form action="${pageContext.request.contextPath}/registration2" method="post" enctype="multipart/form-data">


    <div class="registration-form">

      <!-- Column 1 -->
      <div class="column">
        <div class="row">
         
    <label for="firstName" class="form-label">First Name</label>
    <input type="text" id="firstName" name="firstName" 
           value="${param.firstName}" 
           required>
   <c:if test="${not empty firstNameError}">
        <span class="error">${firstNameError}</span>
    </c:if>
</div>

        <div class="row">
         
          <label for="username">Username</label>
                <input type="text" id="username" name="username" value="${param.username}" required>
       <c:if test="${not empty usernameError}">
        <span class="error">${usernameError}</span>
    </c:if>
        </div>

        <div class="row">
        
                  <label for="phone">Phone</label>
          <input type="text" id="phone" name="phone" value="${param.phone}">
          <c:if test="${not empty phoneError}" >
        <span class="error">${phoneError}</span>
        </c:if>
         
        </div>

        <div class="row">
          <label for="password">Password</label>
          <input type="password" id="password" name="password" value="${param.password}" required>
          <c:if test="${not empty passwordError}">
          <span class="error">${passwordError}</span>
          </c:if>
        </div>

        
      </div>

   
      <div class="column2">
        <div class="row">
          <label for="lastName">Last Name</label>
          <input type="text" id="lastName" name="lastName"  value="${param.lastName}"required>
          <c:if test="${not empty lastNameError}">
          <span class="error">${lastNameError}</span>
          </c:if>
        </div>

        <div class="row">
          <label for="user_dob">Date of Birth</label>
          <input type="date"  name="dob" max="${today}" value="${dob != null ? dob : param.dob}">
          <c:if test="${not empty dobError}">
          <span class="error">${dobError}</span>
          </c:if>
        </div>

        <div class="row">
          <label for="email">Email</label>
          <input type="email"  name="email" value="${param.email}"required>
          <c:if test="${not empty emailError}">
          <span class="error">${emailError}</span>
          </c:if>
        </div>

        <div class="row">
          <label for="confirmPassword">Confirm Password</label>
          <input type="password" id="confirmPassword" name="confirmPassword" value="${param.confirmPassword}" required>
          <c:if test="${not empty confirmPasswordError}">
          <span class="error">${confirmPasswordError}</span>
          </c:if>
        </div>

        
      </div>

    </div>
<div class="second-container">
  <div class="gender-image-row">
  
    <!-- Gender Section -->
    <div class="gender-section">
      <label for="gender">Gender</label><br>

      <input type="radio" id="male" value="male" name="gender" 
             ${gender == 'male' ? 'checked' : ''} required>
      <label for="male">Male</label>

      <input type="radio" id="female" value="female" name="gender" 
             ${gender == 'female' ? 'checked' : ''} required>
      <label for="female">Female</label>

      <input type="radio" id="other" value="other" name="gender" 
             ${gender == 'other' ? 'checked' : ''} required>
      <label for="other">Other</label>
    </div>
<!--   <!-- Profile Picture Section -->
    <div class="image-section">
      <label for="image">Profile Picture:</label><br>
      
     <input type="file" id="profile-picture" name="image" class="file-input">
    </div> 
  

  </div>
</div>




        
    <div class="row">
    <input class="register-button" type="submit" value="Register">

</div>

        <div class="sign-in-container">
        <a href="${pageContext.request.contextPath}/login" class="sign-in-section">
        Already Have an Account?<span class="sign-in">Sign in</span></a></div>
       
  </form>
  
</div>
</body>
</html>




