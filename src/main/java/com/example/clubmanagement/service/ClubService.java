package com.example.clubmanagement.service;

import com.example.clubmanagement.entity.Club;
import com.example.clubmanagement.entity.Member;
import com.example.clubmanagement.repository.ClubRepository;
import com.example.clubmanagement.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClubService {
    
    @Autowired
    private ClubRepository clubRepository;
    
    @Autowired
    private MemberRepository memberRepository;
    
    public Club createClub(String name, String description, Long presidentId) {
        if (clubRepository.existsByName(name)) {
            throw new IllegalArgumentException("社团名称已存在");
        }
        Member president = memberRepository.findById(presidentId)
                .orElseThrow(() -> new IllegalArgumentException("社长不存在"));
        Club club = new Club(name, description, president);
        return clubRepository.save(club);
    }
    
    public Club updateClub(Long id, String name, String description) {
        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("社团不存在"));
        if (name != null && !name.isEmpty()) {
            if (!club.getName().equals(name) && clubRepository.existsByName(name)) {
                throw new IllegalArgumentException("社团名称已存在");
            }
            club.setName(name);
        }
        if (description != null) {
            club.setDescription(description);
        }
        return clubRepository.save(club);
    }
    
    public void deleteClub(Long id) {
        if (!clubRepository.existsById(id)) {
            throw new IllegalArgumentException("社团不存在");
        }
        clubRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public Club getClubById(Long id) {
        return clubRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("社团不存在"));
    }
    
    @Transactional(readOnly = true)
    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }
    
    public Club addMemberToClub(Long clubId, Long memberId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("社团不存在"));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("成员不存在"));
        if (club.getMembers().contains(member)) {
            throw new IllegalArgumentException("成员已在该社团中");
        }
        club.addMember(member);
        return clubRepository.save(club);
    }
    
    public Club removeMemberFromClub(Long clubId, Long memberId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("社团不存在"));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("成员不存在"));
        if (!club.getMembers().contains(member)) {
            throw new IllegalArgumentException("成员不在该社团中");
        }
        if (club.getPresident().getId().equals(memberId)) {
            throw new IllegalArgumentException("不能移除社长");
        }
        club.removeMember(member);
        return clubRepository.save(club);
    }
    
    public Club changePresident(Long clubId, Long newPresidentId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("社团不存在"));
        Member newPresident = memberRepository.findById(newPresidentId)
                .orElseThrow(() -> new IllegalArgumentException("新社长不存在"));
        if (!club.getMembers().contains(newPresident)) {
            throw new IllegalArgumentException("新社长必须是社团成员");
        }
        club.setPresident(newPresident);
        return clubRepository.save(club);
    }
}