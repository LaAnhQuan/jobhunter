package vn.hoidanit.jobhunter.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.dto.ResultPaginationDTO;

public interface CompanyService {

    Company handleCreateCompany(Company company);

    ResultPaginationDTO handleFetchAllCompany(Specification<Company> spec, Pageable pageable);

    Company handleUpdateCompany(Company company);

    void handleDeleteCompany(Long id);

}
