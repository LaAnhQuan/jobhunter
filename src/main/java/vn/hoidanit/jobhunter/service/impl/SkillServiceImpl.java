package vn.hoidanit.jobhunter.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.Skill;

import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.SkillRepository;
import vn.hoidanit.jobhunter.service.SkillService;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public Skill handleCreateSkill(Skill skill) throws IdInvalidException {
        boolean isNameExist = this.skillRepository.existsByName(skill.getName());
        if (isNameExist) {
            throw new IdInvalidException(
                    "Skill " + skill.getName() + " đã tồn tại, vui lòng thêm skill khác");
        }

        return this.skillRepository.save(skill);

    }

    @Override
    public Skill handleUpdateSkill(Skill skill) {
        Skill existingSkill = this.skillRepository.findById(skill.getId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy user với id = " + skill.getId()));

        if (existingSkill.getName().equals(skill.getName())) {
            throw new IllegalArgumentException("Skill name = " + skill.getName() + " đã tồn tại");
        }
        existingSkill.setName(skill.getName());
        this.skillRepository.save(existingSkill);
        return existingSkill;
    }

    @Override
    public ResultPaginationDTO handleFetchAllSkill(Specification<Skill> spec, Pageable pageable) {
        Page<Skill> pageSkill = this.skillRepository.findAll(spec, pageable);

        // Meta info
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();
        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());
        mt.setPages(pageSkill.getTotalPages());
        mt.setTotal(pageSkill.getTotalElements());

        // Response wrapper
        ResultPaginationDTO rs = new ResultPaginationDTO();
        rs.setMeta(mt);
        rs.setResult(pageSkill.getContent());
        return rs;
    }

    @Override
    public void deleteSkill(Long id) {
        // delete job (inside job_skill table)
        Optional<Skill> skillOptional = this.skillRepository.findById(id);
        Skill currentSkill = skillOptional.get();
        currentSkill.getJobs().forEach(job -> job.getSkills().remove(currentSkill));

        //delete subscriber(inside subscriber_skill table)
        currentSkill.getSubscribers().forEach(subs -> subs.getSkills().remove(currentSkill));

        // delete skill
        this.skillRepository.delete(currentSkill);
    }

    @Override
    public Skill fetchSkillById(Long id) {
        return this.skillRepository.findById(id).orElseThrow();
    }

}
