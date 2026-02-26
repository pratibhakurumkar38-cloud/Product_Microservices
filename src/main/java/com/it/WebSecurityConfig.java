package com.it;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

//    private final EmployeeService userDetailsService;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	 private JwtAuthenticationFilter jwtAuthenticationFilter;
	

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(
//                        request -> request
//                                .requestMatchers("register","login").permitAll()
//                                .anyRequest().authenticated()
//                )
//                .httpBasic(Customizer.withDefaults())
//                .addFilterBefore(jwtAuthenticationFilter,
//                        UsernamePasswordAuthenticationFilter.class);
//        return httpSecurity.build();
//    }
    
   
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
    	
//    	httpSecurity.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))//when we use  X-XSRF-TOKEN meaning cookie based csrf
//httpSecurity.csrf(Customizer.withDefaults())  //when we use X-CSRF-TOKEN meaning session based csrf
    	httpSecurity.
    	csrf(csrf->csrf.disable())
    	.
    	
    	
    	authorizeHttpRequests(auth -> auth
                .requestMatchers("register","login1","registerUser","loginuser").permitAll().anyRequest().authenticated())  
//    	.formLogin(Customizer.withDefaults()) //adding form instead of popup
//    	;//
    	.httpBasic(Customizer.withDefaults())// <-- ensures Basic Auth filter is active
    	
    	  .addFilterBefore(jwtAuthenticationFilter,
                  UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
    
    
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails akshay
//                = User.withUsername("akshay")
//                .password("{noop}password") //it has password encoder which convert encode internally
//                .roles("USER")
//                .build();
//
//        UserDetails john
//                = User.withUsername("john")
//                .password("{noop}john")
//                .roles("USER")
//                .build();
//        
//        
//        UserDetails santoshi
//        = User.withUsername("santoshi")
//        .password("{noop}santoshi")
//        .roles("USER")
//        .build();
//        
//        return new InMemoryUserDetailsManager(akshay,john,santoshi);
//    }
    
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
    	
    	return new BCryptPasswordEncoder(12);
    	
    }
//    
  //  AuthenticationProvider  is the worker that actually validates credentials.

    @Bean
    public AuthenticationProvider authenticationProvider(){
    	
    	DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
    	daoAuthenticationProvider.setUserDetailsService(userDetailsService);
    	
//    	daoAuthenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
    	daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
    	
    	return daoAuthenticationProvider;
    }
    
    //AuthenticationManager is the orchestrator (- An orchestrator is the coordinator or director.)that receives authentication requests

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }
	
//@Bean
//	 public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//       httpSecurity
//               .csrf(csrf -> csrf.disable())
//               .authorizeHttpRequests(
//                       request -> request
// .anyRequest().authenticated()
//               )
//               .httpBasic(Customizer.withDefaults());
//       return httpSecurity.build();
//	 }

//    @Bean
//    DefaultSecurityFilterChain springSecurity(HttpSecurity http) throws Exception {
//        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
//        // set the name of the attribute the CsrfToken will be populated on
//        requestHandler.setCsrfRequestAttributeName(null);
//        http
//            // ...
//            .csrf((csrf) -> csrf
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .csrfTokenRequestHandler(requestHandler)
//            );
//        return http.build();
//    }

}
