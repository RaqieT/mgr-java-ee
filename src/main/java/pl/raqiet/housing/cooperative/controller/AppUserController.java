package pl.raqiet.housing.cooperative.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pl.raqiet.housing.cooperative.domain.AppUser;

@Controller
public class AppUserController {

    @RequestMapping(value = "/appUsers")
    public ModelAndView showAppUsers() {

     AppUser appUser = new AppUser();

	 return new ModelAndView("appUser", "appUser", appUser);
    }

    @RequestMapping(value = "/addAppUser", method = RequestMethod.POST)
    public String addAppUser(@ModelAttribute("appUser") AppUser appUser) {

        System.out.println("First Name: " + appUser.getFirstName() +
                " Last Name: " + appUser.getLastName() + " Tel.: " +
                appUser.getTelephone() + " Email: " + appUser.getEmail());

        return "redirect:appUsers.html";
    }

}

