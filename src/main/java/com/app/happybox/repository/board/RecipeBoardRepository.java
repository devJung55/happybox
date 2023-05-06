package com.app.happybox.repository.board;

import com.app.happybox.entity.board.RecipeBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeBoardRepository extends JpaRepository<RecipeBoard, Long>, RecipeBoardQueryDsl {
}
