package com.ll.basic1.Board;

import com.ll.basic1.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public void create(String boardCategory, String managerName, String subject, String content) {
        Board board = new Board();
        board.setBoardCategory(boardCategory);
        board.setManagerName(managerName);
        board.setSubject(subject);
        board.setContent(content);
        board.setCreateDate(LocalDateTime.now());
        this.boardRepository.save(board);
    }

    public void createEvent(String managerName, String subject, String content) {
        create("event", managerName, subject, content);
    }

    public void createQna(String managerName, String subject, String content) {
        create("qna", managerName, subject, content);
    }

    public void createNotice(String managerName, String subject, String content) {
        create("notice", managerName, subject, content);
    }

    public Board getBoard(Integer id) {
        Optional<Board> board = this.boardRepository.findById(id);
        if (board.isPresent()) {
            return board.get();
        } else {
            throw new DataNotFoundException("Board not found");
        }
    }

    public Page<Board> getListByBoardCategory(String boardCategory, int page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, 10, sort);
        return boardRepository.findByBoardCategory(boardCategory, pageable);
    }

    public void addAnswerToBoard(Integer boardId, String content) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new DataNotFoundException("Board not found"));

        Answer answer = new Answer();
        answer.setContent(content);

        board.addAnswer(answer);

        boardRepository.save(board);
    }

    public Page<Board> searchByKeyword(String keyword) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(0, 10, sort);
        return boardRepository.findBySubjectContainingIgnoreCase(keyword, pageable);
    }
}