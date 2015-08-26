package com.braffa.sellem.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.braffa.sellem.form.CatalogForm;
import com.braffa.sellem.form.ProductForm;
import com.braffa.sellem.form.RegisteredUserToProduct;
import com.braffa.sellem.form.WhoHasThisForm;
import com.braffa.sellem.model.xml.webserviceobjects.authentication.Login;
import com.braffa.sellem.model.xml.webserviceobjects.authentication.RegisteredUser;
import com.braffa.sellem.model.xml.webserviceobjects.authentication.RegisteredUsers;
import com.braffa.sellem.model.xml.webserviceobjects.product.Catalog;
import com.braffa.sellem.model.xml.webserviceobjects.product.Product;
import com.braffa.sellem.model.xml.webserviceobjects.product.ProductToUser;
import com.braffa.sellem.model.xml.webserviceobjects.product.ProductToUsers;
import com.braffa.sellem.model.xml.webserviceobjects.product.UserToCatalog;
import com.braffa.sellem.model.xml.webserviceobjects.product.UserToCatalogs;

import com.braffa.productlookup.amazon.ProductLookUp;

import com.braffa.sellem.xml.parser.ConvertObjectToXML;
import com.braffa.sellem.xml.parser.ConvertXMLToObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

@Controller
@SessionAttributes("loggedin")
public class ProductController {

	private static final Logger logger = Logger
			.getLogger(ProductController.class);

	// XML node keys
	public static final String KEY_AUTHOR = "author";
	public static final String KEY_MANUFACTURER = "manufacturer";
	public static final String KEY_PRODUCT_GROUP = "productgroup";
	public static final String KEY_PRODUCT_ID = "productid";
	public static final String KEY_PRODUCT_INDEX = "productIndex";
	public static final String KEY_PRODUCT_ID_TYPE = "productidtype";
	public static final String KEY_SOURCE = "source";
	public static final String KEY_SOURCE_ID = "sourceid";
	public static final String KEY_SOURCE_TITLE = "title";
	public static final String KEY_IMAGE_URL = "imageURL";

	public static final String KEY_IMAGE_BITMAP = "imagebitmap";

	public static final String KEY_URL = "URL";

	public static final String KEY_IMAGE_HEIGHT = "Height";
	public static final String KEY_IMAGE_WIDTH = "Width";

	@RequestMapping(value = "/addExistingProduct", method = RequestMethod.GET)
	public ModelAndView addExistingProduct(@RequestParam String productid,
			Map<String, Object> model) {
		if (logger.isDebugEnabled()) {
			logger.debug(productid);
		}
		Login login = (Login) model.get("loggedin");
		if (login == null || login.getUserId() == null) {
			return new ModelAndView("redirect:homepage.html");
		}
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());

		String xml = service.path("rest").path("catalog").path(productid)
				.path("productFullId").accept(MediaType.APPLICATION_XML)
				.get(String.class);

		ConvertXMLToObject convertXMLToObject = new ConvertXMLToObject(xml);
		Catalog catalog = convertXMLToObject.catalogXMLFileToObjects();

		Product product = catalog.getProducts().getlOfProducts().get(0);
		product.setUserId(login.getUserId());
		product.setAction("addExistingProduct");
		ClientResponse response = service.path("rest").path("catalog")
				.path(productid).accept(MediaType.APPLICATION_XML)
				.put(ClientResponse.class, product);

