package vn.hoidanit.jobhunter.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.Job;
import vn.hoidanit.jobhunter.domain.Skill;
import vn.hoidanit.jobhunter.domain.request.ReqJobDTO;
import vn.hoidanit.jobhunter.domain.response.ResCreJobDTO;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.mapper.JobMapper;
import vn.hoidanit.jobhunter.repository.JobRepository;
import vn.hoidanit.jobhunter.repository.SkillRepository;
import vn.hoidanit.jobhunter.service.JobService;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

@Service
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final SkillRepository skillRepository;

    public JobServiceImpl(JobRepository jobRepository, SkillRepository skillRepository) {
        this.jobRepository = jobRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    public ResCreJobDTO handleCreateJob(ReqJobDTO reqJobDTO) {
        Job job = new Job();
        job.setName(reqJobDTO.getName());
        job.setLocation(reqJobDTO.getLocation());
        job.setSalary(reqJobDTO.getSalary());
        job.setQuantity(reqJobDTO.getQuantity());
        job.setLevel(reqJobDTO.getLevel());
        job.setDescription(reqJobDTO.getDescription());
        job.setStartDate(reqJobDTO.getStartDate());
        job.setEndDate(reqJobDTO.getEndDate());
        job.setActive(reqJobDTO.isActive());

        // Nếu không gửi skills thì set rỗng
        List<Skill> skills = Optional.ofNullable(reqJobDTO.getSkills())
                .orElse(List.of())
                .stream()
                .map(ReqJobDTO.SkillJob::getId)
                .map(skillRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        job.setSkills(skills);

        Job savedJob = this.jobRepository.save(job);

        return JobMapper.toResCreJobDTO(savedJob);
    }

    @Override
    public ResCreJobDTO handleUpdateJob(ReqJobDTO reqJobDTO) throws IdInvalidException {
        Job job = this.jobRepository.findById(reqJobDTO.getId())
                .orElseThrow(() -> new IdInvalidException("Job not found with id = " + reqJobDTO.getId()));

        // Cập nhật các field
        job.setName(reqJobDTO.getName());
        job.setLocation(reqJobDTO.getLocation());
        job.setSalary(reqJobDTO.getSalary());
        job.setQuantity(reqJobDTO.getQuantity());
        job.setLevel(reqJobDTO.getLevel());
        job.setDescription(reqJobDTO.getDescription());
        job.setStartDate(reqJobDTO.getStartDate());
        job.setEndDate(reqJobDTO.getEndDate());
        job.setActive(reqJobDTO.isActive());

        // Cập nhật skills (nếu có)
        List<Skill> skills = Optional.ofNullable(reqJobDTO.getSkills())
                .orElse(List.of())
                .stream()
                .map(ReqJobDTO.SkillJob::getId)
                .map(skillRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        job.setSkills(skills);

        Job savedJob = this.jobRepository.save(job);

        return JobMapper.toResCreJobDTO(savedJob);
    }

    @Override
    public Void handleDeleteJob(Long id) {
        this.jobRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id không tồn tại: " + id));
        this.jobRepository.deleteById(id);
        return null;

    }

    @Override
    public ResultPaginationDTO handleFetchAllJob(Specification<Job> spec, Pageable pageable) {
        Page<Job> pageJob = this.jobRepository.findAll(spec, pageable);
        // Meta info
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();
        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());
        mt.setPages(pageJob.getTotalPages());
        mt.setTotal(pageJob.getTotalElements());

        // Response wrapper
        ResultPaginationDTO rs = new ResultPaginationDTO();
        rs.setMeta(mt);
        rs.setResult(pageJob.getContent());
        return rs;
    }

    @Override
    public Job handleFetchJobById(Long id) {
        return this.jobRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id không tồn tại: " + id));
    }

}
