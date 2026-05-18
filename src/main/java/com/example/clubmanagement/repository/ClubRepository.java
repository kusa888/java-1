package com.example.clubmanagement.repository;

import com.example.clubmanagement.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
    Optional<Club> findByName(String name);
    List<Club> findByNameContaining(String name);
    boolean existsByName(String name);
}