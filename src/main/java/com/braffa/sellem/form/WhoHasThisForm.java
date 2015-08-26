package com.braffa.sellem.form;

import java.util.List;

import com.braffa.sellem.model.xml.webserviceobjects.product.Product;

public class WhoHasThisForm {

	private Product product;

	private List<RegisteredUserToProduct> lOfRegisteredUserToProduct;

	private boolean showLinks;

	private String header;

	private String origin;

	private String currentPage;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public boolean isShowLinks() {
		return showLinks;
	}

	public void setShowLinks(boolean showLinks) {
		this.showLinks = showLinks;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public List<RegisteredUserToProduct> getlOfRegisteredUserToProduct() {
		return lOfRegisteredUserToProduct;
	}

	public void setlOfRegisteredUserToProduct(
			List<RegisteredUserToProduct> lOfRegisteredUserToProduct) {
		this.lOfRegisteredUserToProduct = lOfRegisteredUserToProduct;
	}


}
