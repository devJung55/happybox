package com.app.happybox.service.user;

import com.app.happybox.domain.user.SubscriptionWelFareDTO;
import com.app.happybox.domain.user.WelfareDTO;
import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.entity.user.Welfare;
import com.app.happybox.exception.UserNotFoundException;
import com.app.happybox.repository.subscript.SubscriptionRepository;
import com.app.happybox.repository.user.WelfareRepository;
import com.app.happybox.type.Role;
import com.app.happybox.type.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Qualifier("welfare") @Primary
public class WelfareServiceImpl implements WelfareService {
    private final WelfareRepository welfareRepository;
    private final SubscriptionRepository subscriptionRepository;


    @Override
    public void updateWelfareInfoById(Welfare welfare) {
        welfareRepository.setWelfareInfoById_QueryDSL(welfare);
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
    public WelfareDTO getDetail(Long welfareId) {
        Welfare welfare = welfareRepository.findById(welfareId).orElseThrow(UserNotFoundException::new);
        return toWelfareDTO(welfare);
    }

//    복지관 회원가입
    @Override
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
