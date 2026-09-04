package com.prepsprint.service;

import com.prepsprint.dto.FormulaDto;
import com.prepsprint.entity.ActivityLog;
import com.prepsprint.entity.Formula;
import com.prepsprint.entity.User;
import com.prepsprint.enums.ActivityType;
import com.prepsprint.enums.ContentType;
import com.prepsprint.repository.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FormulaService {

    private final FormulaRepository formulaRepository;
    private final BookmarkRepository bookmarkRepository;
    private final ActivityLogRepository activityLogRepository;
    private final UserRepository userRepository;

    public FormulaService(FormulaRepository formulaRepository,
                          BookmarkRepository bookmarkRepository,
                          ActivityLogRepository activityLogRepository,
                          UserRepository userRepository) {
        this.formulaRepository = formulaRepository;
        this.bookmarkRepository = bookmarkRepository;
        this.activityLogRepository = activityLogRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<FormulaDto> getFormulas(Long userId, Long topicId, String search) {
        List<Formula> list;
        if (topicId != null) {
            list = formulaRepository.findByTopicIdAndActiveTrue(topicId);
        } else {
            list = formulaRepository.findByActiveTrue();
        }

        String query = search != null ? search.trim().toLowerCase() : "";

        return list.stream()
                .filter(f -> query.isEmpty()
                        || f.getTitle().toLowerCase().contains(query)
                        || f.getFormula().toLowerCase().contains(query)
                        || (f.getShortcut() != null && f.getShortcut().toLowerCase().contains(query)))
                .map(f -> mapToDto(userId, f))
                .collect(Collectors.toList());
    }

    public FormulaDto getFormulaById(Long userId, Long formulaId) {
        Formula f = formulaRepository.findById(formulaId)
                .orElseThrow(() -> new IllegalArgumentException("Formula not found with id: " + formulaId));

        if (userId != null) {
            logFormulaView(userId, f);
        }

        return mapToDto(userId, f);
    }

    @Transactional(readOnly = true)
    public List<FormulaDto> getRecentlyViewedFormulas(Long userId, int limit) {
        if (userId == null) {
            return new ArrayList<>();
        }

        List<ActivityLog> logs = activityLogRepository.findByUserIdOrderByCreatedAtDesc(userId, PageRequest.of(0, 20));

        List<Long> viewedFormulaIds = logs.stream()
                .filter(log -> log.getActivityType() == ActivityType.FORMULA_VIEWED && log.getContentId() != null)
                .map(ActivityLog::getContentId)
                .distinct()
                .limit(limit)
                .collect(Collectors.toList());

        List<FormulaDto> recent = new ArrayList<>();
        for (Long fid : viewedFormulaIds) {
            formulaRepository.findById(fid).ifPresent(f -> recent.add(mapToDto(userId, f)));
        }

        return recent;
    }

    private void logFormulaView(Long userId, Formula formula) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return;

        ActivityLog log = new ActivityLog();
        log.setUser(user);
        log.setActivityType(ActivityType.FORMULA_VIEWED);
        log.setContentType(ContentType.FORMULA);
        log.setContentId(formula.getId());
        log.setMetadata("Viewed formula: " + formula.getTitle());
        log.setCreatedAt(LocalDateTime.now());
        activityLogRepository.save(log);
    }

    private FormulaDto mapToDto(Long userId, Formula f) {
        boolean bookmarked = false;
        if (userId != null) {
            bookmarked = bookmarkRepository.existsByUserIdAndContentTypeAndContentId(userId, ContentType.FORMULA, f.getId());
        }

        return new FormulaDto(
                f.getId(),
                f.getTopic().getId(),
                f.getTopic().getName(),
                f.getTopic().getSubject().getId(),
                f.getTopic().getSubject().getName(),
                f.getTitle(),
                f.getFormula(),
                f.getVariables(),
                f.getShortcut(),
                f.getExample(),
                f.getPriority(),
                bookmarked
        );
    }
}
