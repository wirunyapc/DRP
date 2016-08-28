package com.drpweb;

import com.drpweb.role.Role;
import com.drpweb.role.RoleRepository;
import com.drpweb.user.User;
import com.drpweb.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class DrpWebApplication implements CommandLineRunner {
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	public static void main(String[] args) {
		SpringApplication.run(DrpWebApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		/*Role admin = new Role("admin");
		Role member = new Role("member");
		Role patient = new Role("patient");
		roleRepository.save(admin);
		roleRepository.save(member);
		roleRepository.save(patient);
		Set<Role> roles = new HashSet<>();
		roles.add(admin);
		User userAdmin = new User("admin","admin","admin@admin.com","1234",roles);
		userRepository.save(userAdmin);*/
	}
}
