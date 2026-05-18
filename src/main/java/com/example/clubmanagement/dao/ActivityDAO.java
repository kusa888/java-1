package com.example.clubmanagement.dao;

import com.example.clubmanagement.entity.Activity;
import com.example.clubmanagement.util.DataFileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ActivityDAO {
    private static final String FILE_NAME = "activities.dat";
    
    public void saveActivity(Activity activity) {
        List<Activity> activities = getAllActivities();
        int index = -1;
        for (int i = 0; i < activities.size(); i++) {
            if (activities.get(i).getId().equals(activity.getId())) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            activities.set(index, activity);
        } else {
            activities.add(activity);
        }
        DataFileUtil.writeListToFile(activities, FILE_NAME);
    }
    
    public Activity getActivityById(Long id) {
        List<Activity> activities = getAllActivities();
        return activities.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    public List<Activity> getAllActivities() {
        List<Activity> activities = DataFileUtil.readListFromFile(FILE_NAME);
        return activities != null ? activities : new ArrayList<>();
    }
    
    public List<Activity> getActivitiesByClubId(Long clubId) {
        List<Activity> activities = getAllActivities();
        return activities.stream()
                .filter(a -> a.getClub() != null && a.getClub().getId().equals(clubId))
                .collect(Collectors.toList());
    }
    
    public boolean deleteActivity(Long id) {
        List<Activity> activities = getAllActivities();
        List<Activity> filtered = activities.stream()
                .filter(a -> !a.getId().equals(id))
                .collect(Collectors.toList());
        if (filtered.size() < activities.size()) {
            DataFileUtil.writeListToFile(filtered, FILE_NAME);
            return true;
        }
        return false;
    }
}