    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Handcrafted Wonders - Admin Dashboard</title>
      <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/header.css" />
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
    
        input {
            width: 600px;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
        }
        select {
            width: 622px;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;}
    
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
        .metric-card {
    background: white;
    border-radius: 12px;
    padding: 20px;
    box-shadow: 0 4px 12px rgba(0,0,0,0.08);
    display: flex;
    align-items: center;
    gap: 15px;
}

.metric-icon {
    font-size: 28px;
    padding: 15px;
    background: #f5f5f5;
    border-radius: 50%;
}

.metric-content {
    flex: 1;
}

.metric-title {
    color: #666;
    font-size: 14px;
    font-weight: 500;
    margin-bottom: 5px;
}

.metric-value {
    font-size: 24px;
    font-weight: 700;
    color: #333;
    margin-bottom: 5px;
}

.metric-subtext {
    font-size: 12px;
    color: #888;
}

.trend {
    font-weight: 600;
}

.trend.up {
    color: #4CAF50;
}

.trend.down {
    color: #F44336;
}

.health-meter {
    height: 6px;
    background: #f0f0f0;
    border-radius: 3px;
    margin: 8px 0;
}

.health-bar {
    height: 100%;
    border-radius: 3px;
}

.health-bar.good {
    background: #4CAF50;
}

.health-bar.warning {
    background: #FFC107;
}

.health-bar.critical {
    background: #F44336;
}
td, th{
text-align: center;
}
/* Add to your <style> section */
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
.table-header{
display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
    flex-wrap: wrap; /* Allows items to wrap on small screens */
    gap: 15px;
}
.error{
	font-size:10px;
	 min-height: 16px;
	  display: block;
	  color: red;
}
    </style>
    
</head>
<body>
   
        <jsp:include page="header.jsp"/>

    
    <div class="main-content">
        <h1 class="page-title">Product Management</h1>
       
       <!--  <div class="stats-container">
            <div class="stat-card">
                <h3>Best Seller</h3>
                <div class="product-name">Bowl</div>
                <div class="product-stats">
          3,434 sold 	&#183; 13,736 revenue
      </div>
            </div>
            <div class="stat-card">
                <h3>Total Stock</h3>
                <div class="product-name">Bowl</div>
                <div class="product-stats">
          12 low 	&#183; 3 out
      </div>
            </div>
            <div class="stat-card">
                <h3>Items Sold</h3><div class="product-name">1200</div>
                <div class="product-stats">
         most sold category
      </div>
            </div> -->
       <!--  </div> -->
        
        <div class="product-table-container">
        <div class="table-header">
        
        <div class="product-info-table">
        <h2>Product Inventory</h2>
        </div>
        <div class="search-container">
        <form action="${pageContext.request.contextPath}/productmanage" method="get">
           <div class="search-container1">
                <img class="search-icon1" src="${pageContext.request.contextPath}/resource/images/system/search.png" alt="Search">
                <input type="text" class="search-input1" placeholder="Search" name="searchItem" value="${param.searchItem}">
            </div>
        </form>
    </div>
    </div>
            <table>
                <thead>
                    <tr>
                        <th >Product Code</th>
                        <th >Product Name</th>
                        <th >Price (Rs.)</th>
                        <th >Stock</th>
                        <th >Category</th>
                        <th >Total Sales</th>
                        <th >Status</th>
                        <th >Date Created</th>
                    </tr>
                </thead>
                <tbody>
                   <c:forEach var="product" items="${products}">
                    <tr>
                    
                         <td >${product.productId}</td>
        <td >${product.productName}</td>
        <td >${product.price}</td>
        <td  >${product.quantity}</td>
        <td >${product.categoryId}</td>
        <td >${product.totalSales}</td>
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
        <td >${product.createdDate}</td>
                        
                    </tr>
                     </c:forEach>
                </tbody>
            </table>
        </div>
        
        <div class="product-form">
            <h2>Product Information</h2>
            <form action="${pageContext.request.contextPath}/productmanage" method="post">
            <div class="form-grid">
                <div class="form-group">
                    <label for="product-code">Product Code</label>
                    <input type="text" id="product-code" placeholder="Enter product code" name="productCode" value="${productCode}">
                      <span class="error">${productCodeError}</span>
                </div>
                <div class="form-group">
                    <label for="product-name">Product Name</label>
                    <input type="text" id="product-name" placeholder="Enter product name" name="productName" value="${productName}">
                    
          <span class="error">${productNameError}</span>
        
                </div>
                <div class="form-group">
                    <label for="price">Price (Rs.)</label>
                    <input type="number" id="price" placeholder="Enter price" name="price" value="${price}">
                     <span class="error">${priceError}</span>
                </div>
                <div class="form-group">
                    <label for="stock">Stock</label>
                    <input type="number" id="stock" placeholder="Enter stock quantity" name="stock" value="${stock}">
                     <span class="error">${stockError}</span>
                </div>
                <div class="form-group">
                     <label for="category">Category</label>
    <select id="category" name="category">
        <option value="">Select category</option>
        <option value="Pottery" ${category == 'Pottery' ? 'selected' : ''}>Pottery</option>
        <option value="Ceramic" ${category == 'Ceramic' ? 'selected' : ''}>Ceramic</option>
        <option value="Sculpture" ${category == 'Sculpture' ? 'selected' : ''}>Sculpture</option>
        <option value="Stone" ${category == 'Stone' ? 'selected' : ''}>Stone</option>
        <option value="Wood" ${category == 'Wood' ? 'selected' : ''}>Wood</option>
        <option value="Metal" ${category == 'Metal' ? 'selected' : ''}>Metal</option>
        <option value="Textile" ${category == 'Textile' ? 'selected' : ''}>Textile</option>
        <option value="Glass" ${category == 'Glass' ? 'selected' : ''}>Glass</option>
        <option value="Leather" ${category == 'Leather' ? 'selected' : ''}>Leather</option>
        <option value="Paper" ${category == 'Paper' ? 'selected' : ''}>Paper</option>
    </select>
    <c:if test="${not empty productCategoryError}">
        <span class="error">${productCategoryError}</span>
    </c:if>
                    
                </div>
                <div class="form-group">
                    <label for="sales">Total Sales</label>
                    <input type="number" id="sales" placeholder="Enter total Sales" name="totalSales" value="${totalSales}">
                     <span class="error">${totalSalesError}</span>
                </div>
                <div class="form-group">
                    <label for="date-created" >Date Created</label>
                    <input type="date" id="date-created" name="createDate" value="${createDate}">
                     <span class="error">${createDateError}</span>
                </div>
                <div class="form-group">
                    <label for="status">Status</label>
                    <select id="status" name="productStatus" value="${productStatus}">
                        <option value="">Select status</option>
                         <option value="Active" ${productStatus == 'Active' ? 'selected' : ''}>Active</option>
        <option value="Inactive" ${productStatus == 'Inactive' ? 'selected' : ''}>Inactive</option>
        <option value="Discontinued" ${productStatus == 'Discontinued' ? 'selected' : ''}>Discontinued</option>
                    </select>
                     <span class="error">${productStatusError}</span>
                </div>
            </div>
            <div class="form-actions">
                <button class="btn btn-primary" type="Submit" name="action" value="add" >Add Product</button>
                <button class="btn btn-secondary" type="Submit" name="action" value="update" >Update</button>
                <button class="btn btn-danger" type="Submit" name="action" value="delete" >Delete</button>
                <button class="btn btn-secondary" type="reset" name="action" value="clear" >Clear</button>
            </div>
            </form>
        </div>
    </div>

</body>
</html>