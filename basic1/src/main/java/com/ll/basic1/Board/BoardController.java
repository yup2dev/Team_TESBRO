package com.ll.basic1.Board;

import com.ll.basic1.DataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final BoardRepository boardRepository;

    public BoardController(BoardService boardService, BoardRepository boardRepository) {
        this.boardService = boardService;
        this.boardRepository = boardRepository;
    }

    @GetMapping("/event")
    public String getEventList(@RequestParam(defaultValue = "latest") String order,
                               @RequestParam(defaultValue = "0") int page, Model model) {
        Page<Board> boardPage = boardService.getBoardListByCategory("event", order, page);
        model.addAttribute("paging", boardPage);
        return "event_list";
    }

    @GetMapping("/qna")
    public String getQnaList(@RequestParam(defaultValue = "latest") String order,
                             @RequestParam(defaultValue = "0") int page, Model model) {
        Page<Board> boardPage = boardService.getBoardListByCategory("qna", order, page);
        model.addAttribute("paging", boardPage);
        return "board_list";
    }

    @GetMapping("/notice")
    public String getNoticeList(@RequestParam(defaultValue = "latest") String order,
                                @RequestParam(defaultValue = "0") int page, Model model) {
        Page<Board> boardPage = boardService.getBoardListByCategory("notice", order, page);
        model.addAttribute("paging", boardPage);
        return "board_list";
    }


    @GetMapping("/event/create")
    public String getCreateEventForm(Model model) {
        model.addAttribute("boardForm", new BoardForm());
        model.addAttribute("boardCategory", "event");
        return "board_form";
    }

    @GetMapping("/qna/create")
    public String getCreateQnaForm(Model model) {
        model.addAttribute("boardForm", new BoardForm());
        model.addAttribute("boardCategory", "qna");
        return "board_form";
    }

    @GetMapping("/notice/create")
    public String getCreateNoticeForm(Model model) {
        model.addAttribute("boardForm", new BoardForm());
        model.addAttribute("boardCategory", "notice");
        return "board_form";
    }

    @PostMapping("/{boardCategory}/create")
    public String createBoard(@PathVariable("boardCategory") String boardCategory,
                              @ModelAttribute("boardForm") BoardForm boardForm) {
        String managerName = boardForm.getManagerName();
        String subject = boardForm.getSubject();
        String content = boardForm.getContent();

        Board createdBoard = boardService.create(boardCategory, managerName, subject, content);

        return "redirect:/board/" + boardCategory;
    }

    @GetMapping("/{boardCategory}/detail/{id}")
    public String getBoardDetail(@PathVariable("boardCategory") String boardCategory,
                                 @PathVariable("id") Integer id, Model model) {
        boardService.Views(id);

        Board board = boardService.getBoard(id);
        model.addAttribute("board", board);
        model.addAttribute("answerForm", new AnswerForm());

        if (boardCategory.equals("event")) {
            return "event_detail";
        } else {
            return "board_detail";
        }
    }

    @PostMapping("/{boardCategory}/detail/{id}")
    public String addAnswerToBoard(@PathVariable("boardCategory") String boardCategory, @PathVariable("id") Integer id, @ModelAttribute("answerForm") AnswerForm answerForm) {
        String content = answerForm.getContent();
        boardService.addAnswerToBoard(id, content);
        return "redirect:/board/" + boardCategory + "/detail/" + id;
    }

    @GetMapping("/search")
    public String searchBoard(@RequestParam("keyword") String keyword, Model model) {
        Page<Board> searchResult = boardService.searchByKeyword(keyword);
        model.addAttribute("paging", searchResult);
        return "board_list";
    }

    @GetMapping("/modify/{id}")
    public String getModifyForm(@PathVariable("id") Integer id, Model model) {
        Board board = boardService.getBoard(id);
        BoardForm boardForm = new BoardForm();
        boardForm.setManagerName(board.getManagerName());
        boardForm.setSubject(board.getSubject());
        boardForm.setContent(board.getContent());

        model.addAttribute("boardForm", boardForm);
        model.addAttribute("boardId", id);

        return "board_form";
    }

    @PostMapping("/modify/{id}")
    public String modifyBoard(@PathVariable("id") Integer id, @ModelAttribute("boardForm") BoardForm boardForm) {
        String managerName = boardForm.getManagerName();
        String subject = boardForm.getSubject();
        String content = boardForm.getContent();

        boardService.modifyBoard(id, managerName, subject, content);

        return "redirect:/board/detail/" + id;
    }

    @PostMapping("/delete/{id}")
    public String deleteBoard(@PathVariable("id") Integer id) {
        boardService.deleteBoard(id);
        return "redirect:/board/notice";
    }
}
