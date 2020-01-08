package pl.raqiet.housing.cooperative.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.raqiet.housing.cooperative.api.service.BlockService;
import pl.raqiet.housing.cooperative.domain.Block;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class BlockController {
    @NonNull
    private final BlockService blockService;

    @RequestMapping(value = "/blocks")
    public String showBlocks(Model model) {
        model.addAttribute("blockList", blockService.listAllBlocks());
        return "block";
    }

    @RequestMapping(value = "/blocks/manager")
    public String showBlockManager(Model model, HttpServletRequest request) {
        String blockId;
        Block block;
        try {
            blockId = ServletRequestUtils.getRequiredStringParameter(request, "blockId");
            block = blockService.getBlock(UUID.fromString(blockId));
        } catch (ServletRequestBindingException e) {
            block = new Block();
        }
        model.addAttribute("block", block);
        model.addAttribute("flats", block.getFlats());
        return "blockManager";
    }

    @RequestMapping(value = "/saveBlock", method = RequestMethod.POST)
    public String saveBlock(@ModelAttribute Block block) {

        if (block.getId() == null) {
            blockService.addBlock(block);
        } else {
            blockService.editBlock(block);
        }

        return "redirect:blocks.html";
    }

    @RequestMapping("/blocks/delete/{blockId}")
    public String deleteBlock(@PathVariable("blockId") UUID id) {
        blockService.removeBlock(id);
        return "redirect:/blocks.html";
    }
}
