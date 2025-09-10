package vn.hoidanit.jobhunter.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import vn.hoidanit.jobhunter.domain.Job;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.domain.response.job.ResCreJobDTO;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

public interface JobService {
    ResCreJobDTO handleCreateJob(Job job);

    ResCreJobDTO handleUpdateJob(Job job, Job jobInDB) throws IdInvalidException;

    Void handleDeleteJob(Long id);

    ResultPaginationDTO handleFetchAllJob(Specification<Job> spec, Pageable pageable);

    Job handleFetchJobById(Long id) throws IdInvalidException;
}
