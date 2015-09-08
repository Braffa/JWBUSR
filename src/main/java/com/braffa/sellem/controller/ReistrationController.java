package com.braffa.sellem.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.braffa.sellem.form.RegisterForm;
import com.braffa.sellem.form.RegistrationForm;
import com.braffa.sellem.model.xml.authentication.XmlLogin;
import com.braffa.sellem.model.xml.authentication.XmlLoginMsg;
import com.braffa.sellem.model.xml.authentication.XmlRegisteredUser;
import com.braffa.sellem.model.xml.authentication.XmlRegisteredUserMsg;
import com.braffa.sellem.model.xml.webserviceobjects.authentication.Login;
import com.braffa.sellem.model.xml.webserviceobjects.authentication.Register;
import com.braffa.sellem.model.xml.webserviceobjects.authentication.RegisteredUser;
import com.braffa.sellem.validaters.EmailValidator;

import com.braffa.sellem.xml.parser.ConvertXMLToObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

@Controller
@SessionAttributes("loggedin")
public class ReistrationController {

	private static final Logger logger = Logger
			.getLogger(ReistrationController.class);

	@RequestMapping("/registerStart")
	public String registerStart(Map<String, RegisterForm> model) {
		if (logger.isDebugEnabled()) {
			logger.debug("register");
		}
		RegisterForm registerForm = new RegisterForm();
		registerForm.setCurrentPage("register");
		model.put("registerForm", registerForm);
		return "register";
	}

	@RequestMapping(value = "/attemptoregister", method = RequestMethod.POST)
	public String registered(@Valid RegisterForm registeredDetails,
			BindingResult result, Map<String, RegisterForm> model, Model myModel) {
		if (logger.isDebugEnabled()) {
			logger.debug("");
		}
		if (result.hasErrors()) {
			return "register";
		}
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());

		String xmlStr = service.path("rest").path("login").path("find")
				.path(registeredDetails.getUserId()).accept(MediaType.TEXT_XML)
				.get(String.class);

		ConvertXMLToObject convertXMLToObject = new ConvertXMLToObject(xmlStr);
		XmlLoginMsg loginMsg = convertXMLToObject.xmlloginMsgToObjects();
		if (loginMsg.getSuccess().equals("true")) {
			XmlLogin login = loginMsg.getLOfLogins().get(0);
			// does user id already exist
			if (login != null
					&& login.getUserId().equalsIgnoreCase(
							registeredDetails.getUserId())) {
				result.addError(new FieldError("registeredDetails", "userId",
						"Invalid userName" + " ( "
								+ registeredDetails.getUserId()
								+ " already exists" + " )"));
				return "register";
			}
		}
		// is email valid
		if (!validateEmailAddress(registeredDetails.getEmail())) {
			result.addError(new FieldError("registeredDetails", "email",
					"Invalid email" + " ( " + registeredDetails.getEmail()
							+ " please correct it" + " )"));
			return "register";
		}

		XmlLogin xmllogin = new XmlLogin("-99",
				registeredDetails.getPassword(), registeredDetails.getUserId());

		XmlRegisteredUser xmlRegisteredUser = new XmlRegisteredUser();
		xmlRegisteredUser.setEmail(registeredDetails.getEmail());
		xmlRegisteredUser.setFirstname(registeredDetails.getFirstname());
		xmlRegisteredUser.setLastname(registeredDetails.getLastname());
		xmlRegisteredUser.setLogin(xmllogin);
		xmlRegisteredUser.setTelephone(registeredDetails.getTelephone());
		XmlRegisteredUserMsg XmlRegisteredUserMsg = new XmlRegisteredUserMsg(
				xmlRegisteredUser);

		service = client.resource(getBaseURI());
		WebResource createService = service.path("rest")
				.path("registeredusers").path("create");
		ClientResponse response = createService.accept(
				MediaType.APPLICATION_XML).post(ClientResponse.class,
				XmlRegisteredUserMsg);

		if (logger.isDebugEnabled()) {
			logger.debug(response.getStatus());
		}
		return "redirect:homepage.html";
	}

	@RequestMapping(value = "/showRegisteredUsers")
	public Object showRegisteredUsers(Map<String, Object> model, Model myModel) {
		if (logger.isDebugEnabled()) {
			logger.debug("showRegisteredUsers");
		}
		XmlLogin login = (XmlLogin) model.get("loggedin");
		if (login == null || login.getUserId() == null) {
			return new ModelAndView("redirect:homepage.html");
		}
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());

		String xml = service.path("rest").path("registeredusers")
				.path("findall").accept(MediaType.TEXT_XML).get(String.class);

		ConvertXMLToObject convertXMLToObject = new ConvertXMLToObject(xml);
		XmlRegisteredUserMsg registeredUserMsg = convertXMLToObject
				.xmlRegisteredUserMsgToObjects();
		List<XmlRegisteredUser> lOfRegisteredUser = registeredUserMsg
				.getLOfRegisteredUsers();
		List<RegisterForm> lOfRegisteredDetails = new ArrayList<RegisterForm>();
		for (XmlRegisteredUser registeredUser : lOfRegisteredUser) {
			RegisterForm registerForm = new RegisterForm(
					registeredUser.getEmail(), registeredUser.getFirstname(),
					registeredUser.getLastname(), registeredUser.getLogin()
							.getPassword(), registeredUser.getTelephone(),
					registeredUser.getLogin().getUserId());
			lOfRegisteredDetails.add(registerForm);
		}

		RegistrationForm registrationForm = new RegistrationForm();
		registrationForm.setRegisteredDetails(lOfRegisteredDetails);
		registrationForm.setCurrentPage("registeredUsers");
		model.put("registrationForm", registrationForm);
		return "registeredUsers";
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/sellemws").build();
	}

	private boolean validateEmailAddress(String email) {
		EmailValidator emailValidator = new EmailValidator();
		boolean valid = emailValidator.validate(email);
		return valid;
	}
}
