package com.womenhz.swee.service;


import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.womenhz.swee.dao.UserRepository;
import com.womenhz.swee.model.User;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SearchService searchService;
	
	public User getById(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            return userOpt.get();
        }
        return null;
    }

    public Long create(User user) {
        User save = userRepository.save(user);
        searchService.index(save.getId());
        return save.getId();
    }



}
