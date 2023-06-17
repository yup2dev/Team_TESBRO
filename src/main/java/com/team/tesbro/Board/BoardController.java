package com.team.tesbro.Board;

import com.team.tesbro.User.SiteUser;
import com.team.tesbro.User.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;

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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/event/create")
    public String getCreateEventForm(Model model, BoardForm boardForm) {
        model.addAttribute("boardCategory", "event");
        return "board_form";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/qna/create")
    public String getCreateQnaForm(Model model, BoardForm boardForm) {
        model.addAttribute("boardCategory", "qna");
        return "board_form";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/notice/create")
    public String getCreateNoticeForm(Model model, BoardForm boardForm) {
        model.addAttribute("boardCategory", "notice");
        return "board_form";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{boardCategory}/create")
    public String createBoard(@PathVariable("boardCategory") String boardCategory,
                              @Valid BoardForm boardForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "board_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        boardService.create(boardForm.getBoardCategory(), boardForm.getSubject(), boardForm.getContent(), siteUser);
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

    @PreAuthorize("isAuthenticated()")
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String getModifyForm(@PathVariable("id") Integer id, Model model) {
        Board board = boardService.getBoard(id);
        BoardForm boardForm = new BoardForm();
        boardForm.setSubject(board.getSubject());
        boardForm.setContent(board.getContent());

        model.addAttribute("boardForm", boardForm);
        model.addAttribute("boardId", id);

        return "board_form";
    }

    @PostMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String modifyBoard(@PathVariable("id") Integer id, @ModelAttribute("boardForm") BoardForm boardForm) {
        String subject = boardForm.getSubject();
        String content = boardForm.getContent();

        boardService.modifyBoard(id, subject, content);

        return "redirect:/board/detail/" + id;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/delete/{id}")
    public String deleteBoard(@PathVariable("id") Integer id) {
        boardService.deleteBoard(id);
        return "redirect:/board/notice";
    }
}
