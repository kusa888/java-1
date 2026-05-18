package com.example.clubmanagement.repository;

import com.example.clubmanagement.entity.Activity;
import com.example.clubmanagement.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByClub(Club club);
    List<Activity> findByStatus(String status);
    List<Activity> findByNameContaining(String name);
}