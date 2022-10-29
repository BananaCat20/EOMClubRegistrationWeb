package cs.project.eom.ClubRegistrationWeb;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class AppController {
	private RegisterForm registerForm;
	private String loginName;
	private String loginEmail;
	private String loginImageLink;

	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;

    @RequestMapping(value={"/", "/welcome"})
    public ModelAndView welcome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("welcome.html");
        return modelAndView;
    }

    private void getAuthenticationInfo(OAuth2AuthenticationToken authentication) {
    	// From https://www.baeldung.com/spring-security-5-oauth2-login
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
        								authentication.getAuthorizedClientRegistrationId(), authentication.getName());

        String userInfoEndpointUri = client.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();
		if (!userInfoEndpointUri.isEmpty()) {
		    RestTemplate restTemplate = new RestTemplate();
		    HttpHeaders headers = new HttpHeaders();
		    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken().getTokenValue());
		    HttpEntity<String> entity = new HttpEntity<String>("", headers);
		    ResponseEntity <Map>response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
		    Map userAttributes = response.getBody();
		    setLoginName(userAttributes.get("name").toString());
		    setLoginEmail(userAttributes.get("email").toString());
		    setLoginImageLink(userAttributes.get("picture").toString());
		}
    }
    @RequestMapping("/applicant_view")
    public String getLoginInfo(Model model, OAuth2AuthenticationToken authentication) {

    	// Retrieve authentication user information
    	getAuthenticationInfo(authentication);
	   
    	// Set attributes for thymeleaf template
    	model.addAttribute("loginName", getLoginName());
	    model.addAttribute("loginEmail", getLoginEmail());
	    model.addAttribute("loginImageLink", getLoginImageLink());
        return "applicant_view";
    }

    @RequestMapping("/admin_view")
    public ModelAndView adminView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin_view.html");
        return modelAndView;
    }

    @RequestMapping("/register_form")
    public String registerForm(Model model, OAuth2AuthenticationToken authentication) {
    	
    	// Retrieve authentication user information
    	getAuthenticationInfo(authentication);

    	registerForm = new RegisterForm();
    	registerForm.setLoginName(getLoginName());
    	registerForm.setLoginEmail(getLoginEmail());
	    model.addAttribute("registerForm", registerForm);
        return "register_form";
    }

	public RegisterForm getRegisterForm() {
		return registerForm;
	}

	public void setRegisterForm(RegisterForm registerForm) {
		this.registerForm = registerForm;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginEmail() {
		return loginEmail;
	}

	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail;
	}

	public String getLoginImageLink() {
		return loginImageLink;
	}

	public void setLoginImageLink(String loginImageLink) {
		this.loginImageLink = loginImageLink;
	}

}
