package com.prepsprint.controller;

import com.prepsprint.dto.FormulaDto;
import com.prepsprint.entity.Topic;
import com.prepsprint.entity.User;
import com.prepsprint.repository.TopicRepository;
import com.prepsprint.repository.UserRepository;
import com.prepsprint.service.FormulaService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/formulas")
public class FormulaController {

    private final FormulaService formulaService;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    public FormulaController(FormulaService formulaService,
                             TopicRepository topicRepository,
                             UserRepository userRepository) {
        this.formulaService = formulaService;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String listFormulas(@RequestParam(value = "topicId", required = false) Long topicId,
                               @RequestParam(value = "search", required = false) String search,
                               Authentication authentication,
                               Model model) {
        String email = authentication != null ? authentication.getName() : "student@prepsprint.com";
        User user = userRepository.findByEmail(email).orElse(null);
        Long userId = user != null ? user.getId() : 1L;

        List<FormulaDto> formulas = formulaService.getFormulas(userId, topicId, search);
        List<FormulaDto> recentFormulas = formulaService.getRecentlyViewedFormulas(userId, 5);
        List<Topic> topics = topicRepository.findByActiveTrue();

        model.addAttribute("formulas", formulas);
        model.addAttribute("recentFormulas", recentFormulas);
        model.addAttribute("topics", topics);
        model.addAttribute("selectedTopicId", topicId);
        model.addAttribute("search", search);
        model.addAttribute("activeNav", "formulas");

        return "formulas/index";
    }

    @GetMapping("/quick")
    public String showQuickFormulaMode(@RequestParam(value = "topicId", required = false) Long topicId,
                                      @RequestParam(value = "index", required = false, defaultValue = "0") int index,
                                      Authentication authentication,
                                      Model model) {
        String email = authentication != null ? authentication.getName() : "student@prepsprint.com";
        User user = userRepository.findByEmail(email).orElse(null);
        Long userId = user != null ? user.getId() : 1L;

        List<FormulaDto> formulas = formulaService.getFormulas(userId, topicId, null);

        if (formulas.isEmpty()) {
            return "redirect:/formulas";
        }

        int currentIndex = Math.max(0, Math.min(index, formulas.size() - 1));
        FormulaDto currentFormula = formulas.get(currentIndex);

        // Log view
        formulaService.getFormulaById(userId, currentFormula.getId());

        model.addAttribute("currentFormula", currentFormula);
        model.addAttribute("currentIndex", currentIndex);
        model.addAttribute("totalCount", formulas.size());
        model.addAttribute("hasPrev", currentIndex > 0);
        model.addAttribute("hasNext", currentIndex < formulas.size() - 1);
        model.addAttribute("selectedTopicId", topicId);

        return "formulas/quick";
    }
}
