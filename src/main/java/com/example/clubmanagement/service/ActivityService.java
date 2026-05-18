package com.example.clubmanagement.service;

import com.example.clubmanagement.entity.Activity;
import com.example.clubmanagement.entity.Club;
import com.example.clubmanagement.entity.Member;
import com.example.clubmanagement.repository.ActivityRepository;
import com.example.clubmanagement.repository.ClubRepository;
import com.example.clubmanagement.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ActivityService {
    
    @Autowired
    private ActivityRepository activityRepository;
    
    @Autowired
    private ClubRepository clubRepository;
    
    @Autowired
    private MemberRepository memberRepository;
    
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public Activity createActivity(Long clubId, String name, String description, 
                                   String startTimeStr, String endTimeStr, String location) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("社团不存在"));
        Date startTime, endTime;
        try {
            startTime = dateFormat.parse(startTimeStr);
            endTime = dateFormat.parse(endTimeStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException("时间格式错误，应为 yyyy-MM-dd HH:mm:ss");
        }
        Activity activity = new Activity(name, description, club, startTime, endTime, location);
        return activityRepository.save(activity);
    }
    
    public Activity updateActivity(Long id, String name, String description,
                                   String startTimeStr, String endTimeStr, String location) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("活动不存在"));
        if (name != null && !name.isEmpty()) {
            activity.setName(name);
        }
        if (description != null) {
            activity.setDescription(description);
        }
        if (startTimeStr != null && !startTimeStr.isEmpty()) {
            try {
                activity.setStartTime(dateFormat.parse(startTimeStr));
            } catch (ParseException e) {
                throw new IllegalArgumentException("时间格式错误");
            }
        }
        if (endTimeStr != null && !endTimeStr.isEmpty()) {
            try {
                activity.setEndTime(dateFormat.parse(endTimeStr));
            } catch (ParseException e) {
                throw new IllegalArgumentException("时间格式错误");
            }
        }
        if (location != null) {
            activity.setLocation(location);
        }
        return activityRepository.save(activity);
    }
    
    public void deleteActivity(Long id) {
        if (!activityRepository.existsById(id)) {
            throw new IllegalArgumentException("活动不存在");
        }
        activityRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public Activity getActivityById(Long id) {
        return activityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("活动不存在"));
    }
    
    @Transactional(readOnly = true)
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<Activity> getActivitiesByClub(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("社团不存在"));
        return activityRepository.findByClub(club);
    }
    
    public Activity addParticipant(Long activityId, Long memberId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new IllegalArgumentException("活动不存在"));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("成员不存在"));
        if (activity.getParticipants().contains(member)) {
            throw new IllegalArgumentException("成员已报名该活动");
        }
        activity.addParticipant(member);
        return activityRepository.save(activity);
    }
    
    public Activity removeParticipant(Long activityId, Long memberId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new IllegalArgumentException("活动不存在"));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("成员不存在"));
        if (!activity.getParticipants().contains(member)) {
            throw new IllegalArgumentException("成员未报名该活动");
        }
        activity.removeParticipant(member);
        return activityRepository.save(activity);
    }
    
    public Activity startActivity(Long id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("活动不存在"));
        activity.setStatus("ongoing");
        return activityRepository.save(activity);
    }
    
    public Activity endActivity(Long id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("活动不存在"));
        activity.setStatus("ended");
        return activityRepository.save(activity);
    }
}