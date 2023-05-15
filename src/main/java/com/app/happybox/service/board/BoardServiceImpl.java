package com.app.happybox.service.board;

import com.app.happybox.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Qualifier("board")
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    @Override
    public void removeByBoardId(Long boardId) {
        boardRepository.deleteById(boardId);
    }
}
