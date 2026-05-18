package com.example.clubmanagement.repository;

import com.example.clubmanagement.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByStudentId(String studentId);
    List<Member> findByStatus(String status);
    List<Member> findByNameContaining(String name);
    boolean existsByStudentId(String studentId);
}