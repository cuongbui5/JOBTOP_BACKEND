package com.example.jobs_top.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "conversations",
        uniqueConstraints = @UniqueConstraint(columnNames = {"account_id", "company_id"})

)
public class Conversation extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id",referencedColumnName = "id")
    @JsonIgnore
    private Account account;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id",referencedColumnName = "id")
    private Company company;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
