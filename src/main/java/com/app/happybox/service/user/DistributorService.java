package com.app.happybox.service.user;

import com.app.happybox.domain.user.DistributorDTO;
import com.app.happybox.domain.user.WelfareDTO;
import com.app.happybox.entity.user.Distributor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public interface DistributorService {
//    회원정보수정
    public void updateDistributorInfoById(Distributor distributor);

//    회원탈퇴
    public void updateUserStatusById(Long distributorId);

//    관리자 유통회원 목록
    public Page<Distributor> getList(Pageable pageable);

//    관리자 회원조회
    public DistributorDTO getDetail(Long distributorId);

    //    회원가입
    public void join(DistributorDTO distributorDTO, PasswordEncoder passwordEncoder);

    //    유통업체 명 중복검사
    public boolean existsByDistributorName(String distributorName);

    default DistributorDTO toDistributorDTO(Distributor distributor) {
        return DistributorDTO.builder()
                .id(distributor.getId())
                .address(distributor.getAddress())
                .distributorName(distributor.getDistributorName())
                .userEmail(distributor.getUserEmail())
                .userId(distributor.getUserId())
                .userPhoneNumber(distributor.getUserPhoneNumber())
                .userRole(distributor.getUserRole())
                .userStatus(distributor.getUserStatus())
                .createdDate(distributor.getCreatedDate())
                .build();
    }

    default Distributor toDistributorEntity(DistributorDTO distributorDTO){
        return Distributor.builder()
                .address(distributorDTO.getAddress())
                .distributorName(distributorDTO.getDistributorName())
                .userEmail(distributorDTO.getUserEmail())
                .userPassword(distributorDTO.getUserPassword())
                .userId(distributorDTO.getUserId())
                .userPhoneNumber(distributorDTO.getUserPhoneNumber())
                .userRole(distributorDTO.getUserRole())
                .userStatus(distributorDTO.getUserStatus())
                .id(distributorDTO.getId())
                .build();
    }

}
