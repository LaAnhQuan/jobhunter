package vn.hoidanit.jobhunter.service;

import org.springframework.data.domain.Pageable;

import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.dto.ResultPaginationDTO;

public interface CompanyService {

    Company handleCreateCompany(Company company);

    ResultPaginationDTO handleFetchAllCompany(Pageable pageable);

    Company handleUpdateCompany(Company company);

    void handleDeleteCompany(Long id);

}
