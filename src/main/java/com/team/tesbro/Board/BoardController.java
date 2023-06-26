package com.team.tesbro.Board;

import com.team.tesbro.User.SiteUser;
import com.team.tesbro.User.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;


    @GetMapping("/qna")
    public String getQnaList(@RequestParam(defaultValue = "latest") String order,
                             @RequestParam(defaultValue = "0") int page, Model model) {
        Page<Board> boardPage = boardService.getBoardListByCategory("qna", order, page);
        model.addAttribute("paging", boardPage);
        model.addAttribute("boardcategory", "qna");
        return "qna_list";
    }

    @GetMapping("/notice")
    public String getNoticeList(@RequestParam(defaultValue = "latest") String order,
                                @RequestParam(defaultValue = "0") int page, Model model) {
        Page<Board> boardPage = boardService.getBoardListByCategory("notice", order, page);
        model.addAttribute("paging", boardPage);
        model.addAttribute("boardcategory", "notice");
        return "board_list";
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
    public String getModifyForm(@PathVariable("id") Integer id, Principal principal, BoardForm boardForm) {
        Board board = boardService.getBoard(id);
        if (!board.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다");
        }
        boardForm.setSubject(board.getSubject());
        boardForm.setContent(board.getContent());
        return "board_form";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modifyBoard(@Valid BoardForm boardForm, BindingResult bindingResult,
                              Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "board_form";
        }
        Board board = this.boardService.getBoard(id);
        if (!board.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.boardService.modifyBoard(board, boardForm.getSubject(), boardForm.getContent());
        return String.format("redirect:/board/%s/detail/%d", board.getBoardCategory(), id);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/delete/{id}")
    public String deleteBoard(@PathVariable("id") Integer id) {
        boardService.deleteBoard(id);
        return "redirect:/board/notice";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String boardVote(Principal principal, @PathVariable("id") Integer id) {
        Board board = this.boardService.getBoard(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.boardService.vote(board, siteUser);
        return String.format("redirect:/board/%s/detail/%d", board.getBoardCategory(), id);
    }

    @GetMapping("/address")
    public String address(){
        return "juso";
    }

    @GetMapping("/address2")
    public String address2(){
        return "jusoPopup";
    }

    @PostMapping("/address")
    public String addressPost(){
        // POST 요청 처리 로직 작성
        return "juso";
    }

    @PostMapping("/address2")
    public String address2Post(){
        return "jusoPopup";
    }

}
