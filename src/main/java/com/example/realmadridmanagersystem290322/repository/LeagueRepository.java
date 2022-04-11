package com.example.realmadridmanagersystem290322.repository;

import com.example.realmadridmanagersystem290322.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {
}
