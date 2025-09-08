package vn.hoidanit.jobhunter.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import vn.hoidanit.jobhunter.domain.Job;
import vn.hoidanit.jobhunter.domain.request.ReqJobDTO;
import vn.hoidanit.jobhunter.domain.response.ResCreJobDTO;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

public interface JobService {
    ResCreJobDTO handleCreateJob(ReqJobDTO reqCreJobDTO);

    ResCreJobDTO handleUpdateJob(ReqJobDTO reqJobDTO) throws IdInvalidException;

    Void handleDeleteJob(Long id);

    ResultPaginationDTO handleFetchAllJob(Specification<Job> spec, Pageable pageable);

    Job handleFetchJobById(Long id);
}
