package com.prepsprint.service;

import com.prepsprint.dto.SubjectSummaryDto;
import com.prepsprint.dto.TopicSummaryDto;
import com.prepsprint.entity.Subject;
import com.prepsprint.entity.Topic;
import com.prepsprint.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final TopicRepository topicRepository;
    private final FlashcardRepository flashcardRepository;
    private final FormulaRepository formulaRepository;
    private final QuestionRepository questionRepository;

    public SubjectService(SubjectRepository subjectRepository,
                          TopicRepository topicRepository,
                          FlashcardRepository flashcardRepository,
                          FormulaRepository formulaRepository,
                          QuestionRepository questionRepository) {
        this.subjectRepository = subjectRepository;
        this.topicRepository = topicRepository;
        this.flashcardRepository = flashcardRepository;
        this.formulaRepository = formulaRepository;
        this.questionRepository = questionRepository;
    }

    public List<SubjectSummaryDto> getAllActiveSubjects(String search) {
        List<Subject> subjects = subjectRepository.findByActiveTrue();
        String query = search != null ? search.trim().toLowerCase() : "";

        return subjects.stream()
                .filter(s -> query.isEmpty() || s.getName().toLowerCase().contains(query) || (s.getDescription() != null && s.getDescription().toLowerCase().contains(query)))
                .map(s -> {
                    int topicCount = topicRepository.findBySubjectIdAndActiveTrue(s.getId()).size();
                    return new SubjectSummaryDto(s.getId(), s.getName(), s.getDescription(), topicCount);
                })
                .collect(Collectors.toList());
    }

    public SubjectSummaryDto getSubjectById(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found with id: " + subjectId));

        int topicCount = topicRepository.findBySubjectIdAndActiveTrue(subject.getId()).size();
        return new SubjectSummaryDto(subject.getId(), subject.getName(), subject.getDescription(), topicCount);
    }

    public List<TopicSummaryDto> getTopicsForSubject(Long subjectId, String search, String filter) {
        List<Topic> topics = topicRepository.findBySubjectIdAndActiveTrue(subjectId);
        String query = search != null ? search.trim().toLowerCase() : "";

        List<TopicSummaryDto> dtos = new ArrayList<>();
        for (Topic topic : topics) {
            if (!query.isEmpty() && !topic.getName().toLowerCase().contains(query) && (topic.getDescription() == null || !topic.getDescription().toLowerCase().contains(query))) {
                continue;
            }

            int fcCount = flashcardRepository.findByTopicIdAndActiveTrue(topic.getId()).size();
            int formCount = formulaRepository.findByTopicIdAndActiveTrue(topic.getId()).size();
            int mcqCount = questionRepository.findByTopicIdAndActiveTrue(topic.getId()).size();

            // Progress & Accuracy default to 0.0% for now (will compute from real attempt repositories when Prompts 13-15 exist)
            double progress = 0.0;
            double accuracy = 0.0;

            TopicSummaryDto dto = new TopicSummaryDto(
                    topic.getId(),
                    topic.getSubject().getId(),
                    topic.getSubject().getName(),
                    topic.getName(),
                    topic.getDescription(),
                    fcCount,
                    formCount,
                    mcqCount,
                    progress,
                    accuracy
            );

            dtos.add(dto);
        }

        return dtos;
    }
}
