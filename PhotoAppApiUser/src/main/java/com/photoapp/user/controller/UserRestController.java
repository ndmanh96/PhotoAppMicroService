package com.photoapp.user.controller;


import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.photoapp.user.dto.UserDto;
import com.photoapp.user.request.AlbumResponseModel;
import com.photoapp.user.request.UserDetailRequest;
import com.photoapp.user.request.UserDetailResponse;
import com.photoapp.user.request.UserResponseModel;
import com.photoapp.user.service.UserService;

@RestController
@RequestMapping("/users")
public class UserRestController {
	
	@Autowired
	private Environment env;
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserService userService;
	
	@Autowired
	private RestTemplate restTeamplate;
	
	
	@GetMapping("/status/check")
	public String checkStatus() {
		return "User WS Checking OK on Port: "+env.getProperty("local.server.port");
	}
	
	@PostMapping
	public ResponseEntity<UserDetailResponse> createUser(@Valid @RequestBody UserDetailRequest user) {
		

		UserDto userDto = modelMapper.map(user, UserDto.class);

		UserDto createUserDto = userService.createUser(userDto);
		
		UserDetailResponse response = modelMapper.map(createUserDto, UserDetailResponse.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping
	public List<UserDetailRequest> findAll() {
		List<UserDetailRequest> users = new ArrayList<UserDetailRequest>();
		users.add(new UserDetailRequest("manh.nd4@samsung.com", "Nguyen", "Manh", "12345678"));
		return users;
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserResponseModel> getUser(@PathVariable("userId") String userId) 
	{
		UserDto userDto = userService.getUserByuserId(userId);
		UserResponseModel response = modelMapper.map(userDto, UserResponseModel.class);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
}
