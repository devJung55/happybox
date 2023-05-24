package com.app.happybox.service.user;

import com.app.happybox.domain.user.UserFileDTO;
import com.app.happybox.entity.file.UserFile;
import com.app.happybox.repository.user.UserFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Qualifier("userFile") @Primary
public class UserFileServiceImpl implements UserFileService {
    private final UserFileRepository userFileRepository;

    @Override
    public UserFileDTO getDetail(Long userId) {

        UserFileDTO userFileDTO = null;
        Optional<UserFile> userFile = userFileRepository.findById_QueryDSL(userId);

        if(userFile.isPresent()) {
            userFileDTO = userFileToDTO(userFile.get());
        } else userFileDTO = new UserFileDTO();

        return userFileDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerProfile(Long userId, UserFile userFile) {
        userFileRepository.deleteByUserId(userId);
        userFileRepository.save(userFile);
    }

    @Override
    public List<UserFile> getList() {
        return userFileRepository.findAll();
    }
}
