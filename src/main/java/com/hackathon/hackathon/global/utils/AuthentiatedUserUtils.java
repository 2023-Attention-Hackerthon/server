package com.hackathon.hackathon.global.utils;

import com.hackathon.hackathon.domain.user.adapter.UserAdaptor;
import com.hackathon.hackathon.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthentiatedUserUtils {
    private final UserAdaptor userAdaptor;

    public Long getCurrentUserId() {
        return SecurityUtils.getCurrentUserId();
    }

    public User getCurrentUser() {
        return userAdaptor.findById(getCurrentUserId());
    }
}
