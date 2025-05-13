package com.ArtisanRoots7.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import com.ArtisanRoots7.model.ProductModel;
import com.ArtisanRoots7.service.ProductManagementService;
import com.ArtisanRoots7.service.SearchService;
import com.ArtisanRoots7.util.ValidationUtil;

@WebServlet("/productmanage")
public class ProductManagementController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
      
            ProductManagementService productService = new ProductManagementService();
            List<ProductModel> productsToDisplay = null;
            String searchTerm = request.getParameter("searchItem");
            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                searchTerm = searchTerm.trim();
                try {
                    productsToDisplay = SearchService.displayProductByName(searchTerm);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    productsToDisplay = productService.display();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            
            }
            
           
            // Set attributes
            request.setAttribute("products", productsToDisplay);
            request.setAttribute("searchTerm", searchTerm); // Preserve search term
            
            request.getRequestDispatcher("WEB-INF/pages/product-management.jsp").forward(request, response);
            
        
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        

        ProductManagementService productService = new ProductManagementService();
        
        
            switch (action.toLowerCase()) {
                case "delete":
				try {
					handleDelete(request, response, productService);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                    break;
                case "update":
				try {
					handleUpdate(request, response, productService);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                    break;
                case "add":
				try {
					handleAdd(request, response, productService);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
                case "clear":
				try {
					handleClear(request, response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                break;   
            }
                
        
    }
    private Boolean validateProductCode(HttpServletRequest request) {
    	boolean hasErrors=false;
    	 String productCode = request.getParameter("productCode");
    	 request.setAttribute("productCode", productCode);
    	 if (ValidationUtil.isNull(productCode)) {
             request.setAttribute("productCodeError", "Product code cannot be empty");
             hasErrors = true;
         } else if (!ValidationUtil.isNumeric(productCode)) {
             request.setAttribute("productCodeError", "Product code must be numeric");
             hasErrors = true;
         } else if (ValidationUtil.isNegative(productCode)) {
             request.setAttribute("productCodeError", "Product code cannot be negative");
             hasErrors = true;
         }
    	 return hasErrors;
    }
    public Boolean validateDuplicateCode(HttpServletRequest request) throws Exception {
    	boolean hasErrors=false;
    	String productCode = request.getParameter("productCode");
   	 request.setAttribute("productCode", productCode);
    	int count=ProductManagementService.getProductId(productCode);
    	if (count>0) {
    		request.setAttribute("productCodeError", "Product code already exists");
    		hasErrors = true;
    	}
    	
    	return hasErrors;
    }
    public Boolean validateExistenceOfId(HttpServletRequest request) throws Exception {
    	boolean hasErrors=false;
    	String productCode = request.getParameter("productCode");
    	 request.setAttribute("productCode", productCode);
    	int count=ProductManagementService.getProductId(productCode);
    	if (count!=1) {
    		request.setAttribute("productCodeError", "Product code doesnt exist exists");
    		hasErrors = true;
    	}
    	
    	return hasErrors;
    }
    private Boolean validateFields(HttpServletRequest request) {
    	boolean hasErrors=false;
       
        String productName = request.getParameter("productName");
        String productPrice = request.getParameter("price");
        String stock = request.getParameter("stock");
        String createdDate = request.getParameter("createDate");
        String categoryName = request.getParameter("category");
        String productStatus = request.getParameter("productStatus");
        String productTotalSales = request.getParameter("totalSales");
        request.setAttribute("productName", productName);
        request.setAttribute("price", productPrice);
        request.setAttribute("stock", stock);
        request.setAttribute("createDate", createdDate);
        request.setAttribute("category", categoryName);
        request.setAttribute("productStatus", productStatus);
        request.setAttribute("totalSales", productTotalSales);

        
        // Check for null or empty fields
        

        if (ValidationUtil.isNull(productName)) {
            request.setAttribute("productNameError", "Product name cannot be empty");
            hasErrors = true;
        } else if (!ValidationUtil.isAlphabetic(productName)) {
            request.setAttribute("productNameError", "Product name must contain only letters and spaces");
            hasErrors = true;
        }

        if (ValidationUtil.isNull(productPrice)) {
            request.setAttribute("priceError", "Product price cannot be empty");
            hasErrors = true;
        } else if (!ValidationUtil.isNumeric(productPrice)) {
            request.setAttribute("priceError", "Product price must be a valid number");
            hasErrors = true;
        } else if (ValidationUtil.isNegative(productPrice)) {
            request.setAttribute("priceError", "Product price must be a positive number");
            hasErrors = true;
        }

        if (ValidationUtil.isNull(stock)) {
            request.setAttribute("stockError", "Product stock cannot be empty");
            hasErrors = true;
        } else if (!ValidationUtil.isNumeric(stock)) {
            request.setAttribute("stockError", "Stock must be a valid number");
            hasErrors = true;
        } else if (ValidationUtil.isNegative(stock)) {
            request.setAttribute("stockError", "Stock must be a positive number");
            hasErrors = true;
        }

        if (ValidationUtil.isNull(createdDate)) {
            request.setAttribute("createDateError", "Creation date cannot be empty");
            hasErrors = true;
        }
        if (!ValidationUtil.isValidDate(LocalDate.parse(createdDate))) {
            request.setAttribute("createDateError", "Invalid date entry.");
            hasErrors = true;
        }
        System.out.println("Category Name: " + categoryName);
        if (ValidationUtil.isNull(categoryName)) {
            request.setAttribute("productCategoryError", "Category cannot be empty");
            hasErrors = true;
        }

        if (ValidationUtil.isNull(productStatus)) {
            request.setAttribute("productStatusError", "Status cannot be empty");
            hasErrors = true;
        }

        if (ValidationUtil.isNull(productTotalSales)) {
            request.setAttribute("totalSalesError", "Total sales cannot be empty");
            hasErrors = true;
        } else if (!ValidationUtil.isNumeric(productTotalSales)) {
            request.setAttribute("totalSalesError", "Total sales must be a valid number");
            hasErrors = true;
        } else if (ValidationUtil.isNegative(productTotalSales)) {
            request.setAttribute("totalSalesError", "Total sales must be a positive number");
            hasErrors = true;
        }

        return hasErrors;
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response, 
            ProductManagementService productService) throws Exception {
    	Boolean validCode=   	validateProductCode(request);
Boolean checkExistence=validateExistenceOfId(request);

    	
    	if (checkExistence || validCode) {
            // Re-show the form with errors
            doGet(request, response);
            return;
        }
        String productCode = request.getParameter("productCode");
        
        productService.delete(productCode);
        response.sendRedirect(request.getContextPath() + "/productmanage");
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response, 
            ProductManagementService productService) throws Exception {
    	Boolean validCode=   	validateProductCode(request);
    	Boolean validateField = validateFields(request);
    	Boolean checkExistence=validateExistenceOfId(request);
    	
    	if (validCode || validateField || checkExistence) {
            // Re-show the form with errors
            doGet(request, response);
            return;
        }
        ProductModel product = parseProductFromRequest(request, productService);
        productService.update(product);
        response.sendRedirect(request.getContextPath() + "/productmanage");
    }

    private void handleAdd(HttpServletRequest request, HttpServletResponse response, 
            ProductManagementService productService) throws Exception {
    	
    	Boolean validCode=   	validateProductCode(request);
    	Boolean validateField = validateFields(request);
    	Boolean checkDuplicate=validateDuplicateCode(request);
    	
    	if (validCode || validateField || checkDuplicate) {
            // Load all required data before forwarding
            loadDataForDisplay(request);
            request.getRequestDispatcher("WEB-INF/pages/product-management.jsp").forward(request, response);
            return;
        }
               

        ProductModel product = parseProductFromRequest(request, productService);
        productService.add(product);
        response.sendRedirect(request.getContextPath() + "/productmanage");
        
    }
    
    private void handleClear(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("productCode", "");
        response.sendRedirect(request.getContextPath() + "/productmanage");
    }
    private void loadDataForDisplay(HttpServletRequest request) throws Exception {
        ProductManagementService productService = new ProductManagementService();
        List<ProductModel> productsToDisplay = productService.display();
        request.setAttribute("products", productsToDisplay);
       
    }
    private ProductModel parseProductFromRequest(HttpServletRequest request, 
            ProductManagementService productService) throws Exception {
        String productCode = request.getParameter("productCode");
        String productName = request.getParameter("productName");
        float productPrice = Float.parseFloat(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        LocalDate createdDate = LocalDate.parse(request.getParameter("createDate"));
        String categoryName = request.getParameter("category");
        String productStatus=request.getParameter("productStatus");
        int productTotalSales=Integer.parseInt(request.getParameter("totalSales"));
        		
        int categoryId = productService.getCategoryId(categoryName);
        
        return new ProductModel(productCode, productName, productPrice, stock, createdDate, categoryId, productTotalSales, productStatus);
    }

    
}