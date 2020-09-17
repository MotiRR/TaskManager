package org.vtb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vtb.entity.Subscribe;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
    Subscribe findSubscribeByUserIdAndTaskId(Long user_id, Long task_id);
    boolean existsSubscribeByUserIdAndTaskId(Long user_id, Long task_id);
}
