package org.vtb.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.vtb.entity.Subscribe;
import org.vtb.repository.SubscribeRepository;

@Service
@AllArgsConstructor
public class SubscribeService {

    private SubscribeRepository subscribeRepository;

    public Subscribe createSubscribe(Long userId, Long taskId) {
        Subscribe subscribe = new Subscribe();
        subscribe.setSubscriber(userId);
        subscribe.setTask(taskId);
        subscribe.setIsSubscribe(true);
        return saveOrUpdate(subscribe);
    }

    public Subscribe saveOrUpdate(Subscribe subscribe) {
        return subscribeRepository.save(subscribe);
    }

    public Subscribe findSubscribeByUserIdAndTaskId(Long userId, Long taskId) {
        return subscribeRepository.findBySubscriberAndTask(userId, taskId);
    }

    public boolean existsSubscribeByUserIdAndTaskId(Long userId, Long taskId) {
        return subscribeRepository.existsBySubscriberAndTask(userId, taskId);
    }
}
