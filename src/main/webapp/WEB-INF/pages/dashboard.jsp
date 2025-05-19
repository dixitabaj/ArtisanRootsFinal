<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/dashboard.css">
    <title>Handcrafted Wonders - Product Dashboard</title>
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
                        <h3>Current Revenue</h3>
                        <div class="value">${totalRevenue}</div>
                    </div>
                    <div class="stat-card">
                        <h3>Current Sales</h3>
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