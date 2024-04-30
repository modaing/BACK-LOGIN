package com.insider.login.common.utils;

import com.insider.login.member.entity.Member;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtils {

    private static String jwtSecretKey;     // secret key
    private static Long tokenValidateTime;  // token 만료시간


    @Value("${jwt.key}") // Application file에서 설정 정보를 불러와서 setting할 것
    public void setJwtSecretKey (String jwtSecretKey) {
        TokenUtils.jwtSecretKey = jwtSecretKey; // jwt.key에서 값을 받아와서 setting한다
    }

    @Value("${jwt.time}")
    public void setTokenValidateTime (Long tokenValidateTime) { // 정적 변수이기 때문에 class명.field명
        TokenUtils.tokenValidateTime = tokenValidateTime;
    }

    /*
    * token값만 떼서 사용을 하기 위해서
    * header는 "Bearer " + token이기 때문에 token만 빼오기 위해서 작성하는 logic
    * */
    public static String splitHeader(String header) {
        if (header != null) {
            return header.split(" ")[1];
            } else {
                return null;
        }
    }

    public static boolean isValidToken(String token) {
        // true or false 반환
        System.out.println("token만 잘 가져왔는지: " + token); // 확인용
        try {
            Claims claims = getClaimsFromToken(token); // 정상적으로 작동이 되면
            System.out.println("Claims에 담은 정보들: " + claims);
            return true;
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            return false;
        } catch (JwtException e) {
            e.printStackTrace();
            return false;
        } catch (NullPointerException e) { // token이 null인 경우
            e.printStackTrace();
            return false;
        }
    }

    public static Claims getClaimsFromToken (String token) {
        // Jwts parsing을 해서, 복호화를 하고 data를 가지고 온다
        // setSigningKey -> binary로 encoding하는 방식이다
        // parseClaims -> token을 비교한다
//        return Jwts.parser()
//                .setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecretKey)) // secret key를 넣어서 복호화 setting해주고
//                .parseClaimsJws(token) // 값을 제대로 받았으면 -> header, payload, signature로 분리를 한다
//                .getBody(); // payload의 값을 Claims로 반환을 한다 (return)
//        System.out.println("😭😭😭😭😭😭 claims의 문제..");

        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecretKey)) // secret key를 넣어서 복호화 setting해주고
                .parseClaimsJws(token) // 값을 제대로 받았으면 -> header, payload, signature로 분리를 한다
                .getBody();
        System.out.println("😭😭😭" + claims);
        return claims;
    }

    /* token을 생성하는 method */
    public static String generateJwtToken(Member member) {
        // 만료시간도 추가해야한다
        Date expireTime = new Date(System.currentTimeMillis() + tokenValidateTime);
        System.out.println("expire time: " + expireTime);
        System.out.println("로그인한 구성원의 정보 (in Token Utils): " + member);

        /*
        * token에서는 header, payload, signature가 존재를 하기 때문에 각각 값들을 입력을 해줘야 한다
        * - header: setHeader
        * - payload: setSubject
        * - signature: signWith
        * */
        JwtBuilder builder = Jwts.builder()                             // used to construct JWTs
                .setHeader(createHeader())
                .setClaims(createClaims(member))                        // payload에다가 user data를 넣어준다
//                .setSubject("insider's token: " + member.getMemberId()) // token의 제목을 넣어준다
                .setSubject("" + member.getMemberId())
                .signWith(SignatureAlgorithm.HS256, createSignature())  // 반환 받은 key값으로 sign in
                .setExpiration(expireTime);                             // 만료시간
        System.out.println("builder의 정보: " + builder);

        return builder.compact(); // token을 String형식으로 바꿔서 반환을 해준다
    }

    /* token의 header를 설정하는 method */
    private static Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        // token에 대한 정보들이 들어간다
        header.put("type", "jwt");                      // json type
        header.put("alg", "HS256");                     // algorithm 방식
        header.put("date", System.currentTimeMillis()); // 만들어준 시간

        System.out.println("header 정보: " + header);

        return header;
    }

    // putting in informations that will be easy to be taken out
    private static Map<String, Object> createClaims(Member member) {
        Map<String, Object> claims = new HashMap<>();
        System.out.println("member 정보: " + member);

        claims.put("departName",member.getDepartment().getDepartName());    // 부서 이름
        claims.put("positionName", member.getPosition().getPositionName()); // 직급
        claims.put("image", member.getImage().getMemberImagePath());        // 이미지 경로
        claims.put("userName", member.getName());   // claims에다가 정보를 입력하는 것들...
        claims.put("Role", member.getRole());
        claims.put("memberId", member.getMemberId());
        String memberStatus123 = member.getMemberStatus();
        System.out.println("memberStatus: " + memberStatus123);
        claims.put("memberStatus", memberStatus123);               // 상태 추가

//        claims.put("employedDate", member.getEmployedDate());               // 입사일자 추가

        System.out.println("🧥🧥🧥🧥🧥🧥 claims에 담은 memberId 정보: " + claims.get("memberId")); // 확인용
        System.out.println("Claims에 담은 정보들: " + claims);
        System.out.println("memberStatus: " + claims.get("memberStatus"));

        return claims;
    }

    /* JWT 서명을 발급하는 method */
    private static Key createSignature() {                                              // key는 signature를 가지고 확인을 한다
        byte[] secretBytes = DatatypeConverter.parseBase64Binary(jwtSecretKey);         // secret key -> parsed into byte array, which is used for signing JWTs
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;               // set to HMAC-SHA256, widely used for generating secure cryptographic signatures
        return new SecretKeySpec(secretBytes, signatureAlgorithm.getJcaName());         // constructs specification for the secret key, which can be used for cryptographic operations

        /* in summary...
        * - takes Base64-encoded secret key, convert into byte array, select HS256 signature algorithm, and construct a secret key specification using the provided secret key bytes and selected algorithm.
        * - The resulti ng 'Key' is then used for signing JTWs.
        * */
    }



    /*
    * 이렇게 method를 분리 시킨 이유:
    * 1. private으로 접근을 제한을 하기 위해서
    * 2. debugging 하기 편하다
    * */
}
