package com.photoapp.user.request;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class UserDetailRequest {
	@NotNull(message = "Email cannot be null")
	@Email
	private String email;
	
	@NotNull(message = "First name cannot be null")
	@Size(min=2, message = "First name must not be less than 2 characters")
	private String firstName;
	
	@NotNull(message = "Last name cannot be null")
	@Size(min=2, message = "Last name must not be less than 2 characters")
	private String lastName;
	
	@NotNull(message = "Password cannot be null")
	@Size(min=8, max = 64,  message = "First name must be less than 16 characters and grater than 8 characters")
	private String password;
	
	public UserDetailRequest() {}

	public UserDetailRequest(String email, String firstName, String lastName, String password) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}

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

	@Override
	public String toString() {
		return "UserDetailRequest [email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", password=" + password + "]";
	}
	
	
	
}
