package vn.hoidanit.jobhunter.service.impl;

import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.Skill;
import vn.hoidanit.jobhunter.domain.Subscriber;
import vn.hoidanit.jobhunter.repository.SkillRepository;
import vn.hoidanit.jobhunter.repository.SubscriberRepository;
import vn.hoidanit.jobhunter.service.SubscriberService;

import java.util.List;
import java.util.LongSummaryStatistics;


@Service
public class SubscriberServiceImpl implements SubscriberService {
    private  final SubscriberRepository subscriberRepository;
    private final SkillRepository skillRepository;

    public SubscriberServiceImpl(SubscriberRepository subscriberRepository, SkillRepository skillRepository) {
        this.subscriberRepository = subscriberRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    public  boolean  isExistsByEmail(String email){
return  this.subscriberRepository.existsByEmail(email);
    }

    @Override
    public Subscriber create(Subscriber subscriber) {
        //check skills
        if(subscriber.getSkills() != null){
            List<Long> reqSkills = subscriber
                    .getSkills()
                    .stream()
                    .map(Skill::getId)
                    .toList();
            List<Skill> dbSkills = this.skillRepository.findByIdIn(reqSkills);
            subscriber.setSkills(dbSkills);
        }
        return  this.subscriberRepository.save(subscriber);

    }

    @Override
    public Subscriber findById(long id) {
        return this.findById(id);
    }

    @Override
    public Subscriber update(Subscriber subsDB, Subscriber subsRequest) {
        // check Skill
        if(subsRequest.getSkills() != null){
            List<Long> reqSkills = subsRequest
                    .getSkills()
                    .stream()
                    .map(Skill::getId)
                    .toList();
            List<Skill> dbSkills = this.skillRepository.findByIdIn(reqSkills);
            subsDB.setSkills(dbSkills);
        }
        return this.subscriberRepository.save(subsDB);
    }
}