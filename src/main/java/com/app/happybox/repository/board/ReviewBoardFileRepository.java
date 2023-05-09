package com.app.happybox.repository.board;

import com.app.happybox.entity.file.BoardFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewBoardFileRepository extends JpaRepository<BoardFile, Long>, ReviewBoardFileQueryDsl {
}
