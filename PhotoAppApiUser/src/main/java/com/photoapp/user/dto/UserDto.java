package com.photoapp.user.dto;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.photoapp.user.request.AlbumResponseModel;

public class UserDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9142052048834088861L;
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	private String userId;
	private List<AlbumResponseModel> albums;
	
	public UserDto() {}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	

	public List<AlbumResponseModel> getAlbums() {
		return albums;
	}


	public void setAlbums(List<AlbumResponseModel> albums) {
		this.albums = albums;
	}


	public UserDto(String email, String firstName, String lastName, String password, String userId) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.userId=userId;
	}


	@Override
	public String toString() {
		return "UserDto [email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", password="
				+ password + ", userId=" + userId + "]";
	}

	
	
	
}
