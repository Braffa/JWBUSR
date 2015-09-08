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

import com.braffa.productlookup.amazon.ProductLookUp;
import com.braffa.sellem.form.CatalogForm;
import com.braffa.sellem.form.ProductForm;
import com.braffa.sellem.form.RegisteredUserToProduct;
import com.braffa.sellem.form.WhoHasThisForm;
import com.braffa.sellem.model.xml.authentication.XmlLogin;
import com.braffa.sellem.model.xml.authentication.XmlRegisteredUser;
import com.braffa.sellem.model.xml.authentication.XmlRegisteredUserMsg;
import com.braffa.sellem.model.xml.product.XmlProduct;
import com.braffa.sellem.model.xml.product.XmlProductMsg;
import com.braffa.sellem.model.xml.product.XmlUserToProduct;
import com.braffa.sellem.model.xml.product.XmlUserToProductMsg;
import com.braffa.sellem.model.xml.product.XmlUsersLinkedToProduct;
import com.braffa.sellem.model.xml.product.XmlUsersProductMsg;
import com.braffa.sellem.model.xml.webserviceobjects.authentication.Login;
import com.braffa.sellem.model.xml.webserviceobjects.authentication.RegisteredUser;
import com.braffa.sellem.model.xml.webserviceobjects.authentication.RegisteredUsers;
import com.braffa.sellem.model.xml.webserviceobjects.product.Catalog;
import com.braffa.sellem.model.xml.webserviceobjects.product.Product;
import com.braffa.sellem.model.xml.webserviceobjects.product.ProductToUser;
import com.braffa.sellem.model.xml.webserviceobjects.product.ProductToUsers;
import com.braffa.sellem.model.xml.webserviceobjects.product.UserToCatalog;
import com.braffa.sellem.model.xml.webserviceobjects.product.UserToCatalogs;
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

	private int addProduct(XmlProductMsg xmlProductMsg) {
		if (logger.isDebugEnabled()) {
			logger.debug("addProduct");
		}
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());
		WebResource createService = service.path("rest").path("product")
				.path("create");
		ClientResponse response = createService.accept(
				MediaType.APPLICATION_XML).post(ClientResponse.class,
				xmlProductMsg);
		return response.getStatus();
	}

	private int processUserToProduct(String productid, String userId,
			String action) {
		if (logger.isDebugEnabled()) {
			logger.debug("processUserToProduct " + productid + " " + userId
					+ " " + action);
		}
		XmlUserToProduct userToProduct = new XmlUserToProduct();
		userToProduct.setUserId(userId);
		userToProduct.setProductId(productid);
		userToProduct.setProductIndex(0);
		XmlUserToProductMsg userToProductMsg = new XmlUserToProductMsg(
				userToProduct);
		ClientConfig userToProductconfig = new DefaultClientConfig();
		Client userToProductClient = Client.create(userToProductconfig);
		WebResource userToProductService = userToProductClient
				.resource(getBaseURI());
		WebResource usertoproductCreateService = userToProductService
				.path("rest").path("usertoproduct").path(action);
		ClientResponse usertoproductresponse = usertoproductCreateService
				.accept(MediaType.APPLICATION_XML).post(ClientResponse.class,
						userToProductMsg);
		if (logger.isDebugEnabled()) {
			logger.debug(usertoproductresponse.getStatus());
		}
		return usertoproductresponse.getStatus();
	}

	private int deleteToProduct(String productid, String userId) {
		if (logger.isDebugEnabled()) {
			logger.debug("processUserToProduct " + productid + " " + userId);
		}
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource webResource = client.resource(getBaseURI());
		ClientResponse response = webResource.path("rest")
				.path("usertoproduct").path("delete").path(userId)
				.path(productid).path("0").delete(ClientResponse.class);
		if (logger.isDebugEnabled()) {
			logger.debug(response.getStatus());
		}
		return response.getStatus();
	}

	@RequestMapping(value = "/lookUpProduct", method = RequestMethod.POST)
	public Object lookUpProduct(@Valid ProductForm newProductForm,
			BindingResult result, Map<String, Object> model) {
		if (logger.isDebugEnabled()) {
			logger.debug("saveProduct " + newProductForm.getProductid());
		}
		try {
			String barcodeFormat = "EAN";
			Document document = ProductLookUp.getProductsWithImage(
					newProductForm.getProductid(), barcodeFormat, null, null,
					"All", "ThumbnailImage");
			List<XmlProduct> lOfProducts = new ArrayList<XmlProduct>();
			NodeList productList = document.getElementsByTagName("product");
			for (int i = 0; i < productList.getLength(); i++) {
				Element element = (Element) productList.item(i);
				XmlProduct product = new XmlProduct();
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
			productForm.setProductidtype("EAN");
			productForm.setCurrentPage("newProduct");
			productForm.setAction("new");
			productForm.setErrorMessage("Cannot connect to internet");
			model.put("productForm", productForm);
			return "product";
		}
	}

	@RequestMapping(value = "/addExistingProduct", method = RequestMethod.GET)
	public ModelAndView addExistingProduct(@RequestParam String productid,
			Map<String, Object> model) {
		if (logger.isDebugEnabled()) {
			logger.debug("addExistingProduct " + productid);
		}
		XmlLogin login = (XmlLogin) model.get("loggedin");
		if (login == null || login.getUserId() == null) {
			return new ModelAndView("redirect:homepage.html");
		}
		processUserToProduct(productid, login.getUserId(), "create");
		return new ModelAndView("redirect:myCatalog.html");
	}

	@RequestMapping(value = "/deleteProduct", method = RequestMethod.GET)
	public ModelAndView deleteProduct(@Valid ProductForm newProductForm,
			@RequestParam String productid, @RequestParam String productIndex,
			BindingResult result, Map<String, Object> model) {
		if (logger.isDebugEnabled()) {
			logger.debug("deleteProduct" + productid + " " + productIndex);
		}
		XmlLogin login = (XmlLogin) model.get("loggedin");
		deleteToProduct(productid, login.getUserId());
		return new ModelAndView("redirect:myCatalog.html");
	}

	@RequestMapping(value = "/getProductUserDetails", method = RequestMethod.GET)
	public Object getProductUserDetails(Map<String, Object> model,
			@RequestParam String productid) {
		if (logger.isDebugEnabled()) {
			logger.debug("getProductUserDetails productid " + productid);
		}
		XmlLogin login = (XmlLogin) model.get("loggedin");
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());
		String xmlStr = service.path("rest").path("userproduct")
				.path("findusersbyproductid").path(productid)
				.accept(MediaType.TEXT_XML).get(String.class);
		ConvertXMLToObject convertXMLToObject = new ConvertXMLToObject(xmlStr);
		XmlUsersProductMsg xmlUsersProductMsg = convertXMLToObject
				.xmlUsersProductMsgToObjects();

		XmlProduct product = xmlUsersProductMsg.getProduct();

		WhoHasThisForm whoHasThisForm = new WhoHasThisForm();
		if (xmlUsersProductMsg.getSuccess().equals("true")) {
			List<XmlUsersLinkedToProduct> lOfXmlUsersLinkedToProduct = xmlUsersProductMsg
					.getlOfXmlUsersLinkedToProduct();
			if (lOfXmlUsersLinkedToProduct.size() > 0) {
				List<RegisteredUserToProduct> lOfRegisteredUserToProduct = new ArrayList<RegisteredUserToProduct>();
				for (XmlUsersLinkedToProduct usersLinkedToProduct : lOfXmlUsersLinkedToProduct) {
					RegisteredUserToProduct registeredUserToProduct = new RegisteredUserToProduct();
					registeredUserToProduct.setCrDate(usersLinkedToProduct
							.getAddedDate());
					registeredUserToProduct.setEmail(usersLinkedToProduct
							.getEmail());
					registeredUserToProduct.setUserId(usersLinkedToProduct
							.getUserId());
					registeredUserToProduct
							.setProductId(product.getProductid());
					registeredUserToProduct.setProductIndex("0");
					lOfRegisteredUserToProduct.add(registeredUserToProduct);
				}
				whoHasThisForm
						.setlOfRegisteredUserToProduct(lOfRegisteredUserToProduct);
			}
		}
		whoHasThisForm.setProduct(product);
		whoHasThisForm.setShowLinks(false);
		whoHasThisForm.setHeader("Who Has This");
		whoHasThisForm.setCurrentPage("whoHasThis");
		model.put("whoHasThisForm", whoHasThisForm);
		return "whohasthis";
	}

	@RequestMapping(value = "/saveNewProduct", method = RequestMethod.GET)
	public ModelAndView saveNewProduct(@RequestParam String productid,
			Map<String, Object> model) {
		if (logger.isDebugEnabled()) {
			logger.debug("saveNewProduct " + productid);
		}
		XmlLogin login = (XmlLogin) model.get("loggedin");
		if (login == null || login.getUserId() == null) {
			return new ModelAndView("redirect:homepage.html");
		}
		String barcodeFormat = "EAN";
		Document document = ProductLookUp.getProductsWithImage(productid,
				barcodeFormat, null, null, "All", "ThumbnailImage");
		List<XmlProduct> lOfProducts = new ArrayList<XmlProduct>();
		NodeList productList = document.getElementsByTagName("product");
		for (int i = 0; i < productList.getLength(); i++) {
			Element element = (Element) productList.item(i);
			XmlProduct product = new XmlProduct();
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
		XmlProductMsg xmlProductMsg = new XmlProductMsg(lOfProducts.get(0));
		int status = addProduct(xmlProductMsg);
		if (status == 200) {
			processUserToProduct(lOfProducts.get(0).getProductid(),
					login.getUserId(), "create");
		}
		return new ModelAndView("redirect:myCatalog.html");
	}

	@RequestMapping("/addNewProduct")
	public Object addNewProduct(Map<String, Object> model) {
		if (logger.isDebugEnabled()) {
			logger.debug("addNewProduct");
		}
		XmlLogin login = (XmlLogin) model.get("loggedin");
		if (login == null || login.getUserId() == null) {
		 return new ModelAndView("redirect:homepage.html");
		}
		ProductForm productForm = new ProductForm();
		productForm.setProductidtype("EAN");
		productForm.setCurrentPage("newProduct");
		productForm.setAction("new");
		productForm.setCurrentPage("newProduct");
		model.put("productForm", productForm);
		return "product";
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/sellemws").build();
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

}
