package pl.raqiet.housing.cooperative.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pl.raqiet.housing.cooperative.api.service.AppUserService;
import pl.raqiet.housing.cooperative.domain.AppUser;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class AppUserController {
    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @RequestMapping(value = "/appUsers")
    public String showAppUsers(Model model) {

        model.addAttribute("appUserList", appUserService.listAppUser());
        return "appUser.html";
    }

    @RequestMapping(value = "/appUsers/manager")
    public String showUserManager(Model model, HttpServletRequest request) {
        String appUserId;
        try {
            appUserId = ServletRequestUtils.getRequiredStringParameter(request, "appUserId");
            model.addAttribute("appUser", appUserService.getAppUser(UUID.fromString(appUserId)));
        } catch (ServletRequestBindingException e) {
            model.addAttribute("appUser", new AppUser());
        }
        return "appUserManager.html";
    }

    @RequestMapping(value = "/saveAppUser", method = RequestMethod.POST)
    public String saveAppUser(@ModelAttribute AppUser appUser) {
        if (appUser.getId() == null) {
            appUserService.addAppUser(appUser);
        } else {
            appUserService.editAppUser(appUser);
        }

        return "redirect:/appUsers";
    }

    @RequestMapping("/appUsers/delete/{appUserId}")
    public String deleteUser(@PathVariable("appUserId") UUID id) {
        appUserService.removeAppUser(id);
        return "redirect:/appUsers";
    }
}

