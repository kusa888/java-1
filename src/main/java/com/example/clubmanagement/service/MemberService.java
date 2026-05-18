package com.example.clubmanagement.service;

import com.example.clubmanagement.entity.Member;
import com.example.clubmanagement.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberService {
    
    @Autowired
    private MemberRepository memberRepository;
    
    public Member createMember(String name, String studentId, String phone, String email, String department) {
        if (memberRepository.existsByStudentId(studentId)) {
            throw new IllegalArgumentException("学号已存在");
        }
        Member member = new Member(name, studentId, phone, email, department);
        return memberRepository.save(member);
    }
    
    public Member updateMember(Long id, String name, String phone, String email, String department) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("成员不存在"));
        if (name != null && !name.isEmpty()) {
            member.setName(name);
        }
        if (phone != null) {
            member.setPhone(phone);
        }
        if (email != null) {
            member.setEmail(email);
        }
        if (department != null) {
            member.setDepartment(department);
        }
        return memberRepository.save(member);
    }
    
    public void deleteMember(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new IllegalArgumentException("成员不存在");
        }
        memberRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("成员不存在"));
    }
    
    @Transactional(readOnly = true)
    public Member getMemberByStudentId(String studentId) {
        return memberRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("成员不存在"));
    }
    
    @Transactional(readOnly = true)
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<Member> getActiveMembers() {
        return memberRepository.findByStatus("active");
    }
    
    public Member deactivateMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("成员不存在"));
        member.setStatus("inactive");
        return memberRepository.save(member);
    }
    
    public Member activateMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("成员不存在"));
        member.setStatus("active");
        return memberRepository.save(member);
    }
}