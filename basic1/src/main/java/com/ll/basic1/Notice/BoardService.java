package com.ll.basic1.Notice;

import com.ll.basic1.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public void create(String managerName, String subject, String content) {
        Board board = new Board();
        board.setManagerName(managerName);
        board.setSubject(subject);
        board.setContent(content);
        board.setCreateDate(LocalDateTime.now());
        this.boardRepository.save(board);
    }

    public Board getNotice(Integer id) {
        Optional<Board> notice = this.boardRepository.findById(id);
        if(notice.isPresent()) {
            return notice.get();
        } else {
            throw new DataNotFoundException("article not found");
        }
    }
    public Page<Board> getList(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return this.boardRepository.findAll(pageable);
    }

}