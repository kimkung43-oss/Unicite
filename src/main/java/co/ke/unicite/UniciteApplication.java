package co.ke.unicite;

import co.ke.unicite.Repositories.RoleRepository;
import co.ke.unicite.Entities.UserManagement.Roles;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UniciteApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniciteApplication.class, args);
	}

	@Autowired
	RoleRepository roleRepository;

	@PostConstruct
	public void createRoles() {
		if (!roleRepository.existsByCode("instructor")) {
			Roles roles = new Roles();
			roles.setCode("instructor");
			roles.setRoleName("Instructor");
			roleRepository.save(roles);
		}
		if (!roleRepository.existsByCode("learner")) {
			Roles roles = new Roles();
			roles.setCode("learner");
			roles.setRoleName("Learner");
			roleRepository.save(roles);
		}
	}


}
