package com.employeemanagement;



import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import com.employeemanagement.user.UserServiceApplication;


/*@RunWith(SpringRunner.class)
@SpringBootTest
(classes=UserServiceApplication.class)*/

//@SpringBootTest
//class UserServiceApplicationTests {
	
	
/*	@Autowired
	private UserService service;
	
	@MockBean
	private UserRepository repository;
	
	private MockMvc mockMvc; */
	
//	@Test
//	void contextLoads() {
//	}
//	
/*
	@Test
	public void getAllUsersTest() {
		
		when(repository.findAll()).thenReturn(Stream
				.of(new User("Daniel","Fransis","DennyFra","daniel123@gmail.com","Daniela@123"),
					new User("Jakab","Fransis","JakabFra","jakab123@gmail.com","Jakab@123"))
				.collect(Collectors.toList()));
		assertEquals(2,service.getAllUsers().size());
	}
*/
	
	






@SpringBootTest(classes=UserServiceApplication.class)
public class UserServiceApplicationTests {

   @Test
   public void contextLoads() {
   }


}
