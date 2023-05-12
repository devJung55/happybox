package com.app.happybox.service.user;

import com.app.happybox.entity.user.Welfare;
import com.app.happybox.repository.user.WelfareRepository;
import com.app.happybox.type.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Qualifier("welfare") @Primary
public class WelfareServiceImpl implements WelfareService {
    private final WelfareRepository welfareRepository;

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
    public Page<Welfare> getList(Pageable pageable) {
        Page<Welfare> welfares = welfareRepository.findAllWithPaging_QueryDSL(pageable);
        return welfares;
    }

    @Override
    public Optional<Welfare> getDetail(Long welfareId) {
        return welfareRepository.findWelfareById_QueryDSL(welfareId);
    }
}
