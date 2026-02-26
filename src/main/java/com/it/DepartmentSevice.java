package com.it;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentSevice {

	
	@Autowired
	public DepartmentRepository departmentRepository;

	public List<Department> listAll() {
		return departmentRepository.findAll();
	}
	

	public Department createDepartment(Department department) {
		
		return departmentRepository.save(department);
	}
}
