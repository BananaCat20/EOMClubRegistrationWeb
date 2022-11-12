package cs.project.eom.ClubRegistrationWeb;

import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class AppController implements ErrorController {
	private RegisterForm registerForm;
	private String loginName;
	private String loginEmail;
	private String loginImageLink;

	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;
    @Autowired
    private UserRepository userRepo;

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
    public String applicantGetLoginInfo(Model model, OAuth2AuthenticationToken authentication) {

    	// Retrieve authentication user information
    	getAuthenticationInfo(authentication);

    	// Set attributes for thymeleaf template
    	model.addAttribute("loginName", getLoginName());
	    model.addAttribute("loginEmail", getLoginEmail());
	    model.addAttribute("loginImageLink", getLoginImageLink());

    	// Register user in database
    	UserDto newUser = new UserDto();
    	newUser.setEmail(getLoginEmail());
    	newUser.setUserName(getLoginName());
    	
    	// Find all the users in the database
    	List<UserDto> userList = userRepo.findAll();
    	boolean found = false;
    	
    	// Find the newUser in the current user list
    	for (UserDto user : userList) {
    		if (user.getEmail().equals(newUser.getEmail())) {
    			found = true;
    		}
    	}
    	
    	// If we cannot find the newUser, create this newUser in database 
    	if (!found) {
        	userRepo.save(newUser);
    	}

	    return "applicant_view";
    }
    @GetMapping("/loginAdmin")
    public String getAdminLoginPage() {
        return "loginAdmin";
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

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
        
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error-404";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error-500";
            }
        }
        return "error";
    }

    @GetMapping("/403")
    public String getAccessDeniedPage() {
        return "403";
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
