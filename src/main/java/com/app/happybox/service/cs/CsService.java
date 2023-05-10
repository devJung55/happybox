package com.app.happybox.service.cs;

import com.app.happybox.domain.NoticeDTO;
import com.app.happybox.domain.NoticeFileDTO;
import com.app.happybox.entity.customer.Notice;
import com.app.happybox.entity.file.NoticeFile;

import java.util.List;
import java.util.stream.Collectors;

public interface CsService {
    //    상세보기
    public NoticeDTO getNoticeWithId(Long id);

    //    공지사항 DTO로 바꾸기
    default NoticeDTO toNoticeDTO(Notice notice){
        return NoticeDTO.builder().id(notice.getId())
                .noticeTitle(notice.getNoticeTitle())
                .noticeContent(notice.getNoticeContent())
//                변환된 공지사항 파일 DTO 타입 집어넣어주기
                .noticeFileDTOS(toNoticeFileDTO(notice.getNoticeFiles()))
                .build();
    }

//    공지사항 파일 DTO 타입으로 바꾸기
    default List<NoticeFileDTO> toNoticeFileDTO(List<NoticeFile> noticeFiles){
        return noticeFiles.stream()
                .map(noticeFile -> NoticeFileDTO.builder()
                        .id(noticeFile.getId())
                        .filePath(noticeFile.getFilePath())
                        .fileUuid(noticeFile.getFileUuid())
                        .fileOrgName(noticeFile.getFileOrgName())
                        .fileRepresent(noticeFile.getFileRepresent())
                        .build())
                .collect(Collectors.toList());
    }
}
