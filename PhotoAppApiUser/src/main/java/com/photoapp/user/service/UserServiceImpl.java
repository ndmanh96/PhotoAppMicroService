package com.photoapp.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.photoapp.user.dto.UserDto;
import com.photoapp.user.entity.UserEntity;
import com.photoapp.user.feignclient.AlbumsServiceClient;
import com.photoapp.user.repository.UserRepository;
import com.photoapp.user.request.AlbumResponseModel;

import feign.FeignException;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEnconder;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private RestTemplate restTeamplate;
	
	@Autowired
	private AlbumsServiceClient albumsServiceClient;
	
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public UserDto createUser(UserDto userDto) {
		// TODO Auto-generated method stub
		encryptedPassword(userDto);
		userDto.setUserId(UUID.randomUUID().toString());
		UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
		userRepository.save(userEntity);
		
		return userDto;
	}
	
	
	private void encryptedPassword(UserDto userDto) {
		String encryptedPassword = passwordEnconder.encode(userDto.getPassword());
		userDto.setPassword(encryptedPassword);
	}


	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByEmail(email);
		
		if (user == null) throw new UsernameNotFoundException(email);
		
		return new User(user.getEmail(), user.getPassword(), true, true, true, true, new ArrayList<>());
		
	}


	@Override
	public UserDto getUserDtoByEmail(String email) {
		UserEntity user = userRepository.findByEmail(email);
		
		if (user == null) throw new UsernameNotFoundException(email);
	
		UserDto userDto = modelMapper.map(user, UserDto.class);
		
		return userDto;
	}


	@Override
	public UserDto getUserByuserId(String userId) {
		UserEntity user = userRepository.findByUserId(userId);
		
		if (user == null) throw new UsernameNotFoundException(userId);
	
		UserDto userDto = modelMapper.map(user, UserDto.class);
		
//		//Using RestTemplate to get response from PhotoAppApiAlbums to get album
//		String albumUrl=String.format("http://ALBUMS-WS/users/%s/albums", userId); 
//		ResponseEntity<List<AlbumResponseModel>> albumResponse = restTeamplate.exchange(albumUrl, 
//																		HttpMethod.GET, 
//																		null, 
//																		new ParameterizedTypeReference<List<AlbumResponseModel>>() {
//																		});
//				
//		List<AlbumResponseModel> albums = albumResponse.getBody();
		
		//Use Feign Client to request
		List<AlbumResponseModel> albums = new ArrayList<>();

		albums = albumsServiceClient.getAlbums(userId);
		
		userDto.setAlbums(albums);
		
		return userDto;
	}

}
