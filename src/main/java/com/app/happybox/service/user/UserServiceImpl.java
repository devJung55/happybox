package com.app.happybox.service.user;

import com.app.happybox.domain.AddressDTO;
import com.app.happybox.entity.user.User;
import com.app.happybox.provider.UserDetail;
import com.app.happybox.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

//    id로 주소 조회
    @Override
    public AddressDTO findAddressById(Long id) {
        return toAddressDTO(userRepository.findAddressById(id).get());
    }

    //    UserDetail의 값불러오기
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(username).orElseThrow(()-> new UsernameNotFoundException(username + " not found"));
        return UserDetail.builder()
                .id(user.getId())
                .userId((user.getUserId()))
                .userPassword(user.getUserPassword())
                .userRole(user.getUserRole())
                .build();
    }
}
