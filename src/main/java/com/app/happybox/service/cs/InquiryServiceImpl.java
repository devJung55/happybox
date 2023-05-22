package com.app.happybox.service.cs;

import com.app.happybox.domain.InquiryAnswerDTO;
import com.app.happybox.domain.InquiryDTO;
import com.app.happybox.entity.customer.Inquiry;
import com.app.happybox.entity.customer.InquiryAnswer;
import com.app.happybox.entity.file.InquiryFile;
import com.app.happybox.repository.inquiry.InquiryAnswerRepository;
import com.app.happybox.repository.inquiry.InquiryFileRepository;
import com.app.happybox.repository.inquiry.InquiryRepository;
import com.app.happybox.repository.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Qualifier("inquiry")
public class InquiryServiceImpl implements InquiryService {
    private final InquiryRepository inquiryRepository;
    private final InquiryAnswerRepository inquiryAnswerRepository;
    private final MemberRepository memberRepository;
    private final InquiryFileRepository inquiryFileRepository;

    //    문의 목록
    @Override
    public Page<InquiryDTO> getInquiryListById(Pageable pageable, Long id) {
        Page<Inquiry> inquiries = inquiryRepository.findInquiryListByMemberIdWithPaging_QueryDSL(pageable, id);
        List<InquiryDTO> inquiryLists = inquiries.get().map(this::toInquiryDTO).collect(Collectors.toList());
        return new PageImpl<>(inquiryLists, pageable, inquiries.getTotalElements());
    }

    @Override
    public Page<InquiryDTO> getListByMemberId(Pageable pageable, Long memberId) {
        Page<Inquiry> inquiries = inquiryRepository.findInquiryListByMemberIdWithPaging_QueryDSL(pageable, memberId);
        List<InquiryDTO> inquiryLists = inquiries.get().map(this::mypageToInquiryDTO).collect(Collectors.toList());

        // 문의 id들
        List<Long> inquiryIds = inquiries.get().map(Inquiry::getId).collect(Collectors.toList());

        inquiryAnswerRepository.findByInquiryIds(inquiryIds)
                .forEach(answer -> {
                    inquiryLists.forEach(inquiry -> {
                        if (inquiry.getId() == answer.getInquiry().getId())
                            inquiry.setInquiryAnswerDTO(this.toInquiryAnswerDTO(answer));
                    });
                });

        return new PageImpl<>(inquiryLists, pageable, inquiries.getTotalElements());
    }

    //    문의 답변 목록
    @Override
    public Page<InquiryAnswerDTO> getInquiryAnswerListById(Pageable pageable, Long id) {
        Page<InquiryAnswer> inquiryAnswers = inquiryAnswerRepository.findAnswersByInquiryId_QueryDSL(pageable, id);
        List<InquiryAnswerDTO> inquiryAnswerLists = inquiryAnswers.get().map(this::toInquiryAnswerDTO).collect(Collectors.toList());
        return new PageImpl<>(inquiryAnswerLists, pageable, inquiryAnswers.getTotalElements());
    }

    @Override
    public List<InquiryAnswerDTO> getInquiryAnswerListByUserId(Long memberId) {
        List<InquiryAnswer> inquiryAnswers = inquiryAnswerRepository.findByUserId_QueryDSL(memberId);
        return inquiryAnswers.stream().map(this::toInquiryAnswerDTO).collect(Collectors.toList());
    }

    //    문의등록
    @Override
    @Transactional
    public void inquiryWrite(InquiryDTO inquiryDTO, Long id) {
        Inquiry inquiry = toInquiryEntity(inquiryDTO);
//        임시로 1번으로 할당, 로그인 회원가입 완료되면 세션에서 받아온 id값 전달
        inquiry.setUser(memberRepository.findById(id).get());
        inquiryRepository.save(inquiry);
        if(inquiryDTO.getInquiryFileDTOS() != null) {
            List<InquiryFile> inquiryFiles = toInquiryEntity(inquiryDTO).getInquiryFiles();
            inquiryFiles.forEach(inquiryFile -> inquiryFile.setInquiry(inquiry));
            inquiryFileRepository.saveAll(inquiryFiles);
        }
    }

    @Override
    public Long getInquiryCountByUserId(Long id) {
        return inquiryRepository.findInquiryCountByUserId_QueryDSL(id);
    }
}
