package com.app.happybox.service.user;

import com.app.happybox.entity.file.UserFile;
import com.app.happybox.repository.user.UserFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Qualifier("userFile") @Primary
public class UserFileServiceImpl implements UserFileService {
    private final UserFileRepository userFileRepository;

    @Override
    public UserFile getDetail(Long userId) {
        return userFileRepository.findById_QueryDSL(userId);
    }
}
