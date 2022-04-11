package com.example.realmadridmanagersystem290322.repository;

import com.example.realmadridmanagersystem290322.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {

    Club findBySeason(String season);

    boolean existsBySeason(String season);
}
