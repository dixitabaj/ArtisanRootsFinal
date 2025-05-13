<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Handcrafted Wonders - User Management</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css" />
    <style>
            body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f9f5f0;
            color: #333;
        }
    
        /* Navbar */
        .navbar {
            background-color: #3E2723;
            color: white;
            padding: 15px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            position: sticky;
            top: 0;
            z-index: 100;
        }
    
        .logo {
            font-size: 22px;
            font-weight: bold;
        }
    
        .nav-menu {
            display: flex;
            list-style: none;
            margin: 0;
            padding: 0;
        }
    
        .nav-menu li {
            margin-left: 20px;
        }
    
        .nav-menu a {
            color: white;
            text-decoration: none;
            padding: 8px 15px;
            border-radius: 5px;
            transition: all 0.3s;
            display: flex;
            align-items: center;
        }
    
        .nav-menu a:hover, .nav-menu a.active {
            background-color: #8B5A2B;
        }
    
        .nav-menu i {
            margin-right: 8px;
        }
    
        .user-info {
            display: flex;
            align-items: center;
        }
    
        .user-info img {
            width: 32px;
            height: 32px;
            border-radius: 50%;
            margin-right: 10px;
        }
    
        /* Main Content */
        .main-content {
            padding: 30px;
            max-width: 1400px;
            margin: 0 auto;
        }
    
        .page-title {
            font-size: 28px;
            color: #3E2723;
            margin: 0 0 30px 0;
        }
    
        /* Stats Cards */
        .stats-container {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 20px;
            margin-bottom: 30px;
        }
    
        .stat-card {
            background: white;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
        }
    
        .stat-card h3 {
            margin-top: 0;
            color: #8B5A2B;
            font-size: 16px;
        }
    
        .stat-card .value {
            font-size: 28px;
            font-weight: bold;
            margin: 10px 0;
        }
    
        .stat-card .trend {
            font-size: 14px;
            color: #666;
        }
    
        .trend.up {
            color: #4CAF50;
        }
    
        .trend.down {
            color: #F44336;
        }
    
        /* Product Table */
        .product-table-container {
            background: white;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
            overflow-x: auto;
            margin-bottom: 30px;
        }
    
        table {
            width: 100%;
            border-collapse: collapse;
        }
    
        th, td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #eee;
        }
    
        th {
            background-color: #F5F5DC;
            color: #3E2723;
            font-weight: 600;
        }
    
        tr:hover {
            background-color: #f9f9f9;
        }
    
        .status-active {
            color: #4CAF50;
            font-weight: 500;
        }
    
        .status-inactive {
            color: #FFC107;
            font-weight: 500;
        }
    
        .status-discontinued {
            color: #F44336;
            font-weight: 500;
        }
    
        /* Product Form */
        .product-form {
            background: white;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
        }
    
        .form-title {
            margin-top: 0;
            color: #3E2723;
            font-size: 22px;
            margin-bottom: 20px;
        }
    
        .form-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 20px;
        }
    
        .form-group {
            margin-bottom: 15px;
        }
    
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: 500;
        }
    
        input, select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
        }
    
        .form-actions {
            display: flex;
            gap: 10px;
            margin-top: 20px;
        }
    
        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-weight: 500;
            transition: all 0.3s;
        }
    
        .btn-primary {
            background-color: #8B5A2B;
            color: white;
        }
    
        .btn-secondary {
            background-color: #D2B48C;
            color: #3E2723;
        }
    
        .btn-danger {
            background-color: #F44336;
            color: white;
        }
    
        .btn:hover {
            opacity: 0.9;
            transform: translateY(-2px);
        }
    
        /* Responsive */
        @media (max-width: 1024px) {
            .stats-container {
                grid-template-columns: 1fr;
            }
    
            .form-grid {
                grid-template-columns: 1fr;
            }
    
            .navbar {
                flex-direction: column;
                padding: 15px;
            }
    
            .nav-menu {
                margin-top: 15px;
                width: 100%;
                justify-content: space-between;
            }
    
            .nav-menu li {
                margin-left: 0;
            }
        }
    
        @media (max-width: 768px) {
            .nav-menu {
                flex-direction: column;
                align-items: flex-start;
            }
    
            .nav-menu li {
                margin: 5px 0;
                width: 100%;
            }
    
            .nav-menu a {
                display: block;
                width: 100%;
            }
            
        }
        /* Reuse your existing styles from product management */
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f9f5f0;
            color: #333;
        }
        
        /* Navbar and main content styles same as before */
        
        /* Custom styles for user management */
        .status-active {
            color: #4CAF50;
            font-weight: 500;
        }
        
        .status-inactive {
            color: #F44336;
            font-weight: 500;
        }
        
        .role-admin {
            background-color: #3E2723;
            color: white;
            padding: 3px 8px;
            border-radius: 4px;
            font-size: 12px;
        }
        
        .role-customer {
            background-color: #8B5A2B;
            color: white;
            padding: 3px 8px;
            border-radius: 4px;
            font-size: 12px;
        }
        
        .role-artisan {
            background-color: #D2B48C;
            color: #3E2723;
            padding: 3px 8px;
            border-radius: 4px;
            font-size: 12px;
        }
        .user-table-container{
        background: white;
    border-radius: 10px;
    padding: 20px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    overflow-x: auto;
    margin-bottom: 30px;}
        .table-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
}


.search-container1 {
  position: relative;
}

.search-input1 {
  width: 200px;
  height: 15px;
  padding-left: 35px;
  border-radius: 20px;
  border: none;
  font-size: 13px;
  border: 1px solid #D2B48C;
  margin-bottom:20px;
}

.search-icon1 {
  position: absolute;
    left: 12px;
    top: 10px;
    width: 15px;
    height: 15px;
    opacity: 0.6;
}
.product-info-table h2 {
    position:absolute;
    top:180px;
    
}
h2{
font-weight:550;}
table-header{
display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
    flex-wrap: wrap; /* Allows items to wrap on small screens */
    gap: 15px;
    </style>
</head>
<body>
    <jsp:include page="header.jsp"/>

    <div class="main-content">
        <h1 class="page-title">User Management</h1>
        
       <!--  <div class="stats-container">
            <div class="stat-card">
                <h3>Total Users</h3>
                <div class="value">99</div>
            </div>
            <div class="stat-card">
                <h3>Active Users</h3>
                <div class="value">892</div>
            </div>
            <div class="stat-card">
                <h3>New Signups</h3>
                <div class="value">143</div>
            </div>
        </div> -->
        
        <div class="user-table-container">
                <div class="table-header">
         <div class="user-info-table">
        <h2>Product Inventory</h2>
        </div>
        <div class="search-container">
        <form action="${pageContext.request.contextPath}/userManagement" method="get">
           <div class="search-container1">
                <img class="search-icon1" src="${pageContext.request.contextPath}/resource/images/system/search.png" alt="Search">
                <input type="text" class="search-input1" placeholder="Search" name="searchUser" value="${param.searchItem}">
            </div>
        </form>
    </div>
    </div>
            <table>
                <thead>
                    <tr>
                        <th>User ID</th>
                        <th>First Name</th>
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