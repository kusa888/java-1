package com.example.clubmanagement.dao;

import com.example.clubmanagement.entity.Club;
import com.example.clubmanagement.util.DataFileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClubDAO {
    private static final String FILE_NAME = "clubs.dat";
    
    public void saveClub(Club club) {
        List<Club> clubs = getAllClubs();
        int index = -1;
        for (int i = 0; i < clubs.size(); i++) {
            if (clubs.get(i).getId().equals(club.getId())) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            clubs.set(index, club);
        } else {
            clubs.add(club);
        }
        DataFileUtil.writeListToFile(clubs, FILE_NAME);
    }
    
    public Club getClubById(Long id) {
        List<Club> clubs = getAllClubs();
        return clubs.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    public Club getClubByName(String name) {
        List<Club> clubs = getAllClubs();
        return clubs.stream()
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
    
    public List<Club> getAllClubs() {
        List<Club> clubs = DataFileUtil.readListFromFile(FILE_NAME);
        return clubs != null ? clubs : new ArrayList<>();
    }
    
    public boolean deleteClub(Long id) {
        List<Club> clubs = getAllClubs();
        List<Club> filtered = clubs.stream()
                .filter(c -> !c.getId().equals(id))
                .collect(Collectors.toList());
        if (filtered.size() < clubs.size()) {
            DataFileUtil.writeListToFile(filtered, FILE_NAME);
            return true;
        }
        return false;
    }
}