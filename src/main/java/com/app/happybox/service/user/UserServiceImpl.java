package com.app.happybox.service.user;

import com.app.happybox.entity.user.User;
import com.app.happybox.repository.user.UserRepository;
import com.app.happybox.type.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Qualifier("user") @Primary
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void deleteByMemberId(Long userId) {
        userRepository.deleteById(userId);
    }

//    아이디 중복검사
    @Override
    public Boolean existsUserByUserId(String userId) {
        return userRepository.existsUserByUserId(userId);
    }

//    이메일 중복검사
    @Override
    public Boolean existsUserByUserEmail(String userEmail) {
        return userRepository.existsUserByUserEmail(userEmail);
    }

//    휴대폰 중복검사
    @Override
    public Boolean existsUserByUserPhoneNumber(String userPhoneNumber) {
        return userRepository.existsUserByUserPhoneNumber(userPhoneNumber);
    }

    @Override
    public void updateUserStatusByUserId(Long userId) {
        userRepository.findById(userId).ifPresent(user -> user.setUserStatus(UserStatus.UNREGISTERED));
    }
}
