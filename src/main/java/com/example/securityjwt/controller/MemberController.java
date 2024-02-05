package com.example.securityjwt.controller;


import com.example.securityjwt.common.validator.CustomValidators;
import com.example.securityjwt.controller.request.UserJoinRequest;
import com.example.securityjwt.controller.request.UserLoginRequest;
import com.example.securityjwt.controller.response.Response;
import com.example.securityjwt.model.User;
import com.example.securityjwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final UserService userService;

    private final CustomValidators.EmailValidator EmailValidator;
//    private final CustomValidators.NicknameValidator NicknameValidator;
    private final CustomValidators.UseridValidator UserIdValidator;

    /* 커스텀 유효성 검증을 위해 추가 */
    @InitBinder
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(EmailValidator);
//        binder.addValidators(NicknameValidator);
        binder.addValidators(UserIdValidator);
    }

    @PostMapping("/join")
    @ResponseBody
    public Response join(@Valid @RequestBody UserJoinRequest request/*@RequestParam("userid") String userid,
                                 @RequestParam("email") String email,
                                 @RequestParam("username") String username,
                                 @RequestParam("password") String password*/
    , Errors errors) {

        if (errors.hasErrors()) {

            /* 유효성 통과 못한 필드와 메시지를 핸들링 */
            Map<String, String> validatorResult = userService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
//                model.addAttribute(key, validatorResult.get(key));
                return Response.error(validatorResult.get(key));
            }
        }


        User joinedUser = userService.join(request.getUserid(), request.getUsername(), request.getPassword(), request.getEmail());
        return Response.success(joinedUser.toString());
    }

    @PostMapping("/login")
    public Response<String> login(@Valid @RequestBody UserLoginRequest request) {
        String token = userService.login(request.getUserid(), request.getPassword());
        return Response.success(token);
    }

    //아이디 찾기
    @PostMapping("/findId")
    @ResponseBody
    public Response findId(@RequestBody UserJoinRequest request) {
        String userid = userService.findId(request.getUsername(), request.getEmail());
        return Response.success(userid);
    }

    //비밀번호 초기화
    @PostMapping("/resetPassword")
    @ResponseBody
    public Response resetPassword(@RequestBody UserJoinRequest request) {
        String password = userService.resetPassword(request.getUserid(), request.getUsername(), request.getEmail(), request.getPassword());
        return Response.success(password);
    }

    //회원탈퇴
    @DeleteMapping("/deleteUser")
    @ResponseBody
    public Response deleteUser(@AuthenticationPrincipal User userDetails) {
        String userid = userService.deleteUser(userDetails.getUserid());
        return Response.success("");
    }

}
