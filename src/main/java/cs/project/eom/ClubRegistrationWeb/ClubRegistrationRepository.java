package cs.project.eom.ClubRegistrationWeb;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRegistrationRepository extends JpaRepository<ClubRegistrationDto, Long> {

	ArrayList<ClubRegistrationDto> findByUserEmailIs(String email);

}