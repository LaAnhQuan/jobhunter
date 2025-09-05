package vn.hoidanit.jobhunter.service.impl;

import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.CompanyRepository;
import vn.hoidanit.jobhunter.service.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company handleCreateCompany(Company company) {
        return this.companyRepository.save(company);
    }

    public ResultPaginationDTO handleFetchAllCompany(Specification<Company> spec, Pageable pageable) {

        Page<Company> pageCompany = this.companyRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(pageCompany.getTotalPages());
        mt.setTotal(pageCompany.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(pageCompany.getContent());
        return rs;
    }

    public Company handleUpdateCompany(Company company) {
        return companyRepository.findById(company.getId()).map(c -> {
            c.setName(company.getName());
            c.setDescription(company.getDescription());
            c.setAddress(company.getAddress());
            c.setLogo(company.getLogo());
            return companyRepository.save(c);
        }).orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    public void handleDeleteCompany(Long id) {
        if (!companyRepository.existsById(id)) {
            throw new NoSuchElementException("Company not found");
        }
        companyRepository.deleteById(id);
    }
}
