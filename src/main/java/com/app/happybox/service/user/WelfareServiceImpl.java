package com.app.happybox.service.user;

import com.app.happybox.entity.user.Welfare;
import com.app.happybox.repository.user.WelfareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Qualifier("welfare") @Primary
public class WelfareServiceImpl implements WelfareService {
    private final WelfareRepository welfareRepository;

    @Override
    public void updateWelfareInfoById(Welfare welfare) {
        welfareRepository.setWelfareInfoById_QueryDSL(welfare);
    }
}
