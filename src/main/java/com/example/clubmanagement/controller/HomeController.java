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
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    
    @Autowired
    private MemberService memberService;
    
    @Autowired
    private ClubService clubService;
    
    @Autowired
    private ActivityService activityService;
    
    @GetMapping("/")
    public String home(Model model) {
        List<Member> members = memberService.getAllMembers();
        List<Club> clubs = clubService.getAllClubs();
        List<Activity> activities = activityService.getAllActivities();
        
        model.addAttribute("memberCount", members.size());
        model.addAttribute("clubCount", clubs.size());
        model.addAttribute("activityCount", activities.size());
        model.addAttribute("recentActivities", activities.size() > 5 ? activities.subList(0, 5) : activities);
        
        return "index";
    }
}