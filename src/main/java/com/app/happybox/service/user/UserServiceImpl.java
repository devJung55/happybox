package com.app.happybox.service.user;

import com.app.happybox.domain.AddressDTO;
import com.app.happybox.domain.user.MailDTO;
import com.app.happybox.entity.order.OrderSubscription;
import com.app.happybox.entity.user.User;
import com.app.happybox.provider.UserDetail;
import com.app.happybox.repository.user.UserRepository;
import com.app.happybox.type.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Qualifier("user") @Primary
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

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

    @Override
    public User getDetailByUserId(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            return user.get();
        } else {
            return null;
        }
    }

    //    회원 전화번호로 회원 id 찾기
    @Override
    public String findUserIdByUserPhone(String userPhoneNumber) {
        return userRepository.findUserIdByUserPhone_QueryDSL(userPhoneNumber);
    }

    //    회원 이메일로 회원 id 찾기
    @Override
    public String findUserIdByUserEmail(String userEmail) {
        return userRepository.findUserIdByEmail_QueryDSL(userEmail);
    }

    //    회원 전화번호로 회원 이메일 찾기
    @Override
    public Optional<String> findUserEmailByPhone(String userPhoneNumber) {
        return userRepository.findUserEmailByUserPhone_QueryDSL(userPhoneNumber);
    }

    //    이메일로 유저 찾기(MailSend용)
    @Override
    public Optional<User> findUser(String userEmail) {
        return userRepository.findByUserEmail(userEmail);
    }

    //    회원 비밀번호 업데이트
    @Override
    public void updatePassword(String userEmail, String password, PasswordEncoder passwordEncoder) {
        User user = userRepository.findByUserEmail(userEmail).get();
        user.setUserPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public void sendMail(MailDTO mail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail.getAddress());
        message.setFrom("happywelfarebox@gmail.com");
//        from 값을 설정하지 않으면 application.yml의 username값이 설정됩니다.
        message.setSubject(mail.getTitle());
        message.setText(mail.getMessage());

        mailSender.send(message);
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

//    회원탈퇴
    @Override @Transactional
    public void updateUserStatusByUserId(Long userId) {
        userRepository.findById(userId).ifPresent(user -> user.setUserStatus(UserStatus.UNREGISTERED));
    }
}