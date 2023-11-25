package com.hackathon.hackathon.domain.user.controller;

import com.hackathon.hackathon.domain.user.dto.request.SignUpRequestDto;
import com.hackathon.hackathon.domain.user.entity.User;
import com.hackathon.hackathon.domain.user.service.UserDomainService;
import com.hackathon.hackathon.global.response.SuccessResponse;
import com.hackathon.hackathon.global.utils.AuthentiatedUserUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/")
@Tag(name = "추가 정보 받는 api", description = "추가 정보 받는 api")
@SecurityRequirement(name = "access-token")
public class UserController {
    private final UserDomainService userDomainService;
    private final AuthentiatedUserUtils authentiatedUserUtils;
    @PostMapping("/info")
    public SuccessResponse<Object> signUpUser(@RequestBody SignUpRequestDto requestDto) {

            User user = authentiatedUserUtils.getCurrentUser(); // 유저 정보 가져오기

            User newUser = userDomainService.getAccount(requestDto,user);
            System.out.println(user.getName());
            SuccessResponse<Object> successResponse = SuccessResponse.onSuccess(200, newUser);
            return successResponse;
    }
}
