package cs.project.eom.ClubRegistrationWeb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public static PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Configuration
    @Order(1)
    public static class AdminConfigurationAdapter {
        @Bean
        public UserDetailsService userDetailsServiceAdmin() {
            UserDetails user = User.withUsername("admin")
                .password(encoder().encode("adminPass"))
                .roles("ADMIN")
                .build();
            return new InMemoryUserDetailsManager(user);
        }

        @Bean
        public SecurityFilterChain filterChainAdmin(HttpSecurity http) throws Exception {
            http.antMatcher("/admin*")
	            .authorizeRequests()
	            .anyRequest()
	            .hasRole("ADMIN")
	            // log in
	            .and()
	            .formLogin()
	            .loginPage("/loginAdmin")
	            .loginProcessingUrl("/admin_login")
	            .failureUrl("/loginAdmin?error=loginError")
	            .defaultSuccessUrl("/admin_view")
	            // logout
	            .and()
	            .logout()
	            .logoutUrl("/admin_logout")
	            .logoutSuccessUrl("/welcome")
	            .deleteCookies("JSESSIONID")
	            .and()
	            .exceptionHandling()
	            .accessDeniedPage("/403")
	            .and()
	            .csrf()
	            .disable();

            return http.build();
        } 	
    }

    @Order(2)
    @Configuration
    public static class ApplicantConfiguration {
        @Bean
        public SecurityFilterChain filterChainApplicant(HttpSecurity http) throws Exception {
        	http.authorizeRequests()
 		   		.antMatchers("/applicant*")
 		   		.authenticated()
 		   		.and()
 		   		.oauth2Login()
 		   		.and()
 		   		.logout().logoutSuccessUrl("/welcome")
 		   		.deleteCookies("JSESSIONID")
	            .and()
	            .exceptionHandling()
	            .accessDeniedPage("/403");
        	return http.build();
        }
	}
}
