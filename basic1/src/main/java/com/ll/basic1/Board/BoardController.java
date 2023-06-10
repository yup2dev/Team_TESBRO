package com.ll.basic1.Board;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/event")
    public String getEventList(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Board> boardPage = boardService.getListByBoardCategory("event", page);
        model.addAttribute("paging", boardPage);
        return "board_list";
    }

    @GetMapping("/qna")
    public String getQnaList(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Board> boardPage = boardService.getListByBoardCategory("qna", page);
        model.addAttribute("paging", boardPage);
        return "board_list";
    }

    @GetMapping("/notice")
    public String getNoticeList(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Board> boardPage = boardService.getListByBoardCategory("notice", page);
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

        boardService.create(boardCategory, managerName, subject, content);

        if (boardCategory.equals("event")) {
            return "redirect:/board/event";
        } else if (boardCategory.equals("qna")) {
            return "redirect:/board/qna";
        } else if (boardCategory.equals("notice")) {
            return "redirect:/board/notice";
        }
        return "redirect:/board";
    }


    @GetMapping("/detail/{id}")
    public String getBoardDetail(@PathVariable("id") Integer id, Model model) {
        Board board = boardService.getBoard(id);
        model.addAttribute("board", board);
        model.addAttribute("answerForm", new AnswerForm());
        return "board_detail";
    }

    @PostMapping("/detail/{id}")
    public String addAnswerToBoard(@PathVariable("id") Integer id, @ModelAttribute("answerForm") AnswerForm answerForm) {
        String content = answerForm.getContent();

        boardService.addAnswerToBoard(id, content);

        // 게시물이 속한 카테고리에 따라 리다이렉트 경로를 생성
        Board board = boardService.getBoard(id);
        String boardCategory = board.getBoardCategory();

        if (boardCategory.equals("event")) {
            return "redirect:/board/event";
        } else if (boardCategory.equals("qna")) {
            return "redirect:/board/qna";
        } else if (boardCategory.equals("notice")) {
            return "redirect:/board/notice";
        }

        // 위의 조건문에 해당하지 않는 경우 기본적으로 상세 페이지로 리다이렉트
        return "redirect:/board/detail/" + id;
    }

}
