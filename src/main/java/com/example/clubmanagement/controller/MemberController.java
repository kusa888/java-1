package com.example.clubmanagement.controller;

import com.example.clubmanagement.entity.Member;
import com.example.clubmanagement.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/members")
public class MemberController {
    
    @Autowired
    private MemberService memberService;
    
    @GetMapping
    public String listMembers(Model model) {
        List<Member> members = memberService.getAllMembers();
        model.addAttribute("members", members);
        return "members/list";
    }
    
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("member", new Member());
        return "members/add";
    }
    
    @PostMapping("/add")
    public String addMember(@RequestParam String name,
                           @RequestParam String studentId,
                           @RequestParam String phone,
                           @RequestParam String email,
                           @RequestParam String department,
                           Model model) {
        try {
            memberService.createMember(name, studentId, phone, email, department);
            return "redirect:/members";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("member", new Member());
            return "members/add";
        }
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Member member = memberService.getMemberById(id);
        model.addAttribute("member", member);
        return "members/edit";
    }
    
    @PostMapping("/edit/{id}")
    public String updateMember(@PathVariable Long id,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String phone,
                              @RequestParam(required = false) String email,
                              @RequestParam(required = false) String department,
                              Model model) {
        try {
            memberService.updateMember(id, name, phone, email, department);
            return "redirect:/members";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("member", memberService.getMemberById(id));
            return "members/edit";
        }
    }
    
    @GetMapping("/delete/{id}")
    public String deleteMember(@PathVariable Long id) {
        try {
            memberService.deleteMember(id);
        } catch (IllegalArgumentException e) {
        }
        return "redirect:/members";
    }
    
    @GetMapping("/toggle-status/{id}")
    public String toggleStatus(@PathVariable Long id) {
        Member member = memberService.getMemberById(id);
        if ("active".equals(member.getStatus())) {
            memberService.deactivateMember(id);
        } else {
            memberService.activateMember(id);
        }
        return "redirect:/members";
    }
}