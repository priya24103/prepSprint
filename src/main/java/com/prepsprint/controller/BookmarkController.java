package com.prepsprint.controller;

import com.prepsprint.dto.BookmarkItemDto;
import com.prepsprint.entity.User;
import com.prepsprint.enums.ContentType;
import com.prepsprint.repository.UserRepository;
import com.prepsprint.service.BookmarkService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final UserRepository userRepository;

    public BookmarkController(BookmarkService bookmarkService, UserRepository userRepository) {
        this.bookmarkService = bookmarkService;
        this.userRepository = userRepository;
    }

    @GetMapping("/bookmarks")
    public String showMyBookmarks(@RequestParam(value = "type", required = false) ContentType type,
                                  Authentication authentication,
                                  Model model) {
        String email = authentication != null ? authentication.getName() : "student@prepsprint.com";
        User user = userRepository.findByEmail(email).orElse(null);
        Long userId = user != null ? user.getId() : 1L;

        List<BookmarkItemDto> bookmarks = bookmarkService.getUserBookmarks(userId, type);

        model.addAttribute("bookmarks", bookmarks);
        model.addAttribute("selectedType", type);
        model.addAttribute("activeNav", "bookmarks");

        return "bookmarks/index";
    }
}
