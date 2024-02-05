package com.example.securityjwt.common.validator;

import com.example.securityjwt.controller.request.UserJoinRequest;
import com.example.securityjwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * 중복 확인 유효성 검증을 위한 커스텀 Validator 클래스
 */
@RequiredArgsConstructor
@Component
public class CustomValidators {

    @RequiredArgsConstructor
    @Component
    public static class EmailValidator extends AbstractValidator<UserJoinRequest> {
        private final UserService userService;

        @Override
        protected void doValidate(UserJoinRequest dto, Errors errors) {
            if (userService.existsByEmail(dto.getEmail())) {
                errors.rejectValue("email", "이메일 중복 오류", "이미 사용중인 이메일 입니다.");
            }
        }
    }

    @RequiredArgsConstructor
    @Component
//    public static class UsernameValidator extends AbstractValidator<UserDto.Request> {
    public static class UseridValidator extends AbstractValidator<UserJoinRequest> {
//        private final UserRepository userRepository;
        private final UserService userService;

        @Override
//        protected void doValidate(UserJoinRequest dto, Errors errors) {
        protected void doValidate(UserJoinRequest request, Errors errors) {
//            if (UserService.existsByUsername(dto.toEntity().getUsername())) {
            if (userService.existsByUserid(request.getUserid())) {
                errors.rejectValue("username", "아이디 중복 오류", "이미 사용중인 아이디 입니다.");
            }
        }
    }
}
