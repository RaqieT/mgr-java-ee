package pl.raqiet.housing.cooperative.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.raqiet.housing.cooperative.api.service.AppUserService;
import pl.raqiet.housing.cooperative.api.service.BlockService;
import pl.raqiet.housing.cooperative.domain.entity.AppUser;
import pl.raqiet.housing.cooperative.domain.entity.Role;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
@RolesAllowed("ROLE_ADMINISTRATOR")
public class AppUserController {
    private final AppUserService appUserService;
    private final BlockService blockService;

    public AppUserController(AppUserService appUserService, BlockService blockService) {
        this.appUserService = appUserService;
        this.blockService = blockService;
    }

    @RequestMapping(value = "/appUsers")
    public String showAppUsers(Model model) {

        model.addAttribute("appUserList", appUserService.listAppUser());
        model.addAttribute("roles", Role.values());
        return "appUser.html";
    }

    @RequestMapping(value = "/appUsers/manager")
    public String showUserManager(Model model, HttpServletRequest request) {
        String appUserId;
        AppUser appUser;
        try {
            appUserId = ServletRequestUtils.getRequiredStringParameter(request, "appUserId");
            appUser = appUserService.getAppUser(UUID.fromString(appUserId));
            model.addAttribute("appUser", appUser);
        } catch (ServletRequestBindingException e) {
            String userType;
            try {
                userType = ServletRequestUtils.getRequiredStringParameter(request, "userType");
            } catch (ServletRequestBindingException ex) {
                userType = Role.LOCATOR.name();
            }
            appUser = new AppUser();
            appUser.setRole(Role.valueOf(userType));
        }
        if (Role.MODERATOR == appUser.getRole()) {
            model.addAttribute("availableBlocks", blockService.listAllBlocks());
        }
        model.addAttribute("appUser", appUser);
        model.addAttribute("roles", Role.values());
        return "appUserManager.html";
    }

    @RequestMapping(value = "/appUsers/save", method = RequestMethod.POST)
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

