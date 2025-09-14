package vn.hoidanit.jobhunter.service;

import vn.hoidanit.jobhunter.domain.Subscriber;

public interface SubscriberService {

    boolean  isExistsByEmail(String email);

    Subscriber create(Subscriber subscriber);

    Subscriber findById(long id);

    Subscriber update(Subscriber subsDB, Subscriber subsRequest);

}
