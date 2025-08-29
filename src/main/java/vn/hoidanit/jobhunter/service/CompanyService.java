package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.repository.CompanyRepository;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company handleCreateCompany(Company company) {
        return this.companyRepository.save(company);
    }

    public List<Company> handleFetchAllCompany() {
        return this.companyRepository.findAll();
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
