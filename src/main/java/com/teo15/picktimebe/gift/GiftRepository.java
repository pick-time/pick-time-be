package com.teo15.picktimebe.gift;

import com.teo15.picktimebe.target.Target;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GiftRepository extends JpaRepository<Gift, Long> {
    @Query("SELECT g FROM Gift g WHERE g.target.id = :targetId")
    List<Gift> findByTargetId(Long targetId);
}

