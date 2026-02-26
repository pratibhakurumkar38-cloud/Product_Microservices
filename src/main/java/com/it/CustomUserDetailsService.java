package com.it;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	 public UserRepository userRepository;
	
		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

			User user = userRepository.findByUserName(username);

			if (Objects.isNull(user)) {

			throw new UserNameNotFoundException("User not found");
//				throw new Exception("User not found");
				

			}
			return new CustomUserDetails(user);
		}

	}
