package com.it;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class UserController {

	
//    private final UserRepository userRepository;

	@Autowired
    private UserService userService;

//    public UserController(UserRepository userRepository, UserService userService) {
//        this.userRepository = userRepository;
//        this.userService = userService;
//    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        //return userRepository.save(user);
        return userService.register(user);
    }

    @PostMapping("/login1")
    public String login(@RequestBody User user) {
//        return userService.verify(user);
        User u = userService.findByUserName(user.getUserName());
        User u1=userService.findByPassword(user.getPassword());
//        User u1 = userService.findByUserName(user.getUserName());
        
        System.out.println("user name "+u.getUserName());
        System.out.println("password "+u.getPassword());
        if(!Objects.isNull(u)&&!Objects.isNull(u1))
            return "success";
        return "failure";
    }
    
    
    @PostMapping("/registerUser")
    public boolean registerUser(@RequestBody User user) {
        //return userRepository.save(user);
        return userService.registersUser(user);
    }
    
//    @PostMapping("/login")
//    public boolean verifyUser(@RequestBody User user) {
//    	 System.out.println("user name "+user.getUserName());
//         System.out.println("password "+user.getPassword());
//        return userService.verifyUser(user.getUserName(),user.getPassword());
////        var u = userRepository.findByUserName(user.getUserName());
////        if(!Objects.isNull(u))
////            return "success";
////        return "failure";
//    }
    
    
    @PostMapping("/loginuser")
    public String login12(@RequestBody User user) {
        return userService.verify(user);
    }


}
