package com.knitted.marketplace.repositories;

import com.knitted.marketplace.models.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionInterface extends JpaRepository<Reaction, Long> {
}
