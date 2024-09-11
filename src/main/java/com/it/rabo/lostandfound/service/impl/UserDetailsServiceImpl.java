package com.it.rabo.lostandfound.service.impl;

import com.it.rabo.lostandfound.entity.UserDetails;
import com.it.rabo.lostandfound.repository.UserDetailsRepository;
import com.it.rabo.lostandfound.service.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserDetailsRepository userDetailsRepository;

    public UserDetailsServiceImpl(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public UserDetails getUserDetails() {
        return userDetailsRepository.findById(1L).orElseThrow();
    }
}
