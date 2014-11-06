/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.shufange.hibernatemavenproject.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cascade;



@Entity
@Table (name="COMPANY")
public class Company implements java.io.Serializable{
    @Id
    private int companyId;
    @Column(name="CNAME")
    private String companyName;
    
    @OneToMany(fetch=FetchType.EAGER, mappedBy = "COMPANY", cascade=CascadeType.ALL)
    private Set<Employee> employees = new HashSet();
    
    public Company() {
        
    }

    public Company(String companyName) {
        this.companyName = companyName;

    }

    
    public Set<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }


    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    
}
    
    

