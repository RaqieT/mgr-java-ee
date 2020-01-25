package pl.raqiet.housing.cooperative.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
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
import pl.raqiet.housing.cooperative.domain.entity.AppUser;
import pl.raqiet.housing.cooperative.validator.AppUserValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final AppUserService appUserService;
    private final ReCaptchaService reCaptchaService;
    private final AppUserValidator appUserValidator;

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
    public RedirectView registerUser(@ModelAttribute AppUser appUser, HttpServletRequest request, BindingResult bindingResult, RedirectAttributes redir) {
        appUserValidator.validate(appUser, bindingResult);



        if (!reCaptchaService.verify(request.getParameter("g-recaptcha-response"))) {
            var redirectView = new RedirectView("/register-error",true);
            redir.addFlashAttribute("error", "wrong.captcha");
            return redirectView;
        }

        if (bindingResult.getErrorCount() != 0) {
            var redirectView = new RedirectView("/register-error",true);
            redir.addFlashAttribute("error", bindingResult.getAllErrors().get(0).getCode());
            return redirectView;
        }

        appUserService.register(appUser);
        return new RedirectView("/register-success", true);
    }
}
