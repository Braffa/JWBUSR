package com.braffa.sellem.controller;

import java.net.URI;
import java.util.Map;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.braffa.sellem.form.LoginForm;
import com.braffa.sellem.model.xml.authentication.XmlLogin;
import com.braffa.sellem.model.xml.authentication.XmlLoginMsg;
import com.braffa.sellem.model.xml.webserviceobjects.authentication.Login;

import com.braffa.sellem.xml.parser.ConvertXMLToObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

@Controller
@SessionAttributes("loggedin")
public class HomePageController {

	private static final Logger logger = Logger
			.getLogger(HomePageController.class);

	@RequestMapping("/homepage.html")
	public String showHomePage(Map<String, LoginForm> model) {
		if (logger.isDebugEnabled()) {
			logger.debug("showHomePage");
		}
		LoginForm loginForm = new LoginForm();
		loginForm.setCurrentPage("home");
		model.put("loginForm", loginForm);
		return "home";
	}

	@RequestMapping("/gotoPage.html")
	public ModelAndView gotoPage(@RequestParam String gotoPage, Model myModel) {
		if (logger.isDebugEnabled()) {
			logger.debug(gotoPage);
		}
		if (gotoPage.equals("home")) {
			return new ModelAndView("redirect:homepage.html");
		}
		if (gotoPage.equals("catalogue")) {
			return new ModelAndView("redirect:getCatalog.html");
		}
		if (gotoPage.equals("searchCatalogue")) {
			return new ModelAndView("redirect:searchCatalogStart.html");
		}
		if (gotoPage.equals("addProduct")) {
			return new ModelAndView("redirect:addNewProduct.html");
		}
		if (gotoPage.equals("myCatalog")) {
			return new ModelAndView("redirect:myCatalog.html");
		}
		if (gotoPage.equals("login")) {
			return new ModelAndView("redirect:homepage.html");
		}
		if (gotoPage.equals("register")) {
			return new ModelAndView("redirect:registerStart.html");
		}
		if (gotoPage.equals("showRegisteredUsers")) {
			return new ModelAndView("redirect:showRegisteredUsers.html");
		}
		if (gotoPage.equals("signOut")) {
			Login login = new Login();
			myModel.addAttribute("loggedin", login);
			return new ModelAndView("redirect:homepage.html");
		}
		if (gotoPage.equals("productToUsers")) {
			return new ModelAndView("redirect:getProductUserDetails.html");
		}
		return null;
	}

	@RequestMapping("/attemptlogin.html")
	public Object processForm(@Valid LoginForm loginForm, BindingResult result,
			Map<String, Object> model, Model myModel) {
		if (logger.isDebugEnabled()) {
			logger.debug("processForm");
		}
		if (result.hasErrors()) {
			return "home";
		}
		loginForm = (LoginForm) model.get("loginForm");
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());

		String xml = service.path("rest").path("login").path("find")
				.path(loginForm.getUserName())
				.accept(MediaType.TEXT_XML).get(String.class);
		
		ConvertXMLToObject convertXMLToObject = new ConvertXMLToObject(xml);
		XmlLoginMsg loginMsg = convertXMLToObject.xmlloginMsgToObjects();
		XmlLogin login = loginMsg.getLOfLogins().get(0);

		ModelAndView mv = new ModelAndView("redirect:homepage.html");

		if (login.getUserId().equals("9999")) {
			result.addError(new FieldError("loginForm", "userName",
					"Invalid userName" + " ( " + loginForm.getUserName()
							+ " does not exist" + " )"));
			return "home";
		} else {
			if (login.getPassword().equals(loginForm.getPassword())) {
				myModel.addAttribute("loggedin", login);
				model.put("loggedin", login);
				mv = new ModelAndView("redirect:getCatalog.html");
			} else {
				result.addError(new FieldError("loginForm", "password",
						"Invalid " + "password" + " ( " + "Invalid password"
								+ " )"));
				return "home";
			}
		}
		myModel.addAttribute("userId", login.getUserId());
		return mv;
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri(
				"http://localhost:8080/sellemws").build();
	}
}
