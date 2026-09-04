package com.prepsprint.service;

import com.prepsprint.dto.BookmarkItemDto;
import com.prepsprint.entity.*;
import com.prepsprint.enums.ActivityType;
import com.prepsprint.enums.ContentType;
import com.prepsprint.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final FlashcardRepository flashcardRepository;
    private final FormulaRepository formulaRepository;
    private final QuestionRepository questionRepository;
    private final TechnicalQuestionRepository technicalQuestionRepository;
    private final HRQuestionRepository hrQuestionRepository;
    private final ActivityLogRepository activityLogRepository;

    public BookmarkService(BookmarkRepository bookmarkRepository,
                           UserRepository userRepository,
                           FlashcardRepository flashcardRepository,
                           FormulaRepository formulaRepository,
                           QuestionRepository questionRepository,
                           TechnicalQuestionRepository technicalQuestionRepository,
                           HRQuestionRepository hrQuestionRepository,
                           ActivityLogRepository activityLogRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.userRepository = userRepository;
        this.flashcardRepository = flashcardRepository;
        this.formulaRepository = formulaRepository;
        this.questionRepository = questionRepository;
        this.technicalQuestionRepository = technicalQuestionRepository;
        this.hrQuestionRepository = hrQuestionRepository;
        this.activityLogRepository = activityLogRepository;
    }

    public Bookmark addBookmark(Long userId, ContentType contentType, Long contentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        // Idempotency check against unique constraint (user_id, content_type, content_id)
        List<Bookmark> existingList = bookmarkRepository.findByUserIdAndContentType(userId, contentType);
        for (Bookmark bm : existingList) {
            if (bm.getContentId().equals(contentId)) {
                return bm; // Return existing without duplicate exception
            }
        }

        Bookmark bookmark = new Bookmark();
        bookmark.setUser(user);
        bookmark.setContentType(contentType);
        bookmark.setContentId(contentId);
        bookmark.setCreatedAt(LocalDateTime.now());

        Bookmark saved = bookmarkRepository.save(bookmark);

        // Log Activity
        ActivityLog log = new ActivityLog();
        log.setUser(user);
        log.setActivityType(ActivityType.BOOKMARK_ADDED);
        log.setContentType(contentType);
        log.setContentId(contentId);
        log.setMetadata("Bookmarked " + contentType.name() + " #" + contentId);
        log.setCreatedAt(LocalDateTime.now());
        activityLogRepository.save(log);

        return saved;
    }

    public void removeBookmark(Long userId, ContentType contentType, Long contentId) {
        bookmarkRepository.deleteByUserIdAndContentTypeAndContentId(userId, contentType, contentId);
    }

    @Transactional(readOnly = true)
    public boolean isBookmarked(Long userId, ContentType contentType, Long contentId) {
        if (userId == null) return false;
        return bookmarkRepository.existsByUserIdAndContentTypeAndContentId(userId, contentType, contentId);
    }

    @Transactional(readOnly = true)
    public List<BookmarkItemDto> getUserBookmarks(Long userId, ContentType filterType) {
        List<Bookmark> bookmarks;
        if (filterType != null) {
            bookmarks = bookmarkRepository.findByUserIdAndContentType(userId, filterType);
        } else {
            bookmarks = bookmarkRepository.findByUserId(userId);
        }

        List<BookmarkItemDto> items = new ArrayList<>();
        for (Bookmark bm : bookmarks) {
            BookmarkItemDto item = resolveBookmarkItem(bm);
            if (item != null) {
                items.add(item);
            }
        }

        return items;
    }

    private BookmarkItemDto resolveBookmarkItem(Bookmark bm) {
        String title = "Bookmarked Content #" + bm.getContentId();
        String category = bm.getContentType().name();
        String targetUrl = "/dashboard";

        if (bm.getContentType() == ContentType.FLASHCARD) {
            Optional<Flashcard> fc = flashcardRepository.findById(bm.getContentId());
            if (fc.isPresent()) {
                title = fc.get().getQuestion();
                category = fc.get().getTopic().getName();
                targetUrl = "/flashcards/" + fc.get().getId();
            } else return null;
        } else if (bm.getContentType() == ContentType.FORMULA) {
            Optional<Formula> form = formulaRepository.findById(bm.getContentId());
            if (form.isPresent()) {
                title = form.get().getTitle() + " (" + form.get().getFormula() + ")";
                category = form.get().getTopic().getName();
                targetUrl = "/formulas?topicId=" + form.get().getTopic().getId();
            } else return null;
        } else if (bm.getContentType() == ContentType.MCQ) {
            Optional<Question> q = questionRepository.findById(bm.getContentId());
            if (q.isPresent()) {
                title = q.get().getQuestionText();
                category = q.get().getTopic().getName();
                targetUrl = "/quiz/topic/" + q.get().getTopic().getId();
            } else return null;
        } else if (bm.getContentType() == ContentType.TECHNICAL_QUESTION) {
            Optional<TechnicalQuestion> tq = technicalQuestionRepository.findById(bm.getContentId());
            if (tq.isPresent()) {
                title = tq.get().getQuestion();
                category = tq.get().getTopic() != null ? tq.get().getTopic().getName() : "Technical Interview";
                targetUrl = "/interview/technical";
            } else return null;
        } else if (bm.getContentType() == ContentType.HR_QUESTION) {
            Optional<HrQuestion> hq = hrQuestionRepository.findById(bm.getContentId());
            if (hq.isPresent()) {
                title = hq.get().getQuestion();
                category = "HR Interview";
                targetUrl = "/interview/hr";
            } else return null;
        }

        return new BookmarkItemDto(
                bm.getId(),
                bm.getContentType(),
                bm.getContentId(),
                title,
                category,
                targetUrl,
                bm.getCreatedAt()
        );
    }
}
