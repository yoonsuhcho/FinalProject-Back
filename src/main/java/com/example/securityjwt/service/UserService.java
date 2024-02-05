package com.example.securityjwt.service;


import com.example.securityjwt.model.User;
import com.example.securityjwt.model.entity.UserEntity;
import com.example.securityjwt.repository.UserEntityRepository;
import com.example.securityjwt.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token-expired-time-ms}")
    private Long expiredTimeMs;

    public boolean existsByUserid(String userid) {
        return userEntityRepository.existsByUserid(userid);
    }


    @Transactional
    public User join(String userid, String username, String password, String email) {

        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());

        UserEntity userEntity = userEntityRepository.save(UserEntity.of(userid, username, encoder.encode(password), email));
        return User.fromEntity(userEntity);
    }

    public User loadUserByUserId(String userid) {
        UserEntity userEntity = userEntityRepository.findByUserid(userid).get();
        return User.fromEntity(userEntity);
    }

    public String login(String userid, String password) {
//        User user = loadUserByUsername(userid);
        User user = loadUserByUserId(userid);
        if (!encoder.matches(password, user.getPassword())) {
            return "password error";
        }
        String token = JwtTokenUtils.generateToken(userid, secretKey, expiredTimeMs);
        return token;
    }

    /* 회원가입 시, 유효성 검사 및 중복 체크 */
    @Transactional(readOnly = true)
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        /* 유효성 검사, 중복 검사에 실패한 필드 목록을 받음 */
        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

    public String findId(String username, String email) {

        return userEntityRepository.findByUsernameAndEmail(username, email).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        ).getUserid();
    }

    public String resetPassword(String userid, String username, String email, String newPassword) {
//        String encodedPassword = Base64.getEncoder().encodeToString(newPassword.getBytes());
        UserEntity userEntity = userEntityRepository.findByUseridAndUsernameAndEmail(userid, username, email).get(0);
        userEntity.setPassword(encoder.encode(newPassword));
        userEntityRepository.save(userEntity);
        return newPassword;
    }

    public boolean existsByEmail(String email) {
        return userEntityRepository.existsByEmail(email);
    }

    public String deleteUser(String userid) {
//        UserEntity userEntity = userEntityRepository.findByUseridAndUsernameAndEmail(userid, username, email).get(0);
        UserEntity userEntity = userEntityRepository.findByUserid(userid).get();
//        if (!encoder.matches(password, userEntity.getPassword())) {
//            return "password error";
//        }
        userEntityRepository.delete(userEntity);
        return "delete success";
    }
}
