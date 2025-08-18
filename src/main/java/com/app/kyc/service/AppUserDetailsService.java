package com.app.kyc.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.kyc.entity.User;
import com.app.kyc.repository.UserRepository;

import lombok.SneakyThrows;

@Service
public class AppUserDetailsService implements UserDetailsService
{

   @Autowired
   UserRepository userRepository;

   @SneakyThrows
   @Override
   public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
   {
      User user = userRepository.findByEmail(email);
      return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
   }

}
