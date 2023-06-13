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

    public Board create(String boardCategory, String managerName, String subject, String content) {
        Board board = new Board();
        board.setBoardCategory(boardCategory);
        board.setManagerName(managerName);
        board.setSubject(subject);
        board.setContent(content);
        board.setCreateDate(LocalDateTime.now());
        return boardRepository.save(board);
    }

    public Board createEvent(String managerName, String subject, String content) {
        return create("event", managerName, subject, content);
    }

    public Board createQna(String managerName, String subject, String content) {
        return create("qna", managerName, subject, content);
    }

    public Board createNotice(String managerName, String subject, String content) {
        return create("notice", managerName, subject, content);
    }

    public Board getBoard(Integer id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Board not found with id: " + id));
    }

    public Page<Board> getListByBoardCategory(String boardCategory, int page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, 10, sort);
        return boardRepository.findByBoardCategory(boardCategory, pageable);
    }

    public void addAnswerToBoard(Integer boardId, String content) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new DataNotFoundException("Board not found with id: " + boardId));

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

    public void modifyBoard(Integer id, String managerName, String subject, String content) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Board not found with id: " + id));

        board.setManagerName(managerName);
        board.setSubject(subject);
        board.setContent(content);
        board.setUpdateDate(LocalDateTime.now());

        boardRepository.save(board);
    }

    public void deleteBoard(Integer id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Board not found with id: " + id));

        boardRepository.delete(board);
    }

    public void Views(Integer id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Board not found with id: " + id));

        board.setViews(board.getViews() + 1);
        boardRepository.save(board);
    }
}
