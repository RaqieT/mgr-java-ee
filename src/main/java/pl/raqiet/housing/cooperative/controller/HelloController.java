package pl.raqiet.housing.cooperative.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

@Controller
public class HelloController {
    @GetMapping("/")
    public String index(Locale locale, Model model) {
        return "index";
    }
}

