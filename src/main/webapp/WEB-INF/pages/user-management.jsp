<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Handcrafted Wonders - User Management</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/userManagement.css" />
    <style>
  
    </style>
</head>
<body>
    <jsp:include page="header.jsp"/>

    <div class="main-content">
        <h1 class="page-title">User Management</h1>
        
    
        
        <div class="user-table-container">
                <div class="table-header">
         <div class="user-info-table">
        <h2>User Information</h2>
        </div>
         <div class="search-wrapper">
        <form action="${pageContext.request.contextPath}/userManagement" method="get">
            <div class="search-container">
             <span class="search-error">${searchUserError}</span>
                <div class="search-container1">
                    <img class="search-icon1" src="${pageContext.request.contextPath}/resource/images/system/search.png" alt="Search">
                    <input type="text" class="search-input1" placeholder="Search" name="searchUser" value="${param.searchItem}">
                </div>
            </div>
           
        </form>
    </div>
    </div>
            <table>
                <thead>
                    <tr>
                        <th>User ID</th>
                        <th>Name</th>
                        <th>Username</th>
                        <th>dob</th> 
                        <th>Email</th>
                        <th>Phone Number</th>
                        <th>Join Date</th>
                        <th>Gender</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${users}">
                        <tr>
                            <td>${user.userId}</td>
                            <td>${user.firstName} ${user.lastName}</td>
                            <td>${user.username}</td>
                            <td>${user.dob}</td>
                            <td>${user.email}</td>
                            <td>${user.phone}</td>
                            <td>${user.joinedDate}</td>
                            <td>${user.gender}</td>
                            <c:choose>
              <c:when test="${user.status eq 'Active'}">
                  <td style="color: green">${user.status }</td>
              </c:when>
               <c:when test="${user.status  eq 'Discontinued'}">
                  <td style="color: red">${user.status }</td>
              </c:when>
              <c:otherwise>
                  <td style="color: rgb(247, 165, 50)">${user.status }</td>
              </c:otherwise>
          </c:choose>
                            </tr>
                            
                            
                    </c:forEach>
                </tbody>
            </table>
        </div>
        
       
    </div>

    
</body>
</html>