package com.springsecurity.template.security;

import com.springsecurity.template.entity.User;
import com.springsecurity.template.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmailAddress) throws UsernameNotFoundException {

        Optional<User> userByUsername = usersRepository.findByUsernameOrEmailAddress(usernameOrEmailAddress);
        if (!userByUsername.isPresent()) {
            throw new UsernameNotFoundException("Invalid credentials!");
        }

        User user = userByUsername.get();
        if (user == null || !(user.getUsername().equals(usernameOrEmailAddress) || user.getEmailAddress().equals(usernameOrEmailAddress))) {
            throw new UsernameNotFoundException("Invalid credentials!");
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (String role : user.getAuthorities().split(",")) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role));
        }

        return new CustomUser(
                user.getFullName(),
                user.getUsername(),
                user.getPassword(),
                user.getEmailAddress(),
                grantedAuthorities,
                user.getCountry(),
                user.getAccountConfirmed(),
                user.getAccountLocked()
        );
    }
}