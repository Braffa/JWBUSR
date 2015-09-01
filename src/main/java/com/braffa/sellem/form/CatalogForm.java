package com.braffa.sellem.form;

import java.util.List;

import com.braffa.sellem.model.xml.product.XmlProduct;
import com.braffa.sellem.model.xml.webserviceobjects.product.Product;


public class CatalogForm {
	
	private List<XmlProduct> lOfProducts;
	
	private boolean showLinks; 
	
	private String header;
	
	private String origin;
	
	private String currentPage;

	public List<XmlProduct> getlOfProducts() {
		return lOfProducts;
	}

	public void setmOfProducts(List<XmlProduct> lOfProducts) {
		this.lOfProducts = lOfProducts;
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
	
	

}
