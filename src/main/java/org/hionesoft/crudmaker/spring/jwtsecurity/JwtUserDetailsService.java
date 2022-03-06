package org.hionesoft.crudmaker.spring.jwtsecurity;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public JwtUserDetailsDTO loadUserByUsername(String userId) throws UsernameNotFoundException {
        JwtUserDetailsDTO loginVo = new JwtUserDetailsDTO();

        return loginVo;
    }

    public JwtUserDetailsDTO authenticateByEmailAndPassword(String userId, String password) {
        JwtUserDetailsDTO loginVo = new JwtUserDetailsDTO();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(!passwordEncoder.matches(password, loginVo.getPassword())) {
            throw new BadCredentialsException("Password not matched");
        }

        return loginVo;
    }
}
