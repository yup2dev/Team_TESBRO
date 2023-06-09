package com.ll.basic1.Notice;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@Controller
public class BoardController {
    private final BoardRepository boardRepository;
    private final BoardService boardService;

    @GetMapping("/notice/list")
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<Board> paging = this.boardService.getList(page);
        model.addAttribute("paging", paging);
        return "board_list";
    }git
    @GetMapping(value = "/notice/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Board board = this.boardService.getNotice(id);
        model.addAttribute("notice", board);
        return "board_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String noticeCreate(BoardForm boardForm) {
        return "board_form";
    }

    @PostMapping("/notice/create")
    public String noticeCreate(@RequestParam String managerName, @RequestParam String subject, @RequestParam String content) {
        this.boardService.create(managerName, subject, content);
        return "redirect:/board/list";
    }

    // 여기까지가 공지사항


}
