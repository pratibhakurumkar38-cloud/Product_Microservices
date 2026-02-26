package com.it;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	public UserRepository userRepository;
//	private final UserRepository userRepository = null;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;

	public User register(User user) {

		return userRepository.save(user);
	}

	public List<User> listAll() {
		return userRepository.findAll();
	}

	public User findByUserName(String userName) {
		
		User user=userRepository.findByUserName(userName);
		return user;
	}

	public User findByPassword(String password) {
		User user=userRepository.findByPassword(password);
		return user;
	}

//	public boolean verifyUser(String username, String rawPassword) {
//	    User user = userRepository.findByUserName(username);
//	    if (user == null) { 
//	        return false;
//	    }
//	    return bCryptPasswordEncoder.matches(rawPassword, user.getPassword());
//	}


	public String verify(User user) {
		Authentication authenticate = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
		if (authenticate.isAuthenticated())
//			return "2142325345345345";
			return jwtService.generateToken(user);
		return "failure";
	}
	
	public boolean registersUser(User user) {
//	    User user = userRepository.findByUserName(username);
//	    if (user == null) {
//	        return false;
//	    }
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
	    return userRepository.save(user) != null;
	}


	
	
}
