<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Handcrafted Wonders - Product Dashboard</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f9f5f0;
            color: #333;
        }
        
        .container {
        
            width: 1380px;
            margin: 0 auto;
            padding: 20px;
        }
        
        .dashboard-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
        }
        
        .dashboard-title {
            font-size: 28px;
            color: #3E2723;
            margin: 0;
        }
        
        .last-updated {
            color: #666;
            font-size: 14px;
        }
        
        .main-layout {
            display: grid;
            grid-template-columns: 1fr 1.5fr;
            gap: 30px;
        }
        
        .left-column, .right-column {
            display: flex;
            flex-direction: column;
            gap: 30px;
        }
        
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 25px;
        }
        
        .stat-card {
            background: white;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
            height: 110px;
        }
        
        .stat-card h3 {
            margin-top: 0;
            color: #8B5A2B;
            font-size: 16px;
        }
        
        .stat-card .value {
            font-size: 28px;
            font-weight: bold;
            margin: 10px 0 5px 0;
        }
        
        .activities-card, .top-products-card, .overview-card {
           background: white;
    border-radius: 10px;
    padding: 25px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    flex-grow: 1;
        }
        
        .activities-card h2, .top-products-card h2 {
            margin-top: 0;
            color: #3E2723;
            border-bottom: 1px solid #eee;
            padding-bottom: 15px;
            margin-bottom: 20px;
    font-size: 20px;
        }
        
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }
        
        th, td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #eee;
        }
        
        th {
            background-color: #F5F5DC;
            color: #3E2723;
    font-weight: 600;
    font-size: 15px;
        }
        
        tr:hover {
            background-color: #f9f9f9;
        }
        
        .dashboard-row {
            display: flex;
            justify-content: space-between;
            gap: 20px;
        }
        
        .overview-card {
  flex: 1;
  height:265px;
  background-color: white;
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0 2px 5px rgba(0,0,0,0.1);
}
         .metrics-card {
            background: white;
            border-radius: 10px;
            padding: 25px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
            height: 290px;
        }
        
        .metrics-card h2 {
            margin-top: 0;
            color: var(--dark);
            font-size: 20px;
            border-bottom: 1px solid #eee;
            padding-bottom: 10px;
            margin-bottom: 10px;
        }
        
        .metric-item {
            margin-bottom: 15px;
        }
        
        .metric-label {
            font-weight: 500;
            color: #8B5A2B;
            margin-bottom: 4px;
            font-size: 15px;
        }
        
        .metric-value {
            font-size: 14px;
        }
        .overview-card h2{
        font-size:20px;
        margin-top: 0;
        }
    </style>
</head>
<body>
<jsp:include page="header.jsp" />
    <div class="container">
        <div class="dashboard-header">
            <h1 class="dashboard-title">Product Dashboard</h1>
            <div class="last-updated">Last updated: ${updatedTime}</div>
        </div>
        
        <div class="main-layout">
            <div class="left-column">
                <div class="stats-grid">
                    <div class="stat-card">
                        <h3>💰 Current Revenue</h3>
                        <div class="value">${totalRevenue}</div>
                    </div>
                    <div class="stat-card">
                        <h3>💰 Current Sales</h3>
                        <div class="value">${totalSales}</div>
                    </div>
                    <div class="stat-card">
                        <h3>Active Products</h3>
                        <div class="value">${totalActiveSales}</div>
                    </div>
                    <div class="stat-card">
                        <h3>Low Stock Items</h3>
                        <div class="value">${totalLowStock}</div>
                    </div>
                </div>
                
                <div class="activities-card">
                    <h2>Recent Activities</h2>
                    <c:forEach var="activity" items="${productActivity}">
                        <div class="activity-item">
                            <div class="activity-type">${activity}</div>
                        </div>
                    </c:forEach>
                </div>
            </div>
            
            <div class="right-column">
                <div class="dashboard-row">
                    <div class="overview-card">
                        <h2>Metrics Overview</h2>
                        
                        <div class="metric-item">
                            <div class="metric-label">Most Sold Category</div>
                            <div class="metric-value">${mostSoldCategory}</div>
                        </div>
                        <div class="metric-item">
                            <div class="metric-label">Least Sold Category</div>
                            <div class="metric-value">${leastSoldCategory}</div>
                        </div>
                        <div class="metric-item">
                            <div class="metric-label">Most Trending</div>
                            <div class="metric-value">${mostTrending}</div>
                        </div>
                        <div class="metric-item">
                            <div class="metric-label">Least Sold</div>
                            <div class="metric-value">${leastSold}</div>
                        </div>
                    </div>
                    
                    <div class="overview-card">
                        <h2>User Overview</h2>
                        <div class="metric-item">
                            <div class="metric-label">Number of Users</div>
                            <div class="metric-value">${totalUsers}</div>
                        </div>
                        <div class="metric-item">
                            <div class="metric-label">New Users This Month</div>
                            <div class="metric-value">${newUsers}</div>
                        </div>
                        <div class="metric-item">
                            <div class="metric-label">Female Users</div>
                            <div class="metric-value">${femaleUsers}</div>
                        </div>
                        <div class="metric-item">
                            <div class="metric-label">Male Users</div>
                            <div class="metric-value">${maleUsers}</div>
                        </div>
                    </div>
                </div>
                
                <div class="top-products-card">
                    <h2>Top Performing Products</h2>
                    <table>
                        <thead>
                            <tr>
                                <th>Product ID</th>
                                <th>Name</th>
                                <th>Price</th>
                                <th>Sales</th>
                                <th>Revenue</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="product" items="${topProduct}">
                                <tr>
                                    <td>${product.productId}</td>
                                    <td>${product.productName}</td>
                                    <td>${product.price}</td>
                                    <td>${product.totalSales}</td>
                                    <td>${product.totalSales * product.price}</td>
                                    <c:choose>
                                        <c:when test="${product.productStatus eq 'Active'}">
                                            <td style="color: green">${product.productStatus}</td>
                                        </c:when>
                                        <c:when test="${product.productStatus eq 'Discontinued'}">
                                            <td style="color: red">${product.productStatus}</td>
                                        </c:when>
                                        <c:otherwise>
                                            <td style="color: rgb(247, 165, 50)">${product.productStatus}</td>
                                        </c:otherwise>
                                    </c:choose>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</body>
</html>