package com.ArtisanRoots7.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import com.ArtisanRoots7.model.ProductModel;
import com.ArtisanRoots7.service.ProductManagementService;
import com.ArtisanRoots7.service.SearchService;
import com.ArtisanRoots7.util.ImageUtil;
import com.ArtisanRoots7.util.ValidationUtil;

@WebServlet("/productmanage")
@MultipartConfig
public class ProductManagementController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Handles GET requests to display products or search results.
	 *
	 * @param request  the HttpServletRequest object
	 * @param response the HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ProductManagementService productService = new ProductManagementService();
		List<ProductModel> productsToDisplay = null;
		String searchTerm = request.getParameter("searchItem");

		if (searchTerm != null && !searchTerm.trim().isEmpty()) {
			// Search products by name
			searchTerm = searchTerm.trim();
			try {
				productsToDisplay = SearchService.displayProductByName(searchTerm);
				if (productsToDisplay == null || productsToDisplay.isEmpty()) {
					request.setAttribute("searchError", "No products found for: " + searchTerm);
				} else {
					request.setAttribute("products", productsToDisplay);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (searchTerm != null) {
			// Empty search input submitted
			request.setAttribute("searchError", "Please enter a search name");
			try {
				productsToDisplay = productService.display(); // Show all products
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("products", productsToDisplay);
		} else {
			// No search parameter, display all products
			try {
				productsToDisplay = productService.display();
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("products", productsToDisplay);
		}

		// Display success message if present in session
		HttpSession session = request.getSession();
		String successProduct = (String) session.getAttribute("successProduct");
		if (successProduct != null) {
			request.setAttribute("successProduct", successProduct);
			session.removeAttribute("successProduct");
		}

		// Retain search term in the view
		request.setAttribute("searchTerm", searchTerm);
		request.getRequestDispatcher("WEB-INF/pages/product-management.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests for add, update, delete, or clear actions.
	 *
	 * @param request  the HttpServletRequest object
	 * @param response the HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
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

	/**
	 * Validates the product code parameter.
	 *
	 * @param request the HttpServletRequest object
	 * @return true if validation errors are found, false otherwise
	 */
	private Boolean validateProductCode(HttpServletRequest request) {
		boolean hasErrors = false;
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

	/**
	 * Validates if the product code already exists for adding new products.
	 *
	 * @param request the HttpServletRequest object
	 * @return true if duplicate product code exists, false otherwise
	 * @throws Exception if service layer fails
	 */
	public Boolean validateDuplicateCode(HttpServletRequest request) throws Exception {
		boolean hasErrors = false;
		String productCode = request.getParameter("productCode");
		request.setAttribute("productCode", productCode);
		int count = ProductManagementService.getProductId(productCode);
		if (count > 0) {
			request.setAttribute("productCodeError", "Product code already exists");
			hasErrors = true;
		}

		return hasErrors;
	}

	/**
	 * Validates if the product code exists for update/delete actions.
	 *
	 * @param request the HttpServletRequest object
	 * @return true if product code does not exist, false otherwise
	 * @throws Exception if service layer fails
	 */
	public Boolean validateExistenceOfId(HttpServletRequest request) throws Exception {
		boolean hasErrors = false;
		String productCode = request.getParameter("productCode");
		request.setAttribute("productCode", productCode);
		int count = ProductManagementService.getProductId(productCode);
		if (count != 1) {
			request.setAttribute("productCodeError", "Product code doesnt exist exists");
			hasErrors = true;
		}

		return hasErrors;
	}

	/**
	 * Validates all product fields.
	 *
	 * @param request the HttpServletRequest object
	 * @return true if validation errors are found, false otherwise
	 */
	private Boolean validateFields(HttpServletRequest request) {
		boolean hasErrors = false;

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

	/**
	 * Processes product deletion.
	 *
	 * @param request        the HttpServletRequest object
	 * @param response       the HttpServletResponse object
	 * @param productService the ProductManagementService instance
	 * @throws Exception if deletion fails
	 */
	private void handleDelete(HttpServletRequest request, HttpServletResponse response,
			ProductManagementService productService) throws Exception {
		Boolean validCode = validateProductCode(request);
		Boolean checkExistence = validateExistenceOfId(request);

		if (checkExistence || validCode) {
			// Re-show the form with errors
			doGet(request, response);
			return;
		}
		HttpSession session = request.getSession();
		session.setAttribute("successProduct", "Information deleted successfully!!");
		String productCode = request.getParameter("productCode");

		productService.delete(productCode);
		response.sendRedirect(request.getContextPath() + "/productmanage");
	}

	/**
	 * Processes product update.
	 *
	 * @param request        the HttpServletRequest object
	 * @param response       the HttpServletResponse object
	 * @param productService the ProductManagementService instance
	 * @throws Exception if update fails
	 */
	private void handleUpdate(HttpServletRequest request, HttpServletResponse response,
			ProductManagementService productService) throws Exception {
		Boolean validCode = validateProductCode(request);
		Boolean validateField = validateFields(request);
		Boolean checkDuplicate = validateDuplicateCode(request);
		String productCode = request.getParameter("productCode");
		String productName = request.getParameter("productName");
		float productPrice = Float.parseFloat(request.getParameter("price"));
		int stock = Integer.parseInt(request.getParameter("stock"));
		LocalDate createdDate = LocalDate.parse(request.getParameter("createDate"));
		String categoryName = request.getParameter("category");
		String productStatus = request.getParameter("productStatus");
		int productTotalSales = Integer.parseInt(request.getParameter("totalSales"));

		int categoryId = productService.getCategoryId(categoryName);
		Part file = request.getPart("image");

		// Handle image upload
		String imageFilename = handleImageUpload(request, file);
		ProductModel product=new ProductModel(productCode, productName, productPrice, stock, createdDate, categoryId,
				productTotalSales, productStatus, imageFilename);
		if (validCode || validateField || checkDuplicate) {
			// Load all required data before forwarding
			loadDataForDisplay(request);
			request.getRequestDispatcher("WEB-INF/pages/product-management.jsp").forward(request, response);
			return;
		}
		HttpSession session = request.getSession();
		session.setAttribute("successProduct", "Information updated successfully!!");
		productService.update(product);
		response.sendRedirect(request.getContextPath() + "/productmanage");
	}

	/**
	 * Processes product addition.
	 *
	 * @param request        the HttpServletRequest object
	 * @param response       the HttpServletResponse object
	 * @param productService the ProductManagementService instance
	 * @throws Exception if addition fails
	 */
	private void handleAdd(HttpServletRequest request, HttpServletResponse response,
			ProductManagementService productService) throws Exception {

		Boolean validCode = validateProductCode(request);
		Boolean validateField = validateFields(request);
		Boolean checkDuplicate = validateDuplicateCode(request);
		String productCode = request.getParameter("productCode");
		String productName = request.getParameter("productName");
		float productPrice = Float.parseFloat(request.getParameter("price"));
		int stock = Integer.parseInt(request.getParameter("stock"));
		LocalDate createdDate = LocalDate.parse(request.getParameter("createDate"));
		String categoryName = request.getParameter("category");
		String productStatus = request.getParameter("productStatus");
		int productTotalSales = Integer.parseInt(request.getParameter("totalSales"));

		int categoryId = productService.getCategoryId(categoryName);
		Part file = request.getPart("image");

		// Handle image upload
		String imageFilename = handleImageUpload(request, file);
		ProductModel product=new ProductModel(productCode, productName, productPrice, stock, createdDate, categoryId,
				productTotalSales, productStatus, imageFilename);
		if (validCode || validateField || checkDuplicate) {
			// Load all required data before forwarding
			loadDataForDisplay(request);
			request.getRequestDispatcher("WEB-INF/pages/product-management.jsp").forward(request, response);
			return;
		}

		HttpSession session = request.getSession();
		session.setAttribute("successProduct", "Information added successfully!!");
		productService.add(product);
		response.sendRedirect(request.getContextPath() + "/productmanage");

	}

	/**
	 * Clears all input fields and resets the form.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 */
	private void handleClear(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("productCode", "");
		response.sendRedirect(request.getContextPath() + "/productmanage");
	}

	/**
	 * Load product for display.
	 *
	 * @param request HttpServletRequest object
	 */
	private void loadDataForDisplay(HttpServletRequest request) throws Exception {
		ProductManagementService productService = new ProductManagementService();
		List<ProductModel> productsToDisplay = productService.display();
		request.setAttribute("products", productsToDisplay);

	}

	/**
	 * Parse product from request
	 *
	 * @param request        HttpServletRequest object
	 * @param productService ProductManagementService object
	 */
//	private ProductModel parseProductFromRequest(HttpServletRequest request, ProductManagementService productService)
//			throws Exception {
//		String productCode = request.getParameter("productCode");
//		String productName = request.getParameter("productName");
//		float productPrice = Float.parseFloat(request.getParameter("price"));
//		int stock = Integer.parseInt(request.getParameter("stock"));
//		LocalDate createdDate = LocalDate.parse(request.getParameter("createDate"));
//		String categoryName = request.getParameter("category");
//		String productStatus = request.getParameter("productStatus");
//		int productTotalSales = Integer.parseInt(request.getParameter("totalSales"));
//
//		int categoryId = productService.getCategoryId(categoryName);
//
//		return new ProductModel(productCode, productName, productPrice, stock, createdDate, categoryId,
//				productTotalSales, productStatus);
//	}

	/**
	 * Validates if the uploaded file is an acceptable image.
	 * 
	 * @param part    uploaded file
	 * @param request HTTP request
	 * @return true if valid, false otherwise
	 */
	public static boolean isValidImage(Part part, HttpServletRequest request) {
		String contentType = part.getContentType();
		if (!contentType.startsWith("image/")) {
			request.setAttribute("imageError", "Only image files are allowed");
			return false;
		}
		if (part.getSize() > 10_000_000) {
			request.setAttribute("imageError", "Image too large (max 10MB)");
			return false;
		}
		return true;
	}

	/**
	 * Handles image upload process.
	 * 
	 * @param request HTTP request
	 * @param file    uploaded image file
	 * @return the name of the uploaded image file
	 * @throws IOException      if an I/O error occurs
	 * @throws ServletException if the image is invalid or upload fails
	 */
	private String handleImageUpload(HttpServletRequest request, Part file) throws IOException, ServletException {

		String imageFilename = null;

		if (file != null && file.getSize() > 0) {
			if (!isValidImage(file, request)) {
				throw new ServletException("Invalid image file");
			}

			imageFilename = ImageUtil.getImageNameFromPart(file);
			boolean uploadSuccess = ImageUtil.uploadImage(file, getServletContext());

			if (!uploadSuccess) {
				throw new ServletException("Failed to upload image");
			}
		}
		return imageFilename;
	}

}