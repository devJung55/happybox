package com.app.happybox.repository.board;

import com.app.happybox.entity.board.DonationBoard;
import com.app.happybox.entity.file.BoardFile;
import com.app.happybox.entity.user.Welfare;
import com.app.happybox.repository.user.WelfareRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
@Rollback(false)
@Slf4j
public class DonationBoardRepositoryTests {
    @Autowired
    private DonationBoardRepository donationBoardRepository;
    @Autowired
    private WelfareRepository welfareRepository;

    @Test
    public void saveTest() {
        DonationBoard donationBoard = new DonationBoard("기부 제목1", "기부내용1");
        welfareRepository.findById(2L).ifPresent(donationBoard::setWelfare);
        donationBoardRepository.save(donationBoard);
    }

    @Test
    public void findTop3OrderByDate_QueryDSL() {
        // given
        List<DonationBoard> donationBoardList = donationBoardRepository.findTop3OrderByDate_QueryDSL();

        // when

        // then
        donationBoardList.stream().map(DonationBoard::toString).forEach(log::info);
    }
}
