package com.app.happybox.service.subscript;

import com.app.happybox.domain.SubscriptionLikeDTO;
import com.app.happybox.entity.file.BoardFile;
import com.app.happybox.entity.file.BoardFileDTO;
import com.app.happybox.entity.subscript.SubscriptionLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubscriptionLikeService {
//    마이페이지 복지관 찜 목록
    public Page<SubscriptionLikeDTO> getListSubscriptionBookmarkByMemberId(Pageable pageable, Long memberId);

    default SubscriptionLikeDTO subscriptionLikeToDTO(SubscriptionLike subscriptionLike) {
        return SubscriptionLikeDTO.builder()
                .id(subscriptionLike.getId())
                .welfareName(subscriptionLike.getSubscription().getWelfare().getWelfareName())
                .subscriptionPrice(subscriptionLike.getSubscription().getSubscriptionPrice())
                .build();
    }

    default BoardFileDTO boardFileToDTO(BoardFile boardFile) {
        return BoardFileDTO.builder()
                .id(boardFile.getId())
                .fileUuid(boardFile.getFileUuid())
                .filePath(boardFile.getFilePath())
                .fileOrgName(boardFile.getFileOrgName())
                .fileRepresent(boardFile.getFileRepresent())
                .build();
    }
}
