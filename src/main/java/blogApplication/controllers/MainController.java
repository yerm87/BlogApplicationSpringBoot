package blogApplication.controllers;

import blogApplication.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import simpleObjects.Bird;

import java.nio.file.Path;

@Controller
public class MainController {

    @Autowired
    public Bird bird;

    @GetMapping("/")
    public String greeting(Model model) {
        model.addAttribute("name", "Some text");
        model.addAttribute("bird", bird);
        return "home";
    }

    @PostMapping("/handler")
    public String handleFormRequest(@RequestParam(name="title") String title,
                                    @RequestParam(name="anons") String anons,
                                    @RequestParam(name="full_text") String fullText,
                                    Model model){
        Post post = new Post(title, anons, fullText);
        model.addAttribute("data", post);
        return "form-result";
    }
}