package com.app.happybox.repository.board;

import com.app.happybox.entity.board.DonationBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationBoardRepository extends JpaRepository<DonationBoard, Long>, DonationBoardQueryDsl {
}
