<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Artisan Roots</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">

</head>
<body>
<div class="nav-bar">
    <div class="logo1">
        <img class="logo" src="${pageContext.request.contextPath}/resource/images/system/logo4.png" width="120px" height="44px">
    </div>
    
    <div class="middle-nav">
        <%-- Show different links based on role --%>
        <c:choose>
            <%-- Admin links --%>
            <c:when test="${sessionScope.user.role == 'admin'}">
                <a href="/ArtisanRoots7/dashboard">DASHBOARD</a>
                 <a href="/ArtisanRoots7/productmanage">PRODUCT MANAGEMENT</a>
                <a href="/ArtisanRoots7/portfolio">PORTFOLIO</a>
                <a href="/ArtisanRoots7/userManagement">USER MANAGEMENT</a>
            </c:when>
            
            <%-- Customer links (default) --%>
            <c:otherwise>
                <a href="/ArtisanRoots7/home">HOME</a>
                <a href="/ArtisanRoots7/contactus">CONTACT US</a>
                <a href="/ArtisanRoots7/aboutus">ABOUT US</a>
                <a href="/ArtisanRoots7/portfolio">PORTFOLIO</a>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="right-nav">
        <%-- Search bar (shown to all) --%>
                <c:if test="${sessionScope.user.role != 'admin'}">
     
           
<%--            <div class="search-container">
                <img class="search-icon" src="${pageContext.request.contextPath}/resource/images/system/search.png" alt="Search">
                <input type="text" class="search-input" placeholder="Search">
            </div> --%>
        
        <%-- Shopping features (only for customers) --%>
            <div class="wishlist">
                <img src="${pageContext.request.contextPath}/resource/images/system/wishlist.png" width="28px" height="26px">
            </div>
            <div class="cart">
                <img src="${pageContext.request.contextPath}/resource/images/system/cart1.png" width="25px" height="25px">
            </div>
        </c:if>
     

        
        <%-- Simple logout button --%>
            <form action="${pageContext.request.contextPath}/logout" method="get">
                <button type="submit" class="logout-btn">Logout</button>
            </form>
             <%-- In your header.jsp --%>
<%
    String profileImage = (String) session.getAttribute("profileImage");
    String path = request.getContextPath() + profileImage;
                   
%>
<%-- <img src="/ArtisanRoots3/resource/images/system/<%= profileImage %>" 
     alt="Profile" style="width: 40px; height: 40px; border-radius: 50%;"> --%>
     <img src="/ArtisanRoots3/resource/images/system/profile4.png" style="width: 40px; height: 40px; border-radius: 50%";>
    </div>
</div>
</body>
</html>