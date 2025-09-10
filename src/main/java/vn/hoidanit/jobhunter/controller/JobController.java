package vn.hoidanit.jobhunter.controller;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.domain.Job;
import vn.hoidanit.jobhunter.domain.request.ReqJobDTO;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.domain.response.job.ResCreJobDTO;
import vn.hoidanit.jobhunter.service.JobService;
import vn.hoidanit.jobhunter.util.annotation.ApiMessage;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

@RestController
@RequestMapping("/api/v1")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("/jobs")
    @ApiMessage("Create a new job")
    public ResponseEntity<ResCreJobDTO> createNewJob(@Valid @RequestBody Job job) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.jobService.handleCreateJob(job));
    }

    @PutMapping("/jobs")
    @ApiMessage("Update a  job")
    public ResponseEntity<ResCreJobDTO> updateJob(@Valid @RequestBody Job job) throws IdInvalidException {
        Job currentJob = this.jobService.handleFetchJobById(job.getId());
        if (currentJob != null) {
            throw new IdInvalidException("Job not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(this.jobService.handleUpdateJob(job, currentJob.get()));
    }

    @DeleteMapping("/jobs/{id}")
    @ApiMessage("Delete a job")
    public ResponseEntity<Void> deleteJob(@PathVariable("id") long id) {
        this.jobService.handleDeleteJob(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/jobs")
    @ApiMessage("Get all jobs")
    public ResponseEntity<ResultPaginationDTO> getAllJob(@Filter Specification<Job> spec,
            Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(this.jobService.handleFetchAllJob(spec, pageable));
    }

    @GetMapping("/jobs/{id}")
    @ApiMessage("Get a jobs by id")
    public ResponseEntity<Job> getAllJob(@PathVariable("id") long id) throws IdInvalidException {
        return ResponseEntity.status(HttpStatus.OK).body(this.jobService.handleFetchJobById(id));
    }
}
