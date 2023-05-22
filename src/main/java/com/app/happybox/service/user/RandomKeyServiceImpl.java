package com.app.happybox.service.user;

import com.app.happybox.entity.user.User;
import com.app.happybox.entity.user.UserRandomKey;
import com.app.happybox.repository.user.RandomKeyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Qualifier("randomKey")
@Slf4j
public class RandomKeyServiceImpl implements RandomKeyService {
    private final RandomKeyRepository randomKeyRepository;

    //    랜덤키 생성
    @Override
    public UserRandomKey saveRandomKey(User user) {
        UserRandomKey randomKey = new UserRandomKey(user);
        randomKeyRepository.save(randomKey);

        return randomKey;
    }

    //    가장 최근 랜덤키 불러오기
    @Override
    public UserRandomKey getLatestRandomKey(Long id) {
        return randomKeyRepository.getLatestRandomKey_QueryDSL(id);
    }

    //    랜덤키 업데이트 하기
    @Override
    public void updateRandomKey(Long id) {
        UserRandomKey ranKey = randomKeyRepository.getLatestRandomKey_QueryDSL(id);
        ranKey.setRanKey(ranKey.getTmpPassword());
        randomKeyRepository.save(ranKey);
    }
}
