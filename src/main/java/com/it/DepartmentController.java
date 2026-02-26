package com.it;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/department")
public class DepartmentController {


	@Autowired
	public DepartmentSevice departmentSevice;
	
	private static final Logger LOGGER
    = LoggerFactory.getLogger(DepartmentController.class);
	
	@GetMapping("/home")
	public String home() {

		return "Welcome to first demo page";

	}
	
	@GetMapping("/departments")
	public List<Department> list() {
		  LOGGER.info("depart service findAll");
	    return departmentSevice.listAll();
	}
	
	@PostMapping("/departments")
	public ResponseEntity<Department> createProduct(@RequestBody Department department){
		
		Department department2 = departmentSevice.createDepartment(department);
		return new ResponseEntity<Department>(department2,HttpStatus.CREATED);
	}
	
	@GetMapping("/csrf")
	public CsrfToken getToken(HttpServletRequest request) {
		
		return (CsrfToken) request.getAttribute("_csrf");
		
	}
	
}
