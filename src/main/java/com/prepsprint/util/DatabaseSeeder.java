package com.prepsprint.util;

import com.prepsprint.entity.*;
import com.prepsprint.enums.Difficulty;
import com.prepsprint.enums.Role;
import com.prepsprint.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final TopicRepository topicRepository;
    private final FlashcardRepository flashcardRepository;
    private final FormulaRepository formulaRepository;
    private final QuestionRepository questionRepository;
    private final TechnicalQuestionRepository technicalQuestionRepository;
    private final HRQuestionRepository hrQuestionRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public DatabaseSeeder(UserRepository userRepository,
                          SubjectRepository subjectRepository,
                          TopicRepository topicRepository,
                          FlashcardRepository flashcardRepository,
                          FormulaRepository formulaRepository,
                          QuestionRepository questionRepository,
                          TechnicalQuestionRepository technicalQuestionRepository,
                          HRQuestionRepository hrQuestionRepository,
                          BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.topicRepository = topicRepository;
        this.flashcardRepository = flashcardRepository;
        this.formulaRepository = formulaRepository;
        this.questionRepository = questionRepository;
        this.technicalQuestionRepository = technicalQuestionRepository;
        this.hrQuestionRepository = hrQuestionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Seed users if not already present
        seedUsers();

        // PRD Section 22: Avoid destructive seed behavior on every restart
        if (subjectRepository.count() > 0) {
            return;
        }

        seedContent();
    }

    private void seedUsers() {
        if (!userRepository.existsByEmail("student@prepsprint.com")) {
            User student = new User();
            student.setName("Demo Student");
            student.setEmail("student@prepsprint.com");
            student.setPasswordHash(passwordEncoder.encode("student123"));
            student.setRole(Role.STUDENT);
            userRepository.save(student);
        }

        if (!userRepository.existsByEmail("admin@prepsprint.com")) {
            User admin = new User();
            admin.setName("Demo Admin");
            admin.setEmail("admin@prepsprint.com");
            admin.setPasswordHash(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        }
    }

    private void seedContent() {
        // 1. Java/OOP
        Subject javaSubject = createSubject("Java/OOP", "Core Java language concepts, Object-Oriented Programming, Collections, and JVM fundamentals.");
        List<Topic> javaTopics = createTopics(javaSubject, new String[]{
                "Classes & Objects", "Encapsulation & Inheritance", "Polymorphism & Abstraction", "Interfaces & Exceptions", "Java Collections & Generics"
        });

        // 2. DSA
        Subject dsaSubject = createSubject("DSA", "Data Structures, Algorithms, Time/Space Complexity, and Problem Solving.");
        List<Topic> dsaTopics = createTopics(dsaSubject, new String[]{
                "Arrays & Strings", "Linked Lists", "Stacks & Queues", "Trees & BST", "Sorting & Searching"
        });

        // 3. DBMS
        Subject dbmsSubject = createSubject("DBMS", "Database Management Systems, Relational Model, Normalization, and SQL.");
        List<Topic> dbmsTopics = createTopics(dbmsSubject, new String[]{
                "Relational Concepts & Keys", "Normalization", "SQL Queries & Joins", "Transactions & ACID", "Indexing & Views"
        });

        // 4. OS
        Subject osSubject = createSubject("OS", "Operating Systems, Process Management, Memory, Storage, and Concurrency.");
        List<Topic> osTopics = createTopics(osSubject, new String[]{
                "Processes & Threads", "CPU Scheduling", "Deadlocks & Synchronization", "Paging & Virtual Memory", "File Systems"
        });

        // 5. CN
        Subject cnSubject = createSubject("CN", "Computer Networks, Protocol Suites, Layering, and Network Security.");
        List<Topic> cnTopics = createTopics(cnSubject, new String[]{
                "OSI & TCP/IP Models", "IP Addressing & Subnetting", "TCP vs UDP Protocols", "HTTP & HTTPS", "DNS & Routing"
        });

        // 6. Quant Aptitude
        Subject quantSubject = createSubject("Quant Aptitude", "Quantitative Aptitude, Speed Mathematics, and Numerical Problem Solving.");
        List<Topic> quantTopics = createTopics(quantSubject, new String[]{
                "Percentages & Profit Loss", "Ratios & Averages", "Time, Speed & Work", "Permutations & Probability", "Number System & Algebra"
        });

        // 7. Logical Reasoning
        Subject lrSubject = createSubject("Logical Reasoning", "Analytical and Logical Reasoning, Puzzles, and Data Interpretation.");
        List<Topic> lrTopics = createTopics(lrSubject, new String[]{
                "Series & Coding-Decoding", "Blood Relations & Directions", "Syllogism & Venn Diagrams", "Seating Arrangements", "Data Sufficiency"
        });

        // 8. Verbal
        Subject verbalSubject = createSubject("Verbal", "Verbal Ability, English Grammar, Vocabulary, and Comprehension.");
        List<Topic> verbalTopics = createTopics(verbalSubject, new String[]{
                "Grammar & Sentence Correction", "Vocabulary & Synonyms", "Reading Comprehension", "Para Jumbles", "Idioms & Phrases"
        });

        // 9. Technical Interview
        Subject techSubject = createSubject("Technical Interview", "Core Technical Interview Q&A, Intent Analysis, and Viva Preparation.");
        List<Topic> techTopics = createTopics(techSubject, new String[]{
                "Java & OOP Viva", "System Design Basics", "SQL & DB viva", "OS & CN Viva", "Project & Code Review"
        });

        // 10. HR Interview
        Subject hrSubject = createSubject("HR Interview", "HR Preparation, Behavioral Questions, STAR Method, and Career Pitch.");
        List<Topic> hrTopics = createTopics(hrSubject, new String[]{
                "Behavioral & Strengths", "Career Goals & Motivation", "Teamwork & Conflict", "Company Knowledge", "Situational & Ethics"
        });

        // --- FLASHCARDS (12+ each for Java, DBMS, OS, CN) ---
        seedFlashcardsForTopics(javaTopics, "Java/OOP");
        seedFlashcardsForTopics(dbmsTopics, "DBMS");
        seedFlashcardsForTopics(osTopics, "OS");
        seedFlashcardsForTopics(cnTopics, "CN");

        // --- QUESTIONS / MCQs (8+ each for Java, DBMS, OS, CN, Quant) ---
        seedQuestionsForTopics(javaTopics, "Java/OOP");
        seedQuestionsForTopics(dbmsTopics, "DBMS");
        seedQuestionsForTopics(osTopics, "OS");
        seedQuestionsForTopics(cnTopics, "CN");
        seedQuestionsForTopics(quantTopics, "Quant Aptitude");

        // --- FORMULAS (22+ for Quant Aptitude) ---
        seedFormulasForTopics(quantTopics);

        // --- TECHNICAL QUESTIONS (16+) ---
        seedTechnicalQuestions(techTopics);

        // --- HR QUESTIONS (12+) ---
        seedHrQuestions();
    }

    private Subject createSubject(String name, String description) {
        Subject subject = new Subject();
        subject.setName(name);
        subject.setDescription(description);
        subject.setActive(true);
        return subjectRepository.save(subject);
    }

    private List<Topic> createTopics(Subject subject, String[] topicNames) {
        List<Topic> topics = new ArrayList<>();
        for (String topicName : topicNames) {
            Topic topic = new Topic();
            topic.setSubject(subject);
            topic.setName(topicName);
            topic.setDescription("Placeholder description for " + topicName);
            topic.setActive(true);
            topics.add(topicRepository.save(topic));
        }
        return topics;
    }

    private void seedFlashcardsForTopics(List<Topic> topics, String category) {
        Difficulty[] difficulties = Difficulty.values();
        int cardCount = 0;
        for (Topic topic : topics) {
            for (int i = 1; i <= 3; i++) {
                cardCount++;
                Flashcard card = new Flashcard();
                card.setTopic(topic);
                card.setQuestion("Placeholder question text for " + category + " - " + topic.getName() + " #" + i);
                card.setAnswer("Placeholder answer text for " + category + " - " + topic.getName() + " #" + i);
                card.setExplanation("Placeholder explanation text for " + category + " - " + topic.getName() + " #" + i);
                card.setExampleCode("Placeholder code example for " + category + " #" + i);
                card.setDifficulty(difficulties[cardCount % 3]);
                card.setPriority((cardCount % 5) + 1);
                card.setActive(true);
                flashcardRepository.save(card);
            }
        }
    }

    private void seedQuestionsForTopics(List<Topic> topics, String category) {
        Difficulty[] difficulties = Difficulty.values();
        String[] options = {"A", "B", "C", "D"};
        int qCount = 0;
        for (Topic topic : topics) {
            for (int i = 1; i <= 2; i++) {
                qCount++;
                Question q = new Question();
                q.setTopic(topic);
                q.setQuestionText("Placeholder MCQ question text for " + category + " - " + topic.getName() + " #" + i);
                q.setOptionA("Placeholder Option A for " + category + " #" + qCount);
                q.setOptionB("Placeholder Option B for " + category + " #" + qCount);
                q.setOptionC("Placeholder Option C for " + category + " #" + qCount);
                q.setOptionD("Placeholder Option D for " + category + " #" + qCount);
                q.setCorrectOption(options[qCount % 4]);
                q.setExplanation("Placeholder MCQ explanation for " + category + " #" + qCount);
                q.setDifficulty(difficulties[qCount % 3]);
                q.setPriority((qCount % 5) + 1);
                q.setActive(true);
                questionRepository.save(q);
            }
        }
    }

    private void seedFormulasForTopics(List<Topic> quantTopics) {
        int formulaCount = 0;
        for (Topic topic : quantTopics) {
            for (int i = 1; i <= 5; i++) {
                formulaCount++;
                Formula formula = new Formula();
                formula.setTopic(topic);
                formula.setTitle("Placeholder Formula Title for " + topic.getName() + " #" + i);
                formula.setFormula("Formula = a^2 + b^2 + 2ab (Placeholder #" + formulaCount + ")");
                formula.setVariables("a = variable 1, b = variable 2");
                formula.setShortcut("Shortcut tip for " + topic.getName() + " #" + i);
                formula.setExample("Worked example for " + topic.getName() + " #" + i);
                formula.setPriority((formulaCount % 5) + 1);
                formula.setActive(true);
                formulaRepository.save(formula);
            }
        }
    }

    private void seedTechnicalQuestions(List<Topic> techTopics) {
        int count = 0;
        for (Topic topic : techTopics) {
            for (int i = 1; i <= 4; i++) {
                count++;
                TechnicalQuestion tq = new TechnicalQuestion();
                tq.setTopic(topic);
                tq.setQuestion("Placeholder Technical Question #" + count + " for " + topic.getName());
                tq.setWhatInterviewerChecks("Interviewer check criteria placeholder #" + count);
                tq.setShortAnswer("Short answer placeholder #" + count);
                tq.setDetailedAnswer("Detailed answer explanation placeholder #" + count);
                tq.setKeyPoints("Key point 1, Key point 2, Key point 3 for Q#" + count);
                tq.setTips("Pro tip for answering Question #" + count);
                tq.setPriority((count % 5) + 1);
                tq.setActive(true);
                technicalQuestionRepository.save(tq);
            }
        }
    }

    private void seedHrQuestions() {
        for (int i = 1; i <= 12; i++) {
            HrQuestion hq = new HrQuestion();
            hq.setQuestion("Placeholder HR Question #" + i + ": Tell me about a time when...");
            hq.setInterviewerIntent("Evaluates core communication, problem solving, and attitude #" + i);
            hq.setAnswerStructure("Situation -> Task -> Action -> Result (STAR Method #" + i + ")");
            hq.setSampleGuidance("Sample response framework for HR question #" + i);
            hq.setTips("Maintain confident body language and structured bullet points.");
            hq.setPriority((i % 5) + 1);
            hq.setActive(true);
            hrQuestionRepository.save(hq);
        }
    }
}
