package com.app.happybox.service.user;

import com.app.happybox.domain.AddressDTO;
import com.app.happybox.domain.user.MailDTO;
import com.app.happybox.domain.user.MemberDTO;
import com.app.happybox.entity.user.Address;
import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public interface UserService extends UserDetailsService {
//    관리자 회원 삭제
    public void deleteByMemberId(Long userId);

    //    아이디중복체크
    public Boolean existsUserByUserId(String userId);

    //    이메일 중복체크
    public Boolean existsUserByUserEmail(String userEmail);

    //    휴대폰 중복체크
    public Boolean existsUserByUserPhoneNumber(String userPhoneNumber);

    //    id로 주소 조회
    public AddressDTO findAddressById(Long id);

//    회원 정보 조회
    public User getDetailByUserId(Long id);

    //    회원 전화번호로 회원 id 찾기
    public String findUserIdByUserPhone(String userPhoneNumber);

    //    회원 이메일로 회원 id 찾기
    public String findUserIdByUserEmail(String userEmail);

    //    회원 전화번호로 회원 이메일 찾기
    public Optional<String> findUserEmailByPhone(String userPhoneNumber);

    //    이메일로 유저 찾기(MailSend용)
    public Optional<User> findUser(String userEmail);

    //    메일 보내기
    public void sendMail(MailDTO mail);

    //    회원 비밀번호 업데이트
    public void updatePassword(String userEmail, String password, PasswordEncoder passwordEncoder);

    default Address toAddress(AddressDTO addressDTO){
        return Address.builder()
                .firstAddress(addressDTO.getFirstAddress())
                .addressDetail(addressDTO.getAddressDetail())
                .zipcode(addressDTO.getZipcode())
                .build();
    }

    default AddressDTO toAddressDTO(Address address){
        return AddressDTO.builder()
                .addressDetail(address.getAddressDetail())
                .firstAddress(address.getFirstAddress())
                .zipcode(address.getZipcode())
                .build();
    }

//    회원 탈퇴
    public void updateUserStatusByUserId(Long userId);
}
