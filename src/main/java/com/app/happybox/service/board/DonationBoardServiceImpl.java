package com.app.happybox.service.board;

import com.app.happybox.entity.board.DonationBoard;
import com.app.happybox.entity.board.DonationBoardDTO;
import com.app.happybox.exception.BoardNotFoundException;
import com.app.happybox.repository.board.DonationBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Qualifier("donationBoard")
public class DonationBoardServiceImpl implements DonationBoardService {
    private final DonationBoardRepository donationBoardRepository;

    @Override
    public Page<DonationBoardDTO> getList(Pageable pageable) {
        Page<DonationBoard> donationBoards = donationBoardRepository.findAllByIdDescWithPaging_QueryDSL(PageRequest.of(0, 10));
        List<DonationBoardDTO> collect = donationBoards.get().map(board -> donationBoardToDTO(board)).collect(Collectors.toList());
        return new PageImpl<>(collect, pageable, donationBoards.getTotalElements());
    }

    @Override
    public DonationBoardDTO getDetail(Long id) {
        DonationBoard donationBoard = donationBoardRepository.findById(id).orElseThrow(() -> {
            throw new BoardNotFoundException();
        });
        return donationBoardToDTO(donationBoard);
    }

    @Override
    public List<DonationBoardDTO> findTop3OrderByDate_QueryDSL() {
        return donationBoardRepository.findTop3OrderByDate_QueryDSL().stream()
                .map(this::donationBoardToDTO).collect(Collectors.toList());
    }

    @Override
    public Page<DonationBoard> findAllWithPaging_QueryDSL(Pageable pageable) {
        return null;
    }
}
