package com.employeemanagement;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.employeemanagement.user.UserServiceApplication;
import com.employeemanagement.user.model.User;
import com.employeemanagement.user.repository.UserRepository;
import com.employeemanagement.user.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=UserServiceApplication.class)
class UserServiceApplicationTests {
	
	
	@Autowired
	private UserService service;
	
	@MockBean
	private UserRepository repository;
	
	private MockMvc mockMvc;
	
	

	@Test
	public void getAllUsersTest() {
		when(repository.findAll()).thenReturn(Stream
				.of(new User("Daniel","Fransis","DennyFra","daniel123@gmail.com","Daniela@123"),
					new User("Jakab","Fransis","JakabFra","jakab123@gmail.com","Jakab@123"))
				.collect(Collectors.toList()));
		assertEquals(2,service.getAllUsers().size());
	}

	
	
}
