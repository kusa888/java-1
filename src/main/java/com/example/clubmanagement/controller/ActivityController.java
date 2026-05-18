package com.example.clubmanagement.controller;

import com.example.clubmanagement.entity.Activity;
import com.example.clubmanagement.entity.Club;
import com.example.clubmanagement.entity.Member;
import com.example.clubmanagement.service.ActivityService;
import com.example.clubmanagement.service.ClubService;
import com.example.clubmanagement.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/activities")
public class ActivityController {
    
    @Autowired
    private ActivityService activityService;
    
    @Autowired
    private ClubService clubService;
    
    @Autowired
    private MemberService memberService;
    
    @GetMapping
    public String listActivities(Model model) {
        List<Activity> activities = activityService.getAllActivities();
        model.addAttribute("activities", activities);
        return "activities/list";
    }
    
    @GetMapping("/add")
    public String showAddForm(Model model) {
        List<Club> clubs = clubService.getAllClubs();
        model.addAttribute("activity", new Activity());
        model.addAttribute("clubs", clubs);
        return "activities/add";
    }
    
    @PostMapping("/add")
    public String addActivity(@RequestParam Long clubId,
                            @RequestParam String name,
                            @RequestParam String description,
                            @RequestParam String startTime,
                            @RequestParam String endTime,
                            @RequestParam String location,
                            Model model) {
        try {
            activityService.createActivity(clubId, name, description, startTime, endTime, location);
            return "redirect:/activities";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("activity", new Activity());
            model.addAttribute("clubs", clubService.getAllClubs());
            return "activities/add";
        }
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Activity activity = activityService.getActivityById(id);
        List<Club> clubs = clubService.getAllClubs();
        model.addAttribute("activity", activity);
        model.addAttribute("clubs", clubs);
        return "activities/edit";
    }
    
    @PostMapping("/edit/{id}")
    public String updateActivity(@PathVariable Long id,
                                @RequestParam(required = false) String name,
                                @RequestParam(required = false) String description,
                                @RequestParam(required = false) String startTime,
                                @RequestParam(required = false) String endTime,
                                @RequestParam(required = false) String location,
                                Model model) {
        try {
            activityService.updateActivity(id, name, description, startTime, endTime, location);
            return "redirect:/activities";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("activity", activityService.getActivityById(id));
            model.addAttribute("clubs", clubService.getAllClubs());
            return "activities/edit";
        }
    }
    
    @GetMapping("/delete/{id}")
    public String deleteActivity(@PathVariable Long id) {
        try {
            activityService.deleteActivity(id);
        } catch (IllegalArgumentException e) {
        }
        return "redirect:/activities";
    }
    
    @GetMapping("/{id}")
    public String viewActivity(@PathVariable Long id, Model model) {
        Activity activity = activityService.getActivityById(id);
        List<Member> allMembers = memberService.getActiveMembers();
        model.addAttribute("activity", activity);
        model.addAttribute("allMembers", allMembers);
        return "activities/view";
    }
    
    @PostMapping("/{id}/add-participant")
    public String addParticipant(@PathVariable Long id, @RequestParam Long memberId) {
        try {
            activityService.addParticipant(id, memberId);
        } catch (IllegalArgumentException e) {
        }
        return "redirect:/activities/" + id;
    }
    
    @GetMapping("/{id}/remove-participant/{memberId}")
    public String removeParticipant(@PathVariable Long id, @PathVariable Long memberId) {
        try {
            activityService.removeParticipant(id, memberId);
        } catch (IllegalArgumentException e) {
        }
        return "redirect:/activities/" + id;
    }
    
    @GetMapping("/{id}/start")
    public String startActivity(@PathVariable Long id) {
        try {
            activityService.startActivity(id);
        } catch (IllegalArgumentException e) {
        }
        return "redirect:/activities/" + id;
    }
    
    @GetMapping("/{id}/end")
    public String endActivity(@PathVariable Long id) {
        try {
            activityService.endActivity(id);
        } catch (IllegalArgumentException e) {
        }
        return "redirect:/activities/" + id;
    }
}