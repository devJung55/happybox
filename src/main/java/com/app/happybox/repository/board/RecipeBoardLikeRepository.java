package com.app.happybox.repository.board;

import com.app.happybox.entity.board.RecipeBoardLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeBoardLikeRepository extends JpaRepository<RecipeBoardLike, Long>, RecipeBoardLikeQueryDsl {
}
