package com.app.happybox.service.user;

import com.app.happybox.domain.user.SubscriptionWelFareDTO;
import com.app.happybox.domain.user.WelfareDTO;
import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.Welfare;
import com.app.happybox.exception.UserNotFoundException;
import com.app.happybox.repository.subscript.SubscriptionRepository;
import com.app.happybox.repository.user.WelfareRepository;
import com.app.happybox.type.Role;
import com.app.happybox.type.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Qualifier("welfare") @Primary
public class WelfareServiceImpl implements WelfareService {
    private final WelfareRepository welfareRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PasswordEncoder passwordEncoder;

//    회원정보 수정
    @Override @Transactional
    public void updateWelfareInfoById(WelfareDTO welfareDTO) {
        log.error(welfareDTO.toString());
        Welfare welfare = welfareRepository.findById(welfareDTO.getId()).orElseThrow(UserNotFoundException::new);
        welfare.setUserPassword(passwordEncoder.encode(welfareDTO.getUserPassword()));
        welfare.setUserPhoneNumber(welfareDTO.getUserPhoneNumber());
        welfare.setAddress(welfareDTO.getAddress());
        welfare.setUserEmail(welfareDTO.getUserEmail());
        welfare.setWelfareName(welfareDTO.getWelfareName());
    }

    @Override
    public void updateUserStatusById(Long welfareId) {
        Welfare welfare = welfareRepository.findById(welfareId).get();
        welfare.setUserStatus(UserStatus.UNREGISTERED);
    }

    @Override
    public Page<WelfareDTO> getList(Pageable pageable) {
        Page<Welfare> welfares = welfareRepository.findAllWithPaging_QueryDSL(pageable);
        List<WelfareDTO> welfareDTOList = welfares.get().map(this::toWelfareDTO).collect(Collectors.toList());
        return new PageImpl<>(welfareDTOList, pageable, welfares.getTotalElements());
    }

    @Override
    public Slice<WelfareDTO> getListBySearch(Pageable pageable, String welfareName) {
        Slice<Welfare> welfareSlice = welfareRepository.findByWelfareName_QueryDSL(pageable, welfareName);
        log.info(welfareSlice.getContent().toString());
        List<WelfareDTO> welfareDTOS = welfareSlice.get().map(this::toWelfareDTO).collect(Collectors.toList());

        return new SliceImpl<>(welfareDTOS, pageable, welfareSlice.hasNext());
    }

    @Override
    public WelfareDTO getDetail(Long welfareId) {
//        Welfare welfare = welfareRepository.findById(welfareId).orElseThrow(UserNotFoundException::new);
        WelfareDTO welfareDTO = new WelfareDTO();
        Welfare welfare = welfareRepository.findById(welfareId).orElse(null);
        if(welfare != null) {
            welfareDTO = toWelfareDTO(welfare);
        }
        return welfareDTO;
    }

//    복지관 회원가입
    @Override
    @Transactional
    public void join(WelfareDTO welfareDTO, SubscriptionWelFareDTO subscriptionWelFareDTO, PasswordEncoder passwordEncoder) {
        welfareDTO.setUserPassword(passwordEncoder.encode(welfareDTO.getUserPassword()));
        welfareDTO.setUserRole(Role.WELFARE);
        welfareDTO.setUserStatus(UserStatus.REGISTERED);
        welfareDTO.setWelfarePointTotal(1000);
        Welfare welfare = toWelfareEntity(welfareDTO);
        welfareRepository.save(welfare);
        Welfare result = welfareRepository.findByUserId(welfare.getUserId()).get();
        Subscription subscription = toSubscriptionEntity(subscriptionWelFareDTO);
        subscription.setWelfare(result);
        subscriptionRepository.save(subscription);

    }

    @Override
    public boolean existsByUserId(String userId) {
        return false;
    }

    @Override
    public boolean existsByUserPhoneNumber(String userPhoneNumber) {
        return false;
    }

    @Override
    public boolean existsByUserEmail(String userEmail) {
        return false;
    }

    @Override
    public Optional<Welfare> findByUserId(String userId) {
        return Optional.empty();
    }
}
