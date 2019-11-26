package com.womenhz.swee.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.womenhz.swee.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findById(Long aLong);
}
