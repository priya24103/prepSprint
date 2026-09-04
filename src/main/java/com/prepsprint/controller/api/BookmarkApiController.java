package com.prepsprint.controller.api;

import com.prepsprint.dto.BookmarkRequestDto;
import com.prepsprint.entity.User;
import com.prepsprint.enums.ContentType;
import com.prepsprint.repository.UserRepository;
import com.prepsprint.service.BookmarkService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/bookmarks")
public class BookmarkApiController {

    private final BookmarkService bookmarkService;
    private final UserRepository userRepository;

    public BookmarkApiController(BookmarkService bookmarkService, UserRepository userRepository) {
        this.bookmarkService = bookmarkService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addBookmark(@Valid @RequestBody BookmarkRequestDto request,
                                                           Authentication authentication) {
        String email = authentication != null ? authentication.getName() : "student@prepsprint.com";
        User user = userRepository.findByEmail(email).orElse(null);
        Long userId = user != null ? user.getId() : 1L;

        bookmarkService.addBookmark(userId, request.getContentType(), request.getContentId());

        return ResponseEntity.ok(Map.of(
                "success", true,
                "bookmarked", true,
                "contentType", request.getContentType().name(),
                "contentId", request.getContentId()
        ));
    }

    @DeleteMapping("/{contentType}/{contentId}")
    public ResponseEntity<Map<String, Object>> removeBookmark(@PathVariable("contentType") ContentType contentType,
                                                              @PathVariable("contentId") Long contentId,
                                                              Authentication authentication) {
        String email = authentication != null ? authentication.getName() : "student@prepsprint.com";
        User user = userRepository.findByEmail(email).orElse(null);
        Long userId = user != null ? user.getId() : 1L;

        bookmarkService.removeBookmark(userId, contentType, contentId);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "bookmarked", false,
                "contentType", contentType.name(),
                "contentId", contentId
        ));
    }
}
