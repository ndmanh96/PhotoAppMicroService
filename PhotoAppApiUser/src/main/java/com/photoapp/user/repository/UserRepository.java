package com.photoapp.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.photoapp.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	public UserEntity findByEmail(String email);
	public UserEntity findByUserId(String userId);
}
