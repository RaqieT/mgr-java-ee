package pl.raqiet.housing.cooperative.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import pl.raqiet.housing.cooperative.api.service.AppUserService;
import pl.raqiet.housing.cooperative.api.service.ReCaptchaService;
import pl.raqiet.housing.cooperative.domain.AppUser;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RegisterController {

    private AppUserService appUserService;
    private ReCaptchaService reCaptchaService;

    public RegisterController(AppUserService appUserService, ReCaptchaService reCaptchaService) {
        this.appUserService = appUserService;
        this.reCaptchaService = reCaptchaService;
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
    public RedirectView registerUser(@ModelAttribute AppUser appUser, HttpServletRequest request, RedirectAttributes redir) {
        if (!reCaptchaService.verify(request.getParameter("g-recaptcha-response"))) {
            var redirectView = new RedirectView("/register-error",true);
            redir.addFlashAttribute("errorMsg", "wrong.captcha");
            return redirectView;
        }
        appUserService.register(appUser);
        return new RedirectView("/register-success", true);
    }
}
