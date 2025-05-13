<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.ArtisanRoots7.model.UserModel" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Profile</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/portfolio.css">
<style>
/* Gender Radio Button Styling */
.gender-options {
    display: flex;
    gap: 20px;
    margin-top: 8px;
}

.radio-container {
    display: flex;
    align-items: center;
    position: relative;
    padding-left: 25px;
    cursor: pointer;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
}

.radio-container input {
    position: absolute;
    opacity: 0;
    cursor: pointer;
}

.radio-checkmark {
    position: absolute;
    top: 0;
    left: 0;
    height: 18px;
    width: 18px;
    background-color: #fff;
    border-radius: 50%;
    border: 2px solid #8B5A2B;
}

.radio-container:hover input ~ .radio-checkmark {
    background-color: #f1f1f1;
}

.radio-container input:checked ~ .radio-checkmark {
    background-color: #fff;
}

.radio-checkmark:after {
    content: "";
    position: absolute;
    display: none;
}

.radio-container input:checked ~ .radio-checkmark:after {
    display: block;
}

.radio-container .radio-checkmark:after {
    top: 3px;
    left: 3px;
    width: 12px;
    height: 12px;
    border-radius: 50%;
    background: #8B5A2B;
}

.radio-label {
    margin-left: 5px;
    color: #333;
}</style>
</head>
<body>

<jsp:include page="header.jsp" />

<div class="main-container">
    <!-- Profile Section -->
    <div class="profile-section">
        <div class="profile-picture">
        <%
    String profileImage = (String) session.getAttribute("profileImage");
    String path = request.getContextPath() + profileImage;
                   
%>
<img src="/ArtisanRoots3/resource/images/system/<%= profileImage %>" 
     alt="Profile" >
        </div>
        
        <% UserModel loggedInUser = (UserModel) session.getAttribute("user"); %>
        <div class="profile-info">
            <h2><%= loggedInUser.getFirstName() %> <%= loggedInUser.getLastName() %></h2>
            <p><strong>Email:</strong> <%= loggedInUser.getEmail() %></p>
            <p><strong>Phone:</strong> <%= loggedInUser.getPhone() %></p>
            <p><strong>Date of Birth:</strong> <%= loggedInUser.getDob() %></p>
            <p><strong>Gender:</strong> <%= loggedInUser.getGender() %></p>
        </div>
    </div>

    <!-- Form Section -->
    <div class="form-section">
     <div class="settings-header">
            <h2>Account Settings</h2>
           <div class="tabs">
            <button class="tab-btn active" onclick="showTab('account')">Account</button>
            <button class="tab-btn" onclick="showTab('password')">Password</button>
        </div>
        </div>
        
        
        <!-- Account Form -->
       <%
    String activeTab = (String) request.getAttribute("activeTab");
    boolean showAccount = "account".equals(activeTab) || activeTab == null;
    boolean showPassword = "password".equals(activeTab);
%>

<div id="accountForm" class="form-content <%= showAccount ? "active" : "" %>">

            <form action="${pageContext.request.contextPath}/portfolio" method="post">

                <div class="form-grid">
                    <div class="form-group">
                        <label for="firstName">First Name</label>
                        <input type="text" id="firstName" name="firstName" value="<%= loggedInUser.getFirstName() %>" required>
                         <span class="error">${firstNameError}</span>
                    </div>
                    
                    <div class="form-group">
                        <label for="lastName">Last Name</label>
                        <input type="text" id="lastName" name="lastName" value="<%= loggedInUser.getLastName() %>" required>
                         <span class="error">${lastNameError}</span>
                    </div>
                    
                    <div class="form-group">
                        <label for="username">Username</label>
                        <input type="text" id="username" name="username" value="<%= loggedInUser.getUsername() %>" required>
                         <span class="error">${usernameError}</span>
                    </div>
                    
                    <!-- Phone Number Field -->
                    <div class="form-group">
                        <label for="phone">Phone Number</label>
                        <input type="text" id="phone" name="phone" value="<%= loggedInUser.getPhone() %>" required>
                         <span class="error">${phoneNoError}</span>
                    </div>
</div>
                    <!-- Gender Selection -->
                    <div class="form-group">
                        <label>Gender</label>
                        <div class="second-container">
    
    </div>
    <!-- Rest of your form... -->
</div>
                        <input type="radio" id="male" name="gender" value="Male" <%= loggedInUser.getGender().equals("Male") ? "checked" : "" %> required>
                        <label for="male">Male</label>

                        <input type="radio" id="female" name="gender" value="Female" <%= loggedInUser.getGender().equals("Female") ? "checked" : "" %> required>
                        <label for="female">Female</label>

                        <input type="radio" id="other" name="gender" value="Other" <%= loggedInUser.getGender().equals("Other") ? "checked" : "" %> required>
                        <label for="other">Other</label>
                       
                   
                    
                    
                
                <div class="form-actions">
                    <button type="reset" class="btn btn-secondary">Reset</button>
                    <button type="submit" class="btn btn-primary">Save Changes</button>
                </div>
                </form>
               </div>
               
         
        <!-- Password Form -->
        <div id="passwordForm" class="form-content">
            <form action="${pageContext.request.contextPath}/updatepassword" method="post">
            
                <div class="form-grid">
                    <div class="form-group">
                    
                        <label for="currentPassword">Current Password</label>
                        <input type="password" id="currentPassword" name=currentPassword value="${param.currentPass}" required>
                         <span class="error">${enteredPassError}</span>
                    </div>
                    
                    <div class="form-group">
                        <label for="newPassword">New Password</label>
                        <input type="password" id="newPassword" name="newPassword" value="${param.newPass}" required>
                         <span class="error">${newPasswordError}</span>
                    </div>
                    
                    <div class="form-group">
                        <label for="confirmPassword">Confirm Password</label>
                        <input type="password" id="confirmPassword" name="confirmPassword" value="${param.confirmPass}" required>
                    	<span class="error">${confirmPasswordError}</span>
                    </div>
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Update Password</button>
                </div>
            </form>
        </div>
    </div>


<script>
function showTab(tabName) {
    // Hide all form contents
    document.querySelectorAll('.form-content').forEach(content => {
        content.classList.remove('active');
    });
    
    // Deactivate all tabs
    document.querySelectorAll('.tab-btn').forEach(btn => {
        btn.classList.remove('active');
    });
    
    // Show selected tab content
    document.getElementById(tabName + 'Form').classList.add('active');
    
    // Activate clicked tab
    event.currentTarget.classList.add('active');
}
</script>

</body>
</html>
