package com.example.clubmanagement.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clubs")
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, length = 100)
    private String name;
    
    @Column(length = 500)
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "president_id")
    private Member president;
    
    @Column(name = "founded_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date foundedDate;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "club_members",
        joinColumns = @JoinColumn(name = "club_id"),
        inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private Set<Member> members = new HashSet<>();
    
    @PrePersist
    protected void onCreate() {
        foundedDate = new Date();
    }
    
    public Club() {}
    
    public Club(String name, String description, Member president) {
        this.name = name;
        this.description = description;
        this.president = president;
        this.members.add(president);
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Member getPresident() {
        return president;
    }
    
    public void setPresident(Member president) {
        this.president = president;
    }
    
    public Date getFoundedDate() {
        return foundedDate;
    }
    
    public void setFoundedDate(Date foundedDate) {
        this.foundedDate = foundedDate;
    }
    
    public Set<Member> getMembers() {
        return members;
    }
    
    public void setMembers(Set<Member> members) {
        this.members = members;
    }
    
    public void addMember(Member member) {
        this.members.add(member);
    }
    
    public void removeMember(Member member) {
        this.members.remove(member);
    }
}