package com.teo15.picktimebe.gift;

import com.teo15.picktimebe.target.Target;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GiftRepository extends JpaRepository<Gift, Long> {
    Optional<Object> findByTargetId(Long targetId);
}

