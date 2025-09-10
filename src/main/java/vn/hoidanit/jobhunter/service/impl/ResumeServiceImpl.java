package vn.hoidanit.jobhunter.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.Job;
import vn.hoidanit.jobhunter.domain.Resume;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.domain.response.resume.ResCreResumeDTO;
import vn.hoidanit.jobhunter.domain.response.resume.ResFetchResumeDTO;
import vn.hoidanit.jobhunter.domain.response.resume.ResUpResumeDTO;
import vn.hoidanit.jobhunter.mapper.ResumeMapper;
import vn.hoidanit.jobhunter.repository.JobRepository;
import vn.hoidanit.jobhunter.repository.ResumeRepository;
import vn.hoidanit.jobhunter.repository.UserRepository;
import vn.hoidanit.jobhunter.service.ResumeService;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

@Service
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public ResumeServiceImpl(ResumeRepository resumeRepository,
            JobRepository jobRepository,
            UserRepository userRepository) {
        this.resumeRepository = resumeRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResCreResumeDTO createResume(Resume resume) throws IdInvalidException {
        if (resume.getUser() == null || resume.getJob() == null) {
            throw new IdInvalidException("User và Job không được để trống");
        }

        User user = this.userRepository.findById(resume.getUser().getId())
                .orElseThrow(() -> new IdInvalidException("User id/Job id không tồn tại"));
        Job job = this.jobRepository.findById(resume.getJob().getId())
                .orElseThrow(() -> new IdInvalidException("User id/Job id không tồn tại"));

        resume.setUser(user);
        resume.setJob(job);

        this.resumeRepository.save(resume);
        return ResumeMapper.toResCreResumeDTO(resume);
    }

    @Override
    public ResUpResumeDTO update(Resume resume) {
        resume = this.resumeRepository.save(resume);
        ResUpResumeDTO res = new ResUpResumeDTO();
        res.setUpdatedAt(resume.getUpdatedAt());
        res.setUpdatedBy(resume.getUpdatedBy());
        return res;
    }

    @Override
    public Optional<Resume> fetchById(long id) {
        return this.resumeRepository.findById(id);
    }

    @Override
    public void delete(long id) {
        this.resumeRepository.deleteById(id);
    }

    @Override
    public ResFetchResumeDTO getResume(Resume resume) {
        ResFetchResumeDTO res = new ResFetchResumeDTO();

        res.setId(resume.getId());
        res.setEmail(resume.getEmail());
        res.setUrl(resume.getUrl());
        res.setStatus(resume.getStatus());
        res.setCreatedAt(resume.getCreatedAt());
        res.setCreatedBy(resume.getCreatedBy());
        res.setUpdatedAt(resume.getUpdatedAt());
        res.setUpdatedBy(resume.getUpdatedBy());

        if (resume.getJob() != null) {
            res.setCompanyName(resume.getJob().getCompany().getName());
        }

        res.setUser(new ResFetchResumeDTO.UserResume(
                resume.getUser().getId(),
                resume.getUser().getName()));

        res.setJob(new ResFetchResumeDTO.JobResume(
                resume.getJob().getId(),
                resume.getJob().getName()));

        return res;
    }

    @Override
    public ResultPaginationDTO fetchAllResume(Specification<Resume> spec, Pageable pageable) {
        Page<Resume> pageUser = this.resumeRepository.findAll(spec, pageable);

        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());
        mt.setPages(pageUser.getTotalPages());
        mt.setTotal(pageUser.getTotalElements());

        rs.setMeta(mt);

        // remove sensitive data
        List<ResFetchResumeDTO> listResume = pageUser.getContent()
                .stream()
                .map(this::getResume)
                .collect(Collectors.toList());

        rs.setResult(listResume);
        return rs;
    }
}
