package com.app.happybox.service.cs;

import com.app.happybox.domain.InquiryAnswerDTO;
import com.app.happybox.domain.InquiryAnswerFileDTO;
import com.app.happybox.domain.InquiryDTO;
import com.app.happybox.domain.InquiryFileDTO;
import com.app.happybox.entity.customer.Inquiry;
import com.app.happybox.entity.customer.InquiryAnswer;
import com.app.happybox.entity.file.InquiryAnswerFile;
import com.app.happybox.entity.file.InquiryFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

public interface InquiryService {
//    문의내역 목록 및 상세?
    public Page<InquiryDTO> getInquiryListById(Pageable pageable, Long id);

//    문의사항 DTO로 바꾸기
    default InquiryDTO toInquiryDTO(Inquiry inquiry) {
        return InquiryDTO.builder().id(inquiry.getId())
                .inquiryTitle(inquiry.getInquiryTitle())
                .inquiryContent(inquiry.getInquiryContent())
                .createdDate(inquiry.getCreatedDate())
                .updatedDate(inquiry.getUpdatedDate())
                .user(inquiry.getUser())
                .inquiryType(inquiry.getInquiryType())
                .inquiryStatus(inquiry.getInquiryStatus())
                .inquiryFileDTOS(
                        inquiry.getInquiryFiles().stream()
                        .map(this::toInquiryFileDTO)
                        .collect(Collectors.toList())
                )
                .build();
    }

//    문의사항 파일 DTO로 바꾸기
    default InquiryFileDTO toInquiryFileDTO(InquiryFile inquiryFile) {
        return InquiryFileDTO.builder()
                .id(inquiryFile.getId())
                .filePath(inquiryFile.getFilePath())
                .fileUuid(inquiryFile.getFileUuid())
                .fileOrgName(inquiryFile.getFileOrgName())
                .build();
    }

//    문의 답변 목록 및 상세?
    public Page<InquiryAnswerDTO> getInquiryAnswerListById(Pageable pageable, Long id);

//    문의 답변 사항 DTO로 바꾸기
    default InquiryAnswerDTO toInquiryAnswerDTO(InquiryAnswer inquiryAnswer) {
        return InquiryAnswerDTO.builder()
                .id(inquiryAnswer.getId())
                .inquiryAnswerContent(inquiryAnswer.getInquiryAnswerContent())
                .createdDate(inquiryAnswer.getCreatedDate())
                .updatedDate(inquiryAnswer.getUpdatedDate())
                .inquiry(inquiryAnswer.getInquiry())
                .inquiryAnswerFileDTOS(
                        inquiryAnswer.getInquiryAnswerFiles().stream()
                        .map(this::toInquiryAnswerFileDTO)
                        .collect(Collectors.toList())
                )
                .build();
    }

//    문의 답변 파일 DTO로 바꾸기
    default InquiryAnswerFileDTO toInquiryAnswerFileDTO(InquiryAnswerFile inquiryAnswerFile) {
        return InquiryAnswerFileDTO.builder()
                .id(inquiryAnswerFile.getId())
                .filePath(inquiryAnswerFile.getFilePath())
                .fileUuid(inquiryAnswerFile.getFileUuid())
                .fileOrgName(inquiryAnswerFile.getFileOrgName())
                .build();
    }

//    마이페이지 문의내역 목록
    public Page<InquiryDTO> getInquiryListByMemberId(Pageable pageable, Long memberId);

//    마이페이지 문의답변
    public List<InquiryAnswerDTO> getInquiryAnswerListByUserId(Long memberId);
}
