package pl.raqiet.housing.cooperative.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.raqiet.housing.cooperative.api.service.AppUserService;
import pl.raqiet.housing.cooperative.domain.AppUser;

@Controller
public class RegisterController {

    private AppUserService appUserService;

    public RegisterController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @RequestMapping("/register")
    public String register() {
        return "register.html";
    }

    @RequestMapping("/register-error")
    public String registerError(Model model) {
        model.addAttribute("registerError", true);
        return "register.html";
    }

    @RequestMapping("/register-success")
    public String registerSuccess(Model model) {
        return "register-success.html";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute AppUser appUser) {
        appUserService.register(appUser);
        return "redirect:/register-success";
    }
}
