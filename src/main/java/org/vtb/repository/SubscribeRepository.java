package org.vtb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vtb.entity.Subscribe;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
    Subscribe findBySubscriberAndTask(Long userId, Long taskId);
    boolean existsBySubscriberAndTask(Long userId, Long taskId);
}
