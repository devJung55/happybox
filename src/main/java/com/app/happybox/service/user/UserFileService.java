package com.app.happybox.service.user;

import com.app.happybox.domain.user.UserFileDTO;
import com.app.happybox.entity.file.UserFile;

import java.util.List;
import java.util.Optional;

public interface UserFileService {
//    회원 프로필 조회
    public UserFileDTO getDetail(Long userId);

//    회원 프로필 설정
    public void registerProfile(Long userId, UserFile userFile);

//    회원 프로필 전체 조회
    public List<UserFile> getList();

    default UserFileDTO userFileToDTO(UserFile userFile) {
        return UserFileDTO.builder()
                .id(userFile.getId())
                .filePath(userFile.getFilePath())
                .fileUuid(userFile.getFileUuid())
                .fileOrgName(userFile.getFileOrgName())
                .build();
    }
}
