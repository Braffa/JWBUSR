package com.braffa.sellem.form;

import com.braffa.sellem.model.xml.webserviceobjects.product.Product;
import com.braffa.sellem.model.xml.webserviceobjects.product.UserToCatalogs;


public class ProductUserDetails {
	private static final long serialVersionUID = 1L;
	
	private Product product;
	
	private UserToCatalogs UserToCatalogs;
	
	private String currentPage;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public UserToCatalogs getUserToCatalogs() {
		return UserToCatalogs;
	}

	public void setUserToCatalogs(UserToCatalogs userToCatalogs) {
		UserToCatalogs = userToCatalogs;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

	
}
