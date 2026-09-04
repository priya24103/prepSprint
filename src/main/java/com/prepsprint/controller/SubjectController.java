package com.prepsprint.controller;

import com.prepsprint.dto.SubjectSummaryDto;
import com.prepsprint.dto.TopicSummaryDto;
import com.prepsprint.service.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public String listSubjects(@RequestParam(value = "search", required = false) String search,
                               @RequestParam(value = "filter", required = false, defaultValue = "ALL") String filter,
                               Model model) {
        List<SubjectSummaryDto> subjects = subjectService.getAllActiveSubjects(search);

        model.addAttribute("subjects", subjects);
        model.addAttribute("search", search);
        model.addAttribute("currentFilter", filter);
        model.addAttribute("activeNav", "subjects");

        return "subjects/index";
    }

    @GetMapping("/{id}")
    public String getSubjectDetail(@PathVariable("id") Long id,
                                   @RequestParam(value = "search", required = false) String search,
                                   @RequestParam(value = "filter", required = false, defaultValue = "ALL") String filter,
                                   Model model) {
        SubjectSummaryDto subject = subjectService.getSubjectById(id);
        List<TopicSummaryDto> topics = subjectService.getTopicsForSubject(id, search, filter);

        model.addAttribute("subject", subject);
        model.addAttribute("topics", topics);
        model.addAttribute("search", search);
        model.addAttribute("currentFilter", filter);
        model.addAttribute("activeNav", "subjects");

        return "subjects/detail";
    }
}
