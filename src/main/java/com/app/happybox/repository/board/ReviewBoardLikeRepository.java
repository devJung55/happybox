package com.app.happybox.repository.board;

import com.app.happybox.entity.board.ReviewBoardLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewBoardLikeRepository extends JpaRepository<ReviewBoardLike, Long> {
}
