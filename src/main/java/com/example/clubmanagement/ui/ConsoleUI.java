package com.example.clubmanagement.ui;

import com.example.clubmanagement.entity.Activity;
import com.example.clubmanagement.entity.Club;
import com.example.clubmanagement.entity.Member;
import com.example.clubmanagement.service.ActivityService;
import com.example.clubmanagement.service.ClubService;
import com.example.clubmanagement.service.MemberService;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private Scanner scanner = new Scanner(System.in);
    private ClubService clubService = new ClubService();
    private MemberService memberService = new MemberService();
    private ActivityService activityService = new ActivityService();
    
    public void start() {
        while (true) {
            printMainMenu();
            int choice = getIntInput("请输入您的选择: ");
            switch (choice) {
                case 1:
                    memberManagement();
                    break;
                case 2:
                    clubManagement();
                    break;
                case 3:
                    activityManagement();
                    break;
                case 4:
                    System.out.println("感谢使用社团管理系统，再见！");
                    return;
                default:
                    System.out.println("无效的选择，请重新输入");
            }
        }
    }
    
    private void printMainMenu() {
        System.out.println("\n========== 社团管理系统 ==========");
        System.out.println("1. 成员管理");
        System.out.println("2. 社团管理");
        System.out.println("3. 活动管理");
        System.out.println("4. 退出系统");
        System.out.println("==================================");
    }
    
    private void memberManagement() {
        while (true) {
            System.out.println("\n========== 成员管理 ==========");
            System.out.println("1. 添加成员");
            System.out.println("2. 修改成员信息");
            System.out.println("3. 删除成员");
            System.out.println("4. 查看所有成员");
            System.out.println("5. 查询成员");
            System.out.println("6. 注销/激活成员");
            System.out.println("7. 返回主菜单");
            System.out.println("==============================");
            
            int choice = getIntInput("请输入您的选择: ");
            switch (choice) {
                case 1:
                    addMember();
                    break;
                case 2:
                    updateMember();
                    break;
                case 3:
                    deleteMember();
                    break;
                case 4:
                    listAllMembers();
                    break;
                case 5:
                    searchMember();
                    break;
                case 6:
                    toggleMemberStatus();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("无效的选择，请重新输入");
            }
        }
    }
    
    private void addMember() {
        System.out.println("\n========== 添加成员 ==========");
        String name = getInput("请输入姓名: ");
        String studentId = getInput("请输入学号: ");
        String phone = getInput("请输入手机号: ");
        String email = getInput("请输入邮箱: ");
        String department = getInput("请输入院系: ");
        
        Member member = memberService.createMember(name, studentId, phone, email, department);
        System.out.println("添加成功，成员ID: " + member.getId());
    }
    
    private void updateMember() {
        System.out.println("\n========== 修改成员信息 ==========");
        Long id = Long.parseLong(getInput("请输入成员ID: "));
        String name = getInput("请输入新姓名(回车跳过): ");
        String phone = getInput("请输入新手机号(回车跳过): ");
        String email = getInput("请输入新邮箱(回车跳过): ");
        String department = getInput("请输入新院系(回车跳过): ");
        
        Member member = memberService.updateMember(id, 
                name.isEmpty() ? null : name,
                phone.isEmpty() ? null : phone,
                email.isEmpty() ? null : email,
                department.isEmpty() ? null : department);
        System.out.println("修改成功");
    }
    
    private void deleteMember() {
        System.out.println("\n========== 删除成员 ==========");
        Long id = Long.parseLong(getInput("请输入成员ID: "));
        memberService.deleteMember(id);
        System.out.println("删除成功");
    }
    
    private void listAllMembers() {
        System.out.println("\n========== 所有成员 ==========");
        List<Member> members = memberService.getAllMembers();
        if (members.isEmpty()) {
            System.out.println("暂无成员");
            return;
        }
        
        System.out.printf("%-10s %-15s %-15s %-15s %-20s %-15s %-10s\n", 
                "ID", "姓名", "学号", "手机号", "邮箱", "院系", "状态");
        for (Member m : members) {
            System.out.printf("%-10s %-15s %-15s %-15s %-20s %-15s %-10s\n", 
                    m.getId(), m.getName(), m.getStudentId(), m.getPhone(), 
                    m.getEmail(), m.getDepartment(), m.getStatus());
        }
    }
    
    private void searchMember() {
        System.out.println("\n========== 查询成员 ==========");
        System.out.println("1. 按ID查询");
        System.out.println("2. 按学号查询");
        int choice = getIntInput("请输入查询方式: ");
        
        Member member = null;
        if (choice == 1) {
            Long id = Long.parseLong(getInput("请输入成员ID: "));
            member = memberService.getMemberById(id);
        } else if (choice == 2) {
            String studentId = getInput("请输入学号: ");
            member = memberService.getMemberByStudentId(studentId);
        }
        
        if (member != null) {
            System.out.println("\n成员信息:");
            System.out.println("ID: " + member.getId());
            System.out.println("姓名: " + member.getName());
            System.out.println("学号: " + member.getStudentId());
            System.out.println("手机号: " + member.getPhone());
            System.out.println("邮箱: " + member.getEmail());
            System.out.println("院系: " + member.getDepartment());
            System.out.println("加入日期: " + member.getJoinDate());
            System.out.println("状态: " + member.getStatus());
        } else {
            System.out.println("未找到该成员");
        }
    }
    
    private void toggleMemberStatus() {
        System.out.println("\n========== 成员状态管理 ==========");
        Long id = Long.parseLong(getInput("请输入成员ID: "));
        System.out.println("1. 注销成员");
        System.out.println("2. 激活成员");
        int choice = getIntInput("请输入选择: ");
        
        if (choice == 1) {
            memberService.deactivateMember(id);
        } else {
            memberService.activateMember(id);
        }
        System.out.println("操作成功");
    }
    
    private void clubManagement() {
        while (true) {
            System.out.println("\n========== 社团管理 ==========");
            System.out.println("1. 创建社团");
            System.out.println("2. 修改社团信息");
            System.out.println("3. 删除社团");
            System.out.println("4. 查看所有社团");
            System.out.println("5. 查询社团");
            System.out.println("6. 添加成员到社团");
            System.out.println("7. 从社团移除成员");
            System.out.println("8. 更换社长");
            System.out.println("9. 返回主菜单");
            System.out.println("==============================");
            
            int choice = getIntInput("请输入您的选择: ");
            switch (choice) {
                case 1:
                    createClub();
                    break;
                case 2:
                    updateClub();
                    break;
                case 3:
                    deleteClub();
                    break;
                case 4:
                    listAllClubs();
                    break;
                case 5:
                    searchClub();
                    break;
                case 6:
                    addMemberToClub();
                    break;
                case 7:
                    removeMemberFromClub();
                    break;
                case 8:
                    changePresident();
                    break;
                case 9:
                    return;
                default:
                    System.out.println("无效的选择，请重新输入");
            }
        }
    }
    
    private void createClub() {
        System.out.println("\n========== 创建社团 ==========");
        String name = getInput("请输入社团名称: ");
        String description = getInput("请输入社团描述: ");
        Long presidentId = Long.parseLong(getInput("请输入社长ID: "));
        
        Club club = clubService.createClub(name, description, presidentId);
        System.out.println("创建成功，社团ID: " + club.getId());
    }
    
    private void updateClub() {
        System.out.println("\n========== 修改社团信息 ==========");
        Long id = Long.parseLong(getInput("请输入社团ID: "));
        String name = getInput("请输入新名称(回车跳过): ");
        String description = getInput("请输入新描述(回车跳过): ");
        
        Club club = clubService.updateClub(id, 
                name.isEmpty() ? null : name,
                description.isEmpty() ? null : description);
        System.out.println("修改成功");
    }
    
    private void deleteClub() {
        System.out.println("\n========== 删除社团 ==========");
        Long id = Long.parseLong(getInput("请输入社团ID: "));
        clubService.deleteClub(id);
        System.out.println("删除成功");
    }
    
    private void listAllClubs() {
        System.out.println("\n========== 所有社团 ==========");
        List<Club> clubs = clubService.getAllClubs();
        if (clubs.isEmpty()) {
            System.out.println("暂无社团");
            return;
        }
        
        System.out.printf("%-10s %-20s %-30s %-15s %-10s\n", 
                "ID", "名称", "描述", "社长ID", "成员数");
        for (Club c : clubs) {
            Member president = c.getPresident();
            System.out.printf("%-10s %-20s %-30s %-15s %-10d\n", 
                    c.getId(), c.getName(), 
                    c.getDescription().length() > 28 ? c.getDescription().substring(0, 28) + "..." : c.getDescription(),
                    president != null ? president.getId() : null, c.getMembers().size());
        }
    }
    
    private void searchClub() {
        System.out.println("\n========== 查询社团 ==========");
        Long id = Long.parseLong(getInput("请输入社团ID: "));
        Club club = clubService.getClubById(id);
        
        if (club != null) {
            System.out.println("\n社团信息:");
            System.out.println("ID: " + club.getId());
            System.out.println("名称: " + club.getName());
            System.out.println("描述: " + club.getDescription());
            
            Member president = club.getPresident();
            System.out.println("社长: " + (president != null ? president.getName() : "未知"));
            System.out.println("成立日期: " + club.getFoundedDate());
            System.out.println("成员数: " + club.getMembers().size());
        } else {
            System.out.println("未找到该社团");
        }
    }
    
    private void addMemberToClub() {
        System.out.println("\n========== 添加成员到社团 ==========");
        Long clubId = Long.parseLong(getInput("请输入社团ID: "));
        Long memberId = Long.parseLong(getInput("请输入成员ID: "));
        
        clubService.addMemberToClub(clubId, memberId);
        System.out.println("添加成功");
    }
    
    private void removeMemberFromClub() {
        System.out.println("\n========== 从社团移除成员 ==========");
        Long clubId = Long.parseLong(getInput("请输入社团ID: "));
        Long memberId = Long.parseLong(getInput("请输入成员ID: "));
        
        clubService.removeMemberFromClub(clubId, memberId);
        System.out.println("移除成功");
    }
    
    private void changePresident() {
        System.out.println("\n========== 更换社长 ==========");
        Long clubId = Long.parseLong(getInput("请输入社团ID: "));
        Long newPresidentId = Long.parseLong(getInput("请输入新社长ID: "));
        
        clubService.changePresident(clubId, newPresidentId);
        System.out.println("更换成功");
    }
    
    private void activityManagement() {
        while (true) {
            System.out.println("\n========== 活动管理 ==========");
            System.out.println("1. 创建活动");
            System.out.println("2. 修改活动信息");
            System.out.println("3. 删除活动");
            System.out.println("4. 查看所有活动");
            System.out.println("5. 查询活动");
            System.out.println("6. 报名活动");
            System.out.println("7. 取消报名");
            System.out.println("8. 开始/结束活动");
            System.out.println("9. 返回主菜单");
            System.out.println("==============================");
            
            int choice = getIntInput("请输入您的选择: ");
            switch (choice) {
                case 1:
                    createActivity();
                    break;
                case 2:
                    updateActivity();
                    break;
                case 3:
                    deleteActivity();
                    break;
                case 4:
                    listAllActivities();
                    break;
                case 5:
                    searchActivity();
                    break;
                case 6:
                    signUpActivity();
                    break;
                case 7:
                    cancelSignUp();
                    break;
                case 8:
                    toggleActivityStatus();
                    break;
                case 9:
                    return;
                default:
                    System.out.println("无效的选择，请重新输入");
            }
        }
    }
    
    private void createActivity() {
        System.out.println("\n========== 创建活动 ==========");
        Long clubId = Long.parseLong(getInput("请输入所属社团ID: "));
        String name = getInput("请输入活动名称: ");
        String description = getInput("请输入活动描述: ");
        String startTime = getInput("请输入开始时间(yyyy-MM-dd HH:mm:ss): ");
        String endTime = getInput("请输入结束时间(yyyy-MM-dd HH:mm:ss): ");
        String location = getInput("请输入活动地点: ");
        
        Activity activity = activityService.createActivity(clubId, name, description, startTime, endTime, location);
        System.out.println("创建成功，活动ID: " + activity.getId());
    }
    
    private void updateActivity() {
        System.out.println("\n========== 修改活动信息 ==========");
        Long id = Long.parseLong(getInput("请输入活动ID: "));
        String name = getInput("请输入新名称(回车跳过): ");
        String description = getInput("请输入新描述(回车跳过): ");
        String startTime = getInput("请输入新开始时间(回车跳过): ");
        String endTime = getInput("请输入新结束时间(回车跳过): ");
        String location = getInput("请输入新地点(回车跳过): ");
        
        Activity activity = activityService.updateActivity(id,
                name.isEmpty() ? null : name,
                description.isEmpty() ? null : description,
                startTime.isEmpty() ? null : startTime,
                endTime.isEmpty() ? null : endTime,
                location.isEmpty() ? null : location);
        System.out.println("修改成功");
    }
    
    private void deleteActivity() {
        System.out.println("\n========== 删除活动 ==========");
        Long id = Long.parseLong(getInput("请输入活动ID: "));
        activityService.deleteActivity(id);
        System.out.println("删除成功");
    }
    
    private void listAllActivities() {
        System.out.println("\n========== 所有活动 ==========");
        List<Activity> activities = activityService.getAllActivities();
        if (activities.isEmpty()) {
            System.out.println("暂无活动");
            return;
        }
        
        System.out.printf("%-10s %-20s %-15s %-20s %-15s %-10s\n", 
                "ID", "名称", "社团ID", "开始时间", "地点", "状态");
        for (Activity a : activities) {
            Club club = a.getClub();
            System.out.printf("%-10s %-20s %-15s %-20s %-15s %-10s\n", 
                    a.getId(), a.getName(), club != null ? club.getId() : null, 
                    a.getStartTime(), a.getLocation(), a.getStatus());
        }
    }
    
    private void searchActivity() {
        System.out.println("\n========== 查询活动 ==========");
        Long id = Long.parseLong(getInput("请输入活动ID: "));
        Activity activity = activityService.getActivityById(id);
        
        if (activity != null) {
            System.out.println("\n活动信息:");
            System.out.println("ID: " + activity.getId());
            System.out.println("名称: " + activity.getName());
            System.out.println("描述: " + activity.getDescription());
            
            Club club = activity.getClub();
            System.out.println("所属社团: " + (club != null ? club.getName() : "未知"));
            System.out.println("开始时间: " + activity.getStartTime());
            System.out.println("结束时间: " + activity.getEndTime());
            System.out.println("地点: " + activity.getLocation());
            System.out.println("状态: " + activity.getStatus());
            System.out.println("参与人数: " + activity.getParticipants().size());
        } else {
            System.out.println("未找到该活动");
        }
    }
    
    private void signUpActivity() {
        System.out.println("\n========== 报名活动 ==========");
        Long activityId = Long.parseLong(getInput("请输入活动ID: "));
        Long memberId = Long.parseLong(getInput("请输入成员ID: "));
        
        activityService.addParticipant(activityId, memberId);
        System.out.println("报名成功");
    }
    
    private void cancelSignUp() {
        System.out.println("\n========== 取消报名 ==========");
        Long activityId = Long.parseLong(getInput("请输入活动ID: "));
        Long memberId = Long.parseLong(getInput("请输入成员ID: "));
        
        activityService.removeParticipant(activityId, memberId);
        System.out.println("取消成功");
    }
    
    private void toggleActivityStatus() {
        System.out.println("\n========== 活动状态管理 ==========");
        Long id = Long.parseLong(getInput("请输入活动ID: "));
        System.out.println("1. 开始活动");
        System.out.println("2. 结束活动");
        int choice = getIntInput("请输入选择: ");
        
        if (choice == 1) {
            activityService.startActivity(id);
        } else {
            activityService.endActivity(id);
        }
        System.out.println("操作成功");
    }
    
    private String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("请输入有效的数字");
            }
        }
    }
}