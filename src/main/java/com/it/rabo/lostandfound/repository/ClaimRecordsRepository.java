package com.it.rabo.lostandfound.repository;

import com.it.rabo.lostandfound.entity.ClaimRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClaimRecordsRepository extends JpaRepository<ClaimRecord, Long> {
    Optional<ClaimRecord> findByLostFound_IdAndUserDetails_Id(Long id, Long id1);
}
