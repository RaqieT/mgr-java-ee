package pl.raqiet.housing.cooperative.controller;

import lombok.RequiredArgsConstructor;
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
import pl.raqiet.housing.cooperative.api.service.FlatService;
import pl.raqiet.housing.cooperative.domain.entity.Flat;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class FlatController {
    private final FlatService flatService;
    private final BlockService blockService;
    private final AppUserService appUserService;

    @RequestMapping(value = "/flats")
    public String showFlats(Model model) {
        model.addAttribute("flatList", flatService.listAllFlats());
        return "flat.html";
    }

    @RequestMapping(value = "/flats/manager")
    public String showFlatManager(Model model, HttpServletRequest request) {
        String flatId;
        Flat flat;
        try {
            flatId = ServletRequestUtils.getRequiredStringParameter(request, "flatId");
            flat = flatService.getFlat(UUID.fromString(flatId));
        } catch (ServletRequestBindingException e) {
            flat = new Flat();
        }
        model.addAttribute("flat", flat);
        model.addAttribute("availableBlocks", blockService.listAllBlocks());
        if (flat.getOwner() != null) {
            model.addAttribute("availableUsers", appUserService
                    .listLocatorsWithoutFlatAndFlatOwner(flat.getOwner().getId()));
        } else {
            model.addAttribute("availableUsers", appUserService.listLocatorsWithoutFlat());
        }

        return "flatManager.html";
    }

    @RequestMapping(value = "/flats/save", method = RequestMethod.POST)
    public String saveFlat(@ModelAttribute Flat flat) {

        if (flat.getId() == null) {
            flatService.addFlat(flat);
        } else {
            flatService.editFlat(flat);
        }

        return "redirect:/flats";
    }

    @RequestMapping("/flats/delete/{flatId}")
    public String deleteFlat(@PathVariable("flatId") UUID id) {
        flatService.removeFlat(id);
        return "redirect:/flats";
    }
}
