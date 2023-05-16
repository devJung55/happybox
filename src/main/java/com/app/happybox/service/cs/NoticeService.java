package com.app.happybox.service.cs;

import com.app.happybox.domain.NoticeDTO;
import com.app.happybox.domain.NoticeFileDTO;
import com.app.happybox.entity.customer.Notice;
import com.app.happybox.entity.customer.NoticeSearch;
import com.app.happybox.entity.file.NoticeFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

public interface NoticeService {
    //    공지사항 목록 및 검색
    public Page<NoticeDTO> getNoticeList(Pageable pageable, NoticeSearch noticeSearch);

    //    상세보기
    public NoticeDTO getNoticeWithId(Long id);

    //    관리자용 공지사항 목록
    public Page<NoticeDTO> getAdminNoticeList(Pageable pageable);

    //    삭제
    public void deleteById(Long id);

    //    공지사항 등록
    public void noticeWrite(NoticeDTO noticeDTO);

    //    공지사항 DTO로 바꾸기
    default NoticeDTO toNoticeDTO(Notice notice){
        return NoticeDTO.builder().id(notice.getId())
                .noticeTitle(notice.getNoticeTitle())
                .noticeContent(notice.getNoticeContent())
                .createdDate(notice.getCreatedDate())
                .updatedDate(notice.getUpdatedDate())
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

    //    공지사항 DTO Entity로 바꾸기
    default Notice toNoticeEntity(NoticeDTO noticeDTO) {
        return Notice.builder().id(noticeDTO.getId())
                .noticeTitle(noticeDTO.getNoticeTitle())
                .noticeContent(noticeDTO.getNoticeContent())
                .noticeFiles(noticeDTO.getNoticeFileDTOS().stream()
                        .map(this::toNoticeFileEntity)
                        .collect(Collectors.toList())
                )
                .build();
    }

    //    공지사항 파일 DTO Entity로 바꾸기
    default NoticeFile toNoticeFileEntity(NoticeFileDTO noticeFileDTO) {
        return NoticeFile.builder()
                .id(noticeFileDTO.getId())
                .filePath(noticeFileDTO.getFilePath())
                .fileUuid(noticeFileDTO.getFileUuid())
                .fileOrgName(noticeFileDTO.getFileOrgName())
                .build();
    }

}