		if (logger.isDebugEnabled()) {
			logger.debug(response.getStatus());
		}
		return new ModelAndView("redirect:myCatalog.html");
	}
	
	@RequestMapping(value = "/saveNewProduct", method = RequestMethod.GET)
	public ModelAndView saveNewProduct(@RequestParam String productid,
			Map<String, Object> model) {
		if (logger.isDebugEnabled()) {
			logger.debug("saveNewProduct " + productid);
		}
		Login login = (Login) model.get("loggedin");
		if (login == null || login.getUserId() == null) {
			return new ModelAndView("redirect:homepage.html");
		}
		
		String barcodeFormat = "EAN";
		Document document = ProductLookUp.getProductsWithImage(
				productid, barcodeFormat, null, null,
				"All", "ThumbnailImage");
		List<Product> lOfProducts = new ArrayList<Product>();
		NodeList productList = document.getElementsByTagName("product");
		for (int i = 0; i < productList.getLength(); i++) {
			Element element = (Element) productList.item(i);
			Product product = new Product();
			product.setAuthor(getValue(element, KEY_AUTHOR));
			product.setImageLargeURL(getValue(element, KEY_URL));
			product.setImageURL(getValue(element, KEY_URL));
			product.setProductgroup(getValue(element, KEY_PRODUCT_GROUP));
			product.setManufacturer(KEY_MANUFACTURER);
			product.setProductid(getValue(element, KEY_PRODUCT_ID));
			product.setProductidtype(getValue(element, KEY_PRODUCT_ID_TYPE));
			product.setSource(getValue(element, KEY_SOURCE));
			product.setSourceid(getValue(element, KEY_SOURCE_ID));
			product.setTitle(getValue(element, KEY_SOURCE_TITLE));
			product.setUserId(login.getUserId());
			lOfProducts.add(product);
		}
		
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());

		//product.setAction(newProductForm.getAction());

		ClientResponse response = service.path("rest").path("catalog")
				.path(productid).accept(MediaType.APPLICATION_XML)
				.put(ClientResponse.class, lOfProducts.get(0));

		if (logger.isDebugEnabled()) {
			logger.debug(response.getStatus());
		}
		
		return new ModelAndView("redirect:myCatalog.html");
	}

	@RequestMapping("/addNewProduct")
	public Object addNewProduct(Map<String, Object> model) {
		if (logger.isDebugEnabled()) {
			logger.debug("addNewProduct");
		}
		Login login = (Login) model.get("loggedin");
		///if (login == null || login.getUserId() == null) {
		//	return new ModelAndView("redirect:homepage.html");
		//}
		ProductForm productForm = new ProductForm();
		productForm = setUpDummyProductform();
		productForm.setAction("new");
		productForm.setCurrentPage("newProduct");
		model.put("productForm", productForm);
		return "product";
	}

	@RequestMapping(value = "/deleteProduct", method = RequestMethod.GET)
	public ModelAndView deleteProduct(@Valid ProductForm newProductForm,
			@RequestParam String productid, @RequestParam String productIndex,
			BindingResult result, Map<String, Object> model) {
		if (logger.isDebugEnabled()) {
			logger.debug(productid);
		}
		Login login = (Login) model.get("loggedin");
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());
		Product product = new Product();
		product.setProductid(productid);
		product.setProductIndex(productIndex);
		product.setUserId(login.getUserId());
		product.setAction("delete");
		ClientResponse response = service.path("rest").path("catalog")
				.path(productid).accept(MediaType.APPLICATION_XML)
				.put(ClientResponse.class, product);

		if (logger.isDebugEnabled()) {
			logger.debug(response.getStatus());
		}
		return new ModelAndView("redirect:myCatalog.html");
	}

	@RequestMapping(value = "/getProductUserDetails", method = RequestMethod.GET)
	public Object getProductUserDetails(Map<String, Object> model,
			@RequestParam String productid) {
		if (logger.isDebugEnabled()) {
			logger.debug("productid " + productid);
		}
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());

		String xml = service.path("rest").path("producttousers")
				.path(productid).accept(MediaType.APPLICATION_XML)
				.get(String.class);

		ConvertXMLToObject convertXMLToObject = new ConvertXMLToObject(xml);
		ProductToUsers productToUsers = convertXMLToObject
				.productToUsersXMLFileToObjects();
		List<ProductToUser> lOfProductToUser = productToUsers
				.getlOfProductToUser();
		WhoHasThisForm whoHasThisForm = new WhoHasThisForm();
		for (ProductToUser productToUser : lOfProductToUser) {
			Product product = productToUser.getProduct();
			whoHasThisForm.setProduct(product);
			UserToCatalogs userToCatalogs = productToUser.getUserToCatalogs();
			RegisteredUsers registeredUsers = productToUser
					.getRegisteredUsers();
			List<RegisteredUserToProduct> lOfRegisteredUserToProduct = setUpLOfRegisteredUserToProduct(userToCatalogs, registeredUsers);
			whoHasThisForm.setlOfRegisteredUserToProduct(lOfRegisteredUserToProduct);
		}

		whoHasThisForm.setShowLinks(false);
		whoHasThisForm.setHeader("Who Has This");
		whoHasThisForm.setCurrentPage("whoHasThis");
		model.put("whoHasThisForm", whoHasThisForm);
		return "whohasthis";
	}

	private List<RegisteredUserToProduct> setUpLOfRegisteredUserToProduct(
			UserToCatalogs userToCatalogs, RegisteredUsers registeredUsers) {
		Map<String, RegisteredUserToProduct> mOfRegisteredUserToProduct = new HashMap<String, RegisteredUserToProduct>();
		List<UserToCatalog> lOfUserToCatalog = userToCatalogs
				.getlOfUserToCatalog();
		for (UserToCatalog userToCatalog : lOfUserToCatalog) {
			if (mOfRegisteredUserToProduct.containsKey(userToCatalog
					.getUserId())) {

			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("userToCatalog.getUserId() " + userToCatalog.getUserId());
				}
				RegisteredUserToProduct registeredUserToProduct = new RegisteredUserToProduct();
				registeredUserToProduct.setUserId(userToCatalog.getUserId());
				registeredUserToProduct.setCrDate(userToCatalog.getCrDate());
				registeredUserToProduct.setProductId(userToCatalog
						.getProductId());
				registeredUserToProduct.setProductIndex(userToCatalog
						.getProductIndex());
				mOfRegisteredUserToProduct.put(userToCatalog.getUserId(),
						registeredUserToProduct);
			}
		}
		List<RegisteredUser> lOfRegisteredUser = registeredUsers
				.getlOfRegisteredUser();
		for (RegisteredUser registeredUser : lOfRegisteredUser) {
			if (logger.isDebugEnabled()) {
				logger.debug("registeredUser.getUserId() " + registeredUser.getLogin().getUserId());
				logger.debug("registeredUser.getEmail() " + registeredUser.getEmail());
			}
			
			if (mOfRegisteredUserToProduct.containsKey(registeredUser
					.getLogin().getUserId())) {
				RegisteredUserToProduct registeredUserToProduct = mOfRegisteredUserToProduct
						.get(registeredUser.getLogin().getUserId());
				registeredUserToProduct.setEmail(registeredUser.getEmail());
				mOfRegisteredUserToProduct.put(
						registeredUserToProduct.getUserId(),
						registeredUserToProduct);
			} else {

			}
		}
		List<RegisteredUserToProduct> lOfRegisteredUserToProduct = new ArrayList<RegisteredUserToProduct>();
		for (RegisteredUserToProduct registeredUserToProduct : mOfRegisteredUserToProduct
				.values()) {
			lOfRegisteredUserToProduct.add(registeredUserToProduct);
		}
		return lOfRegisteredUserToProduct;
	}

	@RequestMapping(value = "/saveProduct", method = RequestMethod.POST)
	public Object saveProduct(@Valid ProductForm newProductForm,
			BindingResult result, Map<String, Object> model) {
		if (logger.isDebugEnabled()) {
			logger.debug(newProductForm.getProductid());
		}
		if (newProductForm.getAction().equals("ISBN")) {
			return productLookUp(newProductForm, model);
		}
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());

		Login login = (Login) model.get("loggedin");
		Product product = new Product(newProductForm.getAuthor(),
				newProductForm.getImageURL(),
				newProductForm.getImageLargeURL(),
				newProductForm.getManufacturer(),
				newProductForm.getProductIndex(),
				newProductForm.getProductgroup(),
				newProductForm.getProductid(),
				newProductForm.getProductidtype(), newProductForm.getSource(),
				newProductForm.getSourceid(), newProductForm.getTitle(),
				login.getUserId());
		product.setAction(newProductForm.getAction());

		ClientResponse response = service.path("rest").path("catalog")
				.path(product.getProductid()).accept(MediaType.APPLICATION_XML)
				.put(ClientResponse.class, product);

		if (logger.isDebugEnabled()) {
			logger.debug(response.getStatus());
		}
		return new ModelAndView("redirect:myCatalog.html");
	}

	@RequestMapping(value = "/updateProduct", method = RequestMethod.GET)
	public String updateProduct(@Valid ProductForm newProductForm,
			@RequestParam String productid, BindingResult result,
			Map<String, Object> model) {
		if (logger.isDebugEnabled()) {
			logger.debug(productid);
		}
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());

		String xml = service.path("rest").path("catalog").path(productid)
				.path("productFullId").accept(MediaType.APPLICATION_XML)
				.get(String.class);

		ConvertXMLToObject convertXMLToObject = new ConvertXMLToObject(xml);
		Catalog catalog = convertXMLToObject.catalogXMLFileToObjects();

		List<Product> lOfProducts = catalog.getProducts().getlOfProducts();
		Product product = lOfProducts.get(0);
		ProductForm productForm = new ProductForm();
		productForm.setAuthor(product.getAuthor());
		productForm.setImageLargeURL(product.getImageLargeURL());
		productForm.setImageURL(product.getImageURL());
		productForm.setManufacturer(product.getManufacturer());
		productForm.setProductgroup(product.getProductgroup());
		productForm.setProductid(product.getProductid());
		productForm.setProductidtype(product.getProductidtype());
		productForm.setProductIndex(product.getProductIndex());
		productForm.setSource(product.getSource());
		productForm.setSourceid(product.getSourceid());
		productForm.setTitle(product.getTitle());
		productForm.setAction("update");
		productForm.setCurrentPage("newProduct");
		model.put("productForm", productForm);
		return "product";
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri(
				"http://localhost:8080/sellemws").build();
	}

	private final String getElementValue(Node elem) {
		Node child;
		if (elem != null) {
			if (elem.hasChildNodes()) {
				for (child = elem.getFirstChild(); child != null; child = child
						.getNextSibling()) {
					if (child.getNodeType() == Node.TEXT_NODE) {
						return child.getNodeValue();
					}
				}
			}
		}
		return "";
	}

	private String getValue(Element item, String key) {
		NodeList n = item.getElementsByTagName(key);
		String value = this.getElementValue(n.item(0));
		return value;
	}

	private String productLookUp(ProductForm newProductForm,
			Map<String, Object> model) {
		if (logger.isDebugEnabled()) {
			logger.debug("newProductFromISBN");
		}
		try {
			String barcodeFormat = "EAN";
			Document document = ProductLookUp.getProductsWithImage(
					newProductForm.getProductid(), barcodeFormat, null, null,
					"All", "ThumbnailImage");
			List<Product> lOfProducts = new ArrayList<Product>();
			NodeList productList = document.getElementsByTagName("product");
			for (int i = 0; i < productList.getLength(); i++) {
				Element element = (Element) productList.item(i);
				Product product = new Product();
				product.setAuthor(getValue(element, KEY_AUTHOR));
				product.setImageLargeURL(getValue(element, KEY_URL));
				product.setImageURL(getValue(element, KEY_URL));
				product.setProductgroup(getValue(element, KEY_PRODUCT_GROUP));
				product.setManufacturer(KEY_MANUFACTURER);
				product.setProductid(getValue(element, KEY_PRODUCT_ID));
				product.setProductidtype(getValue(element, KEY_PRODUCT_ID_TYPE));
				product.setSource(getValue(element, KEY_SOURCE));
				product.setSourceid(getValue(element, KEY_SOURCE_ID));
				product.setTitle(getValue(element, KEY_SOURCE_TITLE));
				lOfProducts.add(product);
			}
			CatalogForm catalogForm = new CatalogForm();
			catalogForm.setmOfProducts(lOfProducts);
			catalogForm.setShowLinks(false);
			catalogForm.setHeader("Search Results");
			catalogForm.setOrigin("productLookUp");
			catalogForm.setCurrentPage("myCatalogue");
			model.put("catalogForm", catalogForm);
			return "catalog";
		} catch (Exception e) {
			ProductForm productForm = new ProductForm();
			productForm = setUpDummyProductform();
			productForm.setAction("new");
			productForm.setCurrentPage("newProduct");
			productForm.setErrorMessage("Cannot connect to internet");
			model.put("productForm", productForm);
			return "product";
		}
	}

	private ProductForm setUpDummyProductform() {
		ProductForm productForm = new ProductForm();
		productForm.setAuthor("Dave Brayfield");
		productForm
				.setImageLargeURL("http://ecx.images-amazon.com/images/I/41hdqEVaWML._SL75_.jpg");
		productForm.setImageURL("http:\\www.bbc.com");
		productForm.setManufacturer("Braffa Ltd");
		productForm.setProductgroup("Book");
		productForm.setProductid("123456789");
		productForm.setProductidtype("ION");
		productForm.setProductIndex("0");
		productForm.setSource("Braff");
		productForm.setSourceid("123456");
		productForm.setTitle("I want the best of both worlds");
		productForm.setCurrentPage("newProduct");
		return productForm;
	}
}
