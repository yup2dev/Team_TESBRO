package com.team.tesbro.Academy;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/academy")
@RequiredArgsConstructor
@Controller
public class AcademyController {
    private final AcademyRepository academyRepository;
    private final AcademyService academyService;

    @RequestMapping("/list")
    public String academy(Model model, @Param("keyword") String keyword) {
        List<Academy> academyList = this.academyService.getList(keyword);
        model.addAttribute("academyList", academyList);
        model.addAttribute("keyword", keyword);
        return "list";
    }

    @GetMapping("/list_local_ajax")
    public List<Academy> LocalData(@RequestParam String si, @RequestParam String gu, @RequestParam String dong){
    List<Academy> academyList = this.academyService.selectedLocalList(si, gu, dong);
    return academyList;
    }
}

//    @GetMapping(value = "/detail/{id}")
//    public String detail(Model model, @PathVariable("id") Integer id) {
//        Academy academy = this.academyService.getAcademy(id);
//        model.addAttribute("academy", academy);
//        return "academy_detail";
//    }
//}
