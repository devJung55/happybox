package com.app.happybox.service.user;

import com.app.happybox.domain.user.DistributorDTO;
import com.app.happybox.domain.user.MemberDTO;
import com.app.happybox.domain.user.WelfareDTO;
import com.app.happybox.entity.user.Distributor;
import com.app.happybox.entity.user.Member;
import com.app.happybox.exception.UserNotFoundException;
import com.app.happybox.repository.user.DistributorRepository;
import com.app.happybox.type.Role;
import com.app.happybox.type.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Qualifier("distributor") @Primary
public class DistributorServiceImpl implements DistributorService {
    private final DistributorRepository distributorRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void updateUserStatusById(Long distributorId) {
        Distributor distributor = distributorRepository.findById(distributorId).get();
        distributor.setUserStatus(UserStatus.UNREGISTERED);
    }

    @Override
    public Page<Distributor> getList(Pageable pageable) {
        return distributorRepository.findAllWithPaging_QueryDSL(pageable);
    }

    @Override
    public Slice<DistributorDTO> searchByName(Pageable pageable, String searchName) {

        return null;
    }

    @Override
    public DistributorDTO getDetail(Long distributorId) {
        Distributor distributor = distributorRepository.findDistributorById_QueryDSL(distributorId).orElseThrow(UserNotFoundException::new);
        return toDistributorDTO(distributor);
    }

//    유통 회원가입
    @Override
    public void join(DistributorDTO distributorDTO, PasswordEncoder passwordEncoder) {

        distributorDTO.setUserPassword(passwordEncoder.encode(distributorDTO.getUserPassword()));
        distributorDTO.setUserRole(Role.DISTRIBUTOR);
        distributorDTO.setUserStatus(UserStatus.REGISTERED);
        distributorRepository.save(toDistributorEntity(distributorDTO));
    }

//    유통이름 중복체크
    @Override
    public boolean existsByDistributorName(String distributorName) {
        return distributorRepository.existsByDistributorName(distributorName);
    }

    @Override @Transactional
    public void updateDistributorInfoById(DistributorDTO distributorDTO) {
        Distributor distributor = distributorRepository.findById(distributorDTO.getId()).orElseThrow(UserNotFoundException::new);

        if(distributorDTO.getUserPassword() != null && distributorDTO.getUserPassword() == " ,") {
            distributor.setUserPassword(passwordEncoder.encode(distributorDTO.getUserPassword().split(",")[0]));
        }

        distributor.setDistributorName(distributorDTO.getDistributorName());
        distributor.setAddress(distributorDTO.getAddress());
        distributor.setUserEmail(distributorDTO.getUserEmail());
        distributor.setUserPhoneNumber(distributorDTO.getUserPhoneNumber());
    }
}
