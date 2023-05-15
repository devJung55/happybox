package com.app.happybox.service.user;

import com.app.happybox.repository.user.UserRepository;
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
}
