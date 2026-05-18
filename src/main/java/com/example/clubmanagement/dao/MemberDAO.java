package com.example.clubmanagement.dao;

import com.example.clubmanagement.entity.Member;
import com.example.clubmanagement.util.DataFileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MemberDAO {
    private static final String FILE_NAME = "members.dat";
    
    public void saveMember(Member member) {
        List<Member> members = getAllMembers();
        int index = -1;
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getId().equals(member.getId())) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            members.set(index, member);
        } else {
            members.add(member);
        }
        DataFileUtil.writeListToFile(members, FILE_NAME);
    }
    
    public Member getMemberById(Long id) {
        List<Member> members = getAllMembers();
        return members.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    public Member getMemberByStudentId(String studentId) {
        List<Member> members = getAllMembers();
        return members.stream()
                .filter(m -> m.getStudentId().equals(studentId))
                .findFirst()
                .orElse(null);
    }
    
    public List<Member> getAllMembers() {
        List<Member> members = DataFileUtil.readListFromFile(FILE_NAME);
        return members != null ? members : new ArrayList<>();
    }
    
    public List<Member> getActiveMembers() {
        List<Member> members = getAllMembers();
        return members.stream()
                .filter(m -> "active".equals(m.getStatus()))
                .collect(Collectors.toList());
    }
    
    public boolean deleteMember(Long id) {
        List<Member> members = getAllMembers();
        List<Member> filtered = members.stream()
                .filter(m -> !m.getId().equals(id))
                .collect(Collectors.toList());
        if (filtered.size() < members.size()) {
            DataFileUtil.writeListToFile(filtered, FILE_NAME);
            return true;
        }
        return false;
    }
}