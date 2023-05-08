package com.app.happybox.repository.board;

import com.app.happybox.entity.file.BoardFile;

import java.util.List;

public interface ReviewBoardFileQueryDsl {
//    파일 가져오기
    public List<BoardFile> findByReviewBoardId(Long id);
}
