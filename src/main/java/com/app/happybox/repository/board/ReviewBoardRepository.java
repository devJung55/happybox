package com.app.happybox.repository.board;

import com.app.happybox.entity.board.ReviewBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewBoardRepository extends JpaRepository<ReviewBoard, Long>, ReviewBoardQueryDsl {
}
