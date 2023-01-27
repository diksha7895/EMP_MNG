package com.employeemanagement.emp;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import com.employeemanagement.emp.model.Employee;
import com.employeemanagement.emp.repository.EmployeeRepository;
import com.employeemanagement.emp.service.EmployeeService;




@RunWith(SpringRunner.class)
@SpringBootTest(classes=EmployeeServiceApplication.class)
class EmployeeServiceApplicationTests {

	@MockBean
	EmployeeRepository repository;
	
	@Autowired
	EmployeeService service;
	 
	 private MockMvc mockMvc;
	
	@Test
	void contextLoads() {
	}
	  
	@Test
    public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception{
        // given - precondition or setup
		EmployeeService mock = org.mockito.Mockito.mock(EmployeeService.class);
        List<Employee> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(new Employee(101,"Daniel","Fransis","daniel123@gmail.com",200));
        listOfEmployees.add(new Employee(102,"Jakab","Fransis","jakab123@gmail.com",100));
        when(service.getAllEmployees()).thenReturn(listOfEmployees);
        assertEquals(2,service.getAllEmployees().size());

    }
	
//	 @Test
//	    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception{
//	        // given - precondition or setup
//	        long employeeId = 101;
//	        Employee employee = new Employee(101,"Daniel","Fransis","daniel123@gmail.com",200);
//	         when(service.getEmployeeById(employeeId)).thenReturn(employee);
//	        assertEquals(101,service.getEmployeeById(employeeId).getId());
//
//	    }

}
