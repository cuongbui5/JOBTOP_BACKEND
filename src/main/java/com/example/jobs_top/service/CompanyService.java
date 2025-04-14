package com.example.jobs_top.service;

import com.example.jobs_top.model.Company;
import com.example.jobs_top.model.Account;
import com.example.jobs_top.repository.CompanyRepository;
import com.example.jobs_top.utils.Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company createCompany(Company company) {
        Account account=Utils.getAccount();
        company.setAccount(account);
        return companyRepository.save(company);
    }


    @Transactional
    public Company updateCompany(Long id,Company company) {
        Company companyUpdated = companyRepository.findById(id).orElseThrow(()->new RuntimeException("Not found company"));
        companyUpdated.setLogo(company.getLogo());
        companyUpdated.setName(company.getName());
        companyUpdated.setDescription(company.getDescription());
        companyUpdated.setAddress(company.getAddress());
        companyUpdated.setNation(company.getNation());
        companyUpdated.setSize(company.getSize());
        companyUpdated.setWebsite(company.getWebsite());
        companyUpdated.setCategory(company.getCategory());
        companyUpdated.setFoundedDate(company.getFoundedDate());
        return companyRepository.save(company);
    }


    @Transactional
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Company not found with id: " + id));
    }

    public Company getCompanyByAccount(Account account) {
        return companyRepository.findByAccountId(account.getId()).orElseThrow(()->new RuntimeException("Not fount company"));
    }


    @Transactional
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }







}
