package com.ArtisanRoots7.model;

import java.time.LocalDate;

/**
 * Model class representing a product with its details.
 */
public class ProductModel {
	String productId;
	String productName;
	float price;
	int quantity;
	LocalDate createdDate;
	int categoryId;
	int totalSales;
	String productStatus;
	String imagePath;

    /**
     * Constructs a new ProductModel with all required attributes.
     * 
     * @param productId      unique identifier for the product
     * @param productName    name of the product
     * @param price          price of the product
     * @param quantity       available quantity in stock
     * @param createdDate    date the product was created
     * @param categoryId     ID of the category this product belongs to
     * @param totalSales     total sales count for this product
     * @param productStatus  current status of the product
     */
//	public ProductModel(String productId, String productName, float price, int quantity, LocalDate createdDate,
//            int categoryId, int totalSales, String productStatus) {
//this.productId = productId;
//this.productName = productName;
//this.price = price;
//this.quantity = quantity;
//this.createdDate = createdDate;
//this.categoryId = categoryId;
//this.totalSales = totalSales;
//this.productStatus = productStatus;
//}

    public ProductModel(String productId, String productName, float price, int quantity, LocalDate createdDate,
            int categoryId, int totalSales, String productStatus, String imagePath) {
        super();
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.createdDate = createdDate;
        this.totalSales = totalSales;
        this.productStatus = productStatus;
        this.categoryId = categoryId;
        this.imagePath=imagePath;
    }

    public ProductModel() {
		// TODO Auto-generated constructor stub
	}

	/**
     * Returns the product ID.
     * 
     * @return the productId
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Returns the product name.
     * 
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Returns the product price.
     * 
     * @return the price
     */
    public float getPrice() {
        return price;
    }

    /**
     * Returns the available quantity of the product.
     * 
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Returns the date the product was created.
     * 
     * @return the createdDate
     */
    public LocalDate getCreatedDate() {
        return createdDate;
    }

    /**
     * Returns the category ID of the product.
     * 
     * @return the categoryId
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * Sets the product ID.
     * 
     * @param productId the unique identifier to set
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * Sets the product name.
     * 
     * @param productName the name to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Sets the price of the product.
     * 
     * @param price the price to set
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * Sets the available quantity of the product.
     * 
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Sets the creation date of the product.
     * 
     * @param createdDate the creation date to set
     */
    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Sets the category ID for the product.
     * 
     * @param categoryId the category ID to set
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Returns the total sales count of the product.
     * 
     * @return the totalSales
     */
    public int getTotalSales() {
        return totalSales;
    }

    /**
     * Returns the current status of the product.
     * 
     * @return the productStatus
     */
    public String getProductStatus() {
        return productStatus;
    }

    /**
     * Sets the total sales count of the product.
     * 
     * @param totalSales the total sales count to set
     */
    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }

    /**
     * Sets the current status of the product.
     * 
     * @param productStatus the product status to set
     */
    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
