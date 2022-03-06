package org.hionesoft.crudmaker.spring.jwtsecurity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@CrossOrigin
public class JwtAuthenticationController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        // 1. 로그인 정보 확인
//        final LoginUserVo loginVo = userDetailService.authenticateByEmailAndPassword
//                (authenticationRequest.getUserId(), authenticationRequest.getPassword());

        // 2. jwt 생성
        UserDetails userDetails = new JwtUserDetailsDTO();

        final String token = jwtTokenUtil.generateToken(userDetails);

        // TODO 3. pushToken db 저장


        return ResponseEntity.ok(new JwtResponse(token));
    }

}

@Data
class JwtRequest {
    private String userId;
    private String password;
    private String pushToken;
    private String jwt;
}

@Data
@AllArgsConstructor
class JwtResponse {
    private String token;
}
