package com.it.rabo.lostandfound.repository;

import com.it.rabo.lostandfound.entity.LostFound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LostAndFoundRepository extends JpaRepository<LostFound, Long> {
}
