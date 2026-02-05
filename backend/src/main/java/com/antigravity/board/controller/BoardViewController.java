package com.antigravity.board.controller;

import com.antigravity.board.dto.BoardResponseDTO;
import com.antigravity.board.dto.CommentResponseDTO;
import com.antigravity.board.entity.BoardType;
import com.antigravity.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardViewController {

    private final BoardService boardService;

    @GetMapping("/boards")
    public String boardList(
            @RequestParam BoardType type,
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 10) Pageable pageable,
            Model model) {
        Page<BoardResponseDTO> boards = boardService.getBoards(type, keyword, pageable);
        model.addAttribute("boards", boards);
        model.addAttribute("type", type);
        model.addAttribute("keyword", keyword);
        return "board/list";
    }

    @GetMapping("/boards/{id}")
    public String boardDetail(@PathVariable Long id, Model model) {
        BoardResponseDTO board = boardService.getBoardWithViewCount(id);
        List<CommentResponseDTO> comments = boardService.getComments(id);
        model.addAttribute("board", board);
        model.addAttribute("comments", comments);
        return "board/detail";
    }

    @GetMapping("/boards/write")
    public String writeForm(@RequestParam BoardType type, Model model) {
        model.addAttribute("type", type);
        return "board/write";
    }
}
