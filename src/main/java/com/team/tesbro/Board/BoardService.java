package com.team.tesbro.Board;

import com.team.tesbro.DataNotFoundException;
import com.team.tesbro.User.SiteUser;
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

    public Board create(String boardCategory, String subject, String content, SiteUser user) {
        Board board = new Board();
        board.setBoardCategory(boardCategory);
        board.setSubject(subject);
        board.setContent(content);
        board.setAuthor(user);
        board.setCreateDate(LocalDateTime.now());
        return boardRepository.save(board);
    }

    public Board createEvent(String subject, String content, SiteUser user) {
        return create("event", subject, content, user);
    }

    public Board createQna(String subject, String content, SiteUser user) {
        return create("qna", subject, content, user);
    }

    public Board createNotice(String subject, String content, SiteUser user) {
        return create("notice", subject, content, user);
    }

    public Board getBoard(Integer id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Board not found with id: " + id));
    }

    public Page<Board> getBoardListByCategory(String boardCategory, String order, int page) {
        Sort sort;
        if (order.equals("views")) {
            sort = Sort.by(Sort.Direction.DESC, "views");
        } else if (order.equals("outdated")) {
            sort = Sort.by(Sort.Direction.ASC, "id");
        } else if (order.equals("voter")) {
            sort = Sort.by(Sort.Direction.DESC, "voter");
        } else if (order.equals("answers")) {
            sort = Sort.by(Sort.Direction.DESC, "answers");
        } else {
            sort = Sort.by(Sort.Direction.DESC, "id");
        }

        Pageable pageable = PageRequest.of(page, 15, sort);
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
        Pageable pageable = PageRequest.of(0, 15, sort);
        return boardRepository.findBySubjectContainingIgnoreCase(keyword, pageable);
    }

    public void modifyBoard(Board board, String subject, String content) {
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
    public void vote(Board board, SiteUser siteUser) {
        board.getVoter().add(siteUser);
        this.boardRepository.save(board);
    }
}
