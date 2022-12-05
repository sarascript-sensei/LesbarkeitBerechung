package com.example.Lesbarkeit;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller

public class Text_Analyse_Controller {
    @GetMapping("/text_analyse")
    public String text_analyseForm(Model model) {
      model.addAttribute("text_analyse", new TextAnalyse());
      return "text_analyse";
    }
  
    @PostMapping("/text_analyse")
    public String text_analyseSubmit(@ModelAttribute TextAnalyse textAnalyse, Model model) {
      model.addAttribute("text_analyse", textAnalyse);
      return "result";
    }

}