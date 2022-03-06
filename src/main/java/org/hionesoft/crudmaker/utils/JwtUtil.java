package org.hionesoft.crudmaker.utils;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {
    public String encodeJwt(Map<String, Object> headers, Map<String, Object> payloads, Date ext, String secretKey) {
        return Jwts.builder()
                .setHeader(headers) // Headers 설정
                .setClaims(payloads) // Claims 설정
                //.setSubject("user") // 토큰 용도
                .setExpiration(ext) // 토큰 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes()) // HS256과 Key로 Sign
                .compact(); // 토큰 생성
    }

    // ExpiredJwtException : 유효 기간이 지난 JWT를 수신한 경우
    // UnsupportedJwtException : jwt가 구조적으로 문제를 가지고 있음
    public Claims decodeJwt(String jwt, String secretKey) throws Exception, ExpiredJwtException, UnsupportedJwtException {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .parseClaimsJws(jwt).getBody();
    }
}
