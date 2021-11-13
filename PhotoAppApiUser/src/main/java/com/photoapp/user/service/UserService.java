package com.photoapp.user.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.photoapp.user.dto.UserDto;

public interface UserService extends UserDetailsService{
	public UserDto createUser(UserDto userDto);
	public UserDto getUserDtoByEmail(String email);
	public UserDto getUserByuserId(String userId);
}
