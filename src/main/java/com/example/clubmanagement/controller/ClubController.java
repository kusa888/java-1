package com.example.clubmanagement.controller;

import com.example.clubmanagement.entity.Club;
import com.example.clubmanagement.entity.Member;
import com.example.clubmanagement.service.ClubService;
import com.example.clubmanagement.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/clubs")
public class ClubController {
    
    @Autowired
    private ClubService clubService;
    
    @Autowired
    private MemberService memberService;
    
    @GetMapping
    public String listClubs(Model model) {
        List<Club> clubs = clubService.getAllClubs();
        model.addAttribute("clubs", clubs);
        return "clubs/list";
    }
    
    @GetMapping("/add")
    public String showAddForm(Model model) {
        List<Member> members = memberService.getActiveMembers();
        model.addAttribute("club", new Club());
        model.addAttribute("members", members);
        return "clubs/add";
    }
    
    @PostMapping("/add")
    public String addClub(@RequestParam String name,
                         @RequestParam String description,
                         @RequestParam Long presidentId,
                         Model model) {
        try {
            clubService.createClub(name, description, presidentId);
            return "redirect:/clubs";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("club", new Club());
            model.addAttribute("members", memberService.getActiveMembers());
            return "clubs/add";
        }
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Club club = clubService.getClubById(id);
        model.addAttribute("club", club);
        return "clubs/edit";
    }
    
    @PostMapping("/edit/{id}")
    public String updateClub(@PathVariable Long id,
                            @RequestParam(required = false) String name,
                            @RequestParam(required = false) String description,
                            Model model) {
        try {
            clubService.updateClub(id, name, description);
            return "redirect:/clubs";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("club", clubService.getClubById(id));
            return "clubs/edit";
        }
    }
    
    @GetMapping("/delete/{id}")
    public String deleteClub(@PathVariable Long id) {
        try {
            clubService.deleteClub(id);
        } catch (IllegalArgumentException e) {
        }
        return "redirect:/clubs";
    }
    
    @GetMapping("/{id}")
    public String viewClub(@PathVariable Long id, Model model) {
        Club club = clubService.getClubById(id);
        List<Member> allMembers = memberService.getActiveMembers();
        model.addAttribute("club", club);
        model.addAttribute("allMembers", allMembers);
        return "clubs/view";
    }
    
    @PostMapping("/{id}/add-member")
    public String addMemberToClub(@PathVariable Long id, @RequestParam Long memberId) {
        try {
            clubService.addMemberToClub(id, memberId);
        } catch (IllegalArgumentException e) {
        }
        return "redirect:/clubs/" + id;
    }
    
    @GetMapping("/{id}/remove-member/{memberId}")
    public String removeMemberFromClub(@PathVariable Long id, @PathVariable Long memberId) {
        try {
            clubService.removeMemberFromClub(id, memberId);
        } catch (IllegalArgumentException e) {
        }
        return "redirect:/clubs/" + id;
    }
    
    @PostMapping("/{id}/change-president")
    public String changePresident(@PathVariable Long id, @RequestParam Long presidentId) {
        try {
            clubService.changePresident(id, presidentId);
        } catch (IllegalArgumentException e) {
        }
        return "redirect:/clubs/" + id;
    }
}