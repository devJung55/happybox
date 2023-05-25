package com.app.happybox.service.cs;

import com.app.happybox.domain.InquiryAnswerDTO;
import com.app.happybox.domain.InquiryDTO;
import com.app.happybox.entity.customer.Inquiry;
import com.app.happybox.entity.customer.InquiryAnswer;
import com.app.happybox.entity.file.InquiryFile;
import com.app.happybox.exception.InquiryNotFoundException;
import com.app.happybox.repository.inquiry.InquiryAnswerFileRepository;
import com.app.happybox.repository.inquiry.InquiryAnswerRepository;
import com.app.happybox.repository.inquiry.InquiryFileRepository;
import com.app.happybox.repository.inquiry.InquiryRepository;
import com.app.happybox.repository.user.MemberRepository;
import com.app.happybox.repository.user.UserRepository;
import com.app.happybox.service.user.UserService;
import com.app.happybox.type.InquiryStatus;
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
    private final InquiryAnswerFileRepository inquiryAnswerFileRepository;
    private final MemberRepository memberRepository;
    private final InquiryFileRepository inquiryFileRepository;
    private final UserRepository userRepository;

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

    @Override
    public InquiryAnswerDTO saveInquiryAnswer(Long inquiryId, InquiryAnswerDTO inquiryAnswerDTO) {
        log.info("======================================= 처음확인용");
        log.info(inquiryAnswerDTO.toString());
        Inquiry inquiry = inquiryRepository.findById(inquiryId).orElseThrow(InquiryNotFoundException::new);
        InquiryAnswer inquiryAnswer = toInquiryAnswerEntity(inquiryAnswerDTO);

        inquiryAnswer.setInquiry(inquiry);
        InquiryAnswer savedAnswer = inquiryAnswerRepository.save(inquiryAnswer);

        inquiry.setInquiryStatus(InquiryStatus.COMPLETE);

        log.info("======================================= 파일 확인 용도");
        log.info(inquiryAnswerDTO.toString());
        log.info("여기 들어왔냐???");
        inquiryAnswerDTO.getInquiryAnswerFileDTOS()
                .stream()
                .map(this::toInquiryAnswerFileEntity)
                .forEach(file -> {
                    file.setInquiryAnswer(inquiryAnswer);
                    inquiryAnswerFileRepository.save(file);
                });

        return toInquiryAnswerDTO(savedAnswer);
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
        inquiry.setUser(userRepository.findById(id).get());
        inquiry.setInquiryStatus(InquiryStatus.STANDBY);
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

    @Override
    public Page<InquiryDTO> getInquiries(Pageable pageable) {
        Page<Inquiry> inquiryPage = inquiryRepository.findInquiryListWithPaging_QueryDSL(pageable);
        List<InquiryDTO> inquiryDTOList = inquiryPage.get().map(this::toInquiryDTO).collect(Collectors.toList());

        return new PageImpl<>(inquiryDTOList, pageable, inquiryPage.getTotalElements());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteInquiries(List<Long> ids) {
        log.info("===============" + ids);
        inquiryRepository.deleteAllById(ids);
    }


    // 상세보기
    @Override
    @Transactional(rollbackFor = Exception.class)
    public InquiryDTO getInquiryDetailById(Long id) {
        Inquiry inquiry = inquiryRepository.findInquiryByInquiryId_QueryDSL(id).orElseThrow(InquiryNotFoundException::new);
        return toInquiryDTO(inquiry);
    }
}
