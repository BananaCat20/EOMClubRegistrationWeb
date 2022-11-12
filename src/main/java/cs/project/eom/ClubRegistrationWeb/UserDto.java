package cs.project.eom.ClubRegistrationWeb;

import javax.persistence.*;

// https://www.codejava.net/frameworks/spring-boot/user-registration-and-login-tutorial

@Entity
@Table(name = "user")
public class UserDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(name = "username", nullable = false, length = 20)
    private String userName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

     
}
