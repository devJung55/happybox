package com.app.happybox.service.cs;

import com.app.happybox.domain.InquiryAnswerDTO;
import com.app.happybox.domain.InquiryDTO;
import com.app.happybox.entity.customer.Inquiry;
import com.app.happybox.entity.customer.InquiryAnswer;
import com.app.happybox.repository.inquiry.InquiryAnswerRepository;
import com.app.happybox.repository.inquiry.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Qualifier("inquiry")
public class InquiryServiceImpl implements InquiryService {
    private final InquiryRepository inquiryRepository;
    private final InquiryAnswerRepository inquiryAnswerRepository;

//    문의 목록
    @Override
    public Page<InquiryDTO> getInquiryListById(Pageable pageable, Long id) {
        Page<Inquiry> inquiries = inquiryRepository.findInquiryListByMemberIdWithPaging_QueryDSL(pageable, id);
        List<InquiryDTO> inquiryLists = inquiries.get().map(this::toInquiryDTO).collect(Collectors.toList());
        return new PageImpl<>(inquiryLists, pageable, inquiries.getTotalElements());
    }

//    문의 답변 목록
    @Override
    public Page<InquiryAnswerDTO> getInquiryAnswerListById(Pageable pageable, Long id) {
        Page<InquiryAnswer> inquiryAnswers = inquiryAnswerRepository.findAnswersByInquiryId_QueryDSL(pageable, id);
        List<InquiryAnswerDTO> inquiryAnswerLists = inquiryAnswers.get().map(this::toInquiryAnswerDTO).collect(Collectors.toList());
        return new PageImpl<>(inquiryAnswerLists, pageable, inquiryAnswers.getTotalElements());
    }
}
