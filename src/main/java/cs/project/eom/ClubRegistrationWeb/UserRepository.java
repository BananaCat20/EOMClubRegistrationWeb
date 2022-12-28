package cs.project.eom.ClubRegistrationWeb;

import org.springframework.data.jpa.repository.JpaRepository;

// Used by UserDto
public interface UserRepository extends JpaRepository<UserDto, Long> {

}