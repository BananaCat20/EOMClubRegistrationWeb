package cs.project.eom.ClubRegistrationWeb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class AppController implements ErrorController {
	private ClubRegistrationDto clubRegistrationDto;
	private ArrayList<ClubRegistrationDto> currentRegList = new ArrayList<ClubRegistrationDto>();
	private ArrayList<ClubRegistrationDto> allRegList = new ArrayList<ClubRegistrationDto>();
	private String loginName;
	private String loginEmail;
	private String loginImageLink;

	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ClubRegistrationRepository clubRegistrationRepo;

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
    public String applicantView(Model model, OAuth2AuthenticationToken authentication) {

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

    	// Find all the registrations with current user
    	currentRegList = clubRegistrationRepo.findByUserEmailIs(newUser.getEmail());
    	model.addAttribute("currentRegList", currentRegList);
	    return "applicant_view";
    }
    @GetMapping("/loginAdmin")
    public String getAdminLoginPage() {
        return "loginAdmin";
    }
    @RequestMapping("/admin_view")
    public String adminView(Model model) {
    	
        // Find all the registrations with current user
    	allRegList = (ArrayList<ClubRegistrationDto>) clubRegistrationRepo.findAll();
    	model.addAttribute("allRegList", allRegList);

        return "admin_view";
    }

    @RequestMapping("/register_form")
    public String registerForm(Model model, OAuth2AuthenticationToken authentication) {
    	
    	// Retrieve authentication user information
    	getAuthenticationInfo(authentication);

    	clubRegistrationDto = new ClubRegistrationDto();
    	clubRegistrationDto.setUserName(getLoginName());
    	clubRegistrationDto.setUserEmail(getLoginEmail());
	    model.addAttribute("registrationDto", clubRegistrationDto);
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

    @PostMapping("/action_register_new_form")
    public String actionRegisterNewForm(@ModelAttribute ClubRegistrationDto registerDto, Model model) {
      
      registerDto.setUserName(getLoginName());
      registerDto.setUserEmail(getLoginEmail());
      registerDto.setClubRegisterStatus(ClubRegistrationDto.ClubRegisterStatus.SUBMITTED);
      ClubRegistrationDto registerResult = clubRegistrationRepo.save(registerDto);
      model.addAttribute("registerResultDto", registerResult);
      boolean update = false;
      model.addAttribute("update", update);
      return "applicant_register_result";
    }

    @GetMapping("/applicant_register_update/{id}")
    public String updateRegisterForm(@PathVariable("id") Long id, Model model) {

    	ClubRegistrationDto updateRegisterForm = clubRegistrationRepo.findById(id)
    			.orElseThrow(() -> new IllegalArgumentException("Invalid registration Id:" + id));

    	model.addAttribute("updateRegisterForm", updateRegisterForm);
        return "applicant_register_update";
    }

    @PostMapping("update_register_form/{id}")
    public String actionUpdateRegisterForm(@PathVariable("id") Long id, ClubRegistrationDto updateRegisterForm,
    		BindingResult result, Model model) {
        
    	if (result.hasErrors()) {
        	updateRegisterForm.setId(id);
            return "applicant_register_update";
        }
    	ClubRegistrationDto newRegisterForm = clubRegistrationRepo.findById(id)
    			.orElseThrow(() -> new IllegalArgumentException("Invalid registration Id:" + id));
    	
    	// Copy user's new answers from updateRegisterForm to newRegisterForm.
    	newRegisterForm.setClubNameOption(updateRegisterForm.getClubNameOption());
    	newRegisterForm.setClubExecutiveTeamMembersEmails(updateRegisterForm.getClubExecutiveTeamMembersEmails());
    	newRegisterForm.setClubExecutiveTeamMembersNames(updateRegisterForm.getClubExecutiveTeamMembersNames());
    	newRegisterForm.setClubLocation(updateRegisterForm.getClubLocation());
    	newRegisterForm.setClubMeetingDescription(updateRegisterForm.getClubMeetingDescription());
    	newRegisterForm.setClubMeetInterval(updateRegisterForm.getClubMeetInterval());
    	newRegisterForm.setClubPresidentsEmails(updateRegisterForm.getClubPresidentsEmails());
    	newRegisterForm.setClubPresidentsInstagram(updateRegisterForm.getClubPresidentsInstagram());
    	newRegisterForm.setClubPresidentsNames(updateRegisterForm.getClubPresidentsNames());
    	newRegisterForm.setClubPurpose(updateRegisterForm.getClubPurpose());
    	newRegisterForm.setClubSocialMediaInfo(updateRegisterForm.getClubSocialMediaInfo());
    	newRegisterForm.setGoogleClassroomCode(updateRegisterForm.getGoogleClassroomCode());
    	newRegisterForm.setNote(updateRegisterForm.getNote());
    	newRegisterForm.setOtherClubName(updateRegisterForm.getOtherClubName());
    	newRegisterForm.setSupervisorEmail(updateRegisterForm.getSupervisorEmail());
    	newRegisterForm.setSupervisorName(updateRegisterForm.getSupervisorName());
    	newRegisterForm.setWhoCanJoin(updateRegisterForm.getWhoCanJoin());
    	ClubRegistrationDto registerResult = clubRegistrationRepo.save(newRegisterForm);
    	model.addAttribute("registerResultDto", registerResult);
    	boolean update = true;
    	model.addAttribute("update", update);

        return "applicant_register_result";
    }

    @PostMapping("delete_register_form/{id}")
    public String actionDeleteRegisterForm(@PathVariable("id") Long id, Model model) {
    	ClubRegistrationDto newRegisterForm = clubRegistrationRepo.findById(id)
    			.orElseThrow(() -> new IllegalArgumentException("Invalid registration Id:" + id));
    	
    	if (newRegisterForm != null) {
    		clubRegistrationRepo.deleteById(id);
    	}
    	
    	return "redirect:/applicant_view";
    }

    @GetMapping("/403")
    public String getAccessDeniedPage() {
        return "403";
    }
    
	public ClubRegistrationDto getRegisterForm() {
		return clubRegistrationDto;
	}

	public void setRegisterForm(ClubRegistrationDto clubRegistrationDto) {
		this.clubRegistrationDto = clubRegistrationDto;
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
