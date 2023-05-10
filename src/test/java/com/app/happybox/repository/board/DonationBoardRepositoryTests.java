package com.app.happybox.repository.board;

import com.app.happybox.entity.board.Board;
import com.app.happybox.entity.board.DonationBoard;
import com.app.happybox.entity.file.BoardFile;
import com.app.happybox.entity.type.DonateType;
import com.app.happybox.entity.type.FileRepresent;
import com.app.happybox.entity.user.Welfare;
import com.app.happybox.repository.user.WelfareRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Transactional
@Rollback(false)
@Slf4j
public class DonationBoardRepositoryTests {
    @Autowired
    private DonationBoardRepository donationBoardRepository;
    @Autowired
    private WelfareRepository welfareRepository;
    @Autowired
    private BoardFileRepository boardFileRepository;

    @Test
    public void saveTest() {
        for (int i=0; i<10; i++){
            DonationBoard donationBoard = new DonationBoard("기부 제목" + (i + 1), "기부내용" + (i + 1), DonateType.FOOD, "석계무료급식소");
            BoardFile boardFile = new BoardFile("2023/05/05", UUID.randomUUID().toString(), "기부" + (i + 1) + ".png", FileRepresent.REPRESENT);
            boardFile.setBoard(donationBoard);
            boardFileRepository.save(boardFile);
            welfareRepository.findById(2L).ifPresent(donationBoard::setWelfare);
            donationBoardRepository.save(donationBoard);
            Integer welfarePoint = welfareRepository.findById(2L).get().getWelfarePointTotal();
            welfareRepository.findById(2L).get().setWelfarePointTotal(welfarePoint + 100);
        }
    }

    @Test
    public void findAllWithPagingTest(){
        donationBoardRepository.findAllWithPaging(PageRequest.of(0, 9)
        ).stream().map(DonationBoard::toString).forEach(log::info);
    }

    @Test
    public void findTop3OrderByDate_QueryDSL() {
        // given
        List<DonationBoard> donationBoardList = donationBoardRepository.findTop3OrderByDate_QueryDSL();

        // when

        // then
        donationBoardList.stream().map(DonationBoard::toString).forEach(log::info);
    }

    @Test
    public void findAllWithPaging_QueryDSL_Test() {
        donationBoardRepository.findAllWithPaging(PageRequest.of(0, 5))
                .stream().map(Board::getBoardContent).forEach(log::info);
    }
}
