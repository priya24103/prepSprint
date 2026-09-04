package com.prepsprint.repository;

import com.prepsprint.entity.Bookmark;
import com.prepsprint.enums.ContentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findByUserIdAndContentType(Long userId, ContentType type);
    List<Bookmark> findByUserId(Long userId);
    boolean existsByUserIdAndContentTypeAndContentId(Long userId, ContentType type, Long contentId);

    @Transactional
    void deleteByUserIdAndContentTypeAndContentId(Long userId, ContentType type, Long contentId);
}
