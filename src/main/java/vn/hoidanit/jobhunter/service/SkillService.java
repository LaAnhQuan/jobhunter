package vn.hoidanit.jobhunter.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import vn.hoidanit.jobhunter.domain.Skill;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

public interface SkillService {

    ResultPaginationDTO handleFetchAllSkill(Specification<Skill> spec, Pageable pageable);

    Skill handleCreateSkill(Skill skill) throws IdInvalidException;

    Skill handleUpdateSkill(Skill skill);

    void deleteSkill(Long id);

    Skill fetchSkillById(Long id);
}
