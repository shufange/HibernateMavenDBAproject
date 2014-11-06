/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.shufange.hibernatemavenproject;

import com.shufange.hibernatemavenproject.model.*;
import com.shufange.hibernatemavenproject.util.HibernateUtil;
import java.util.*;
import org.hibernate.HibernateException;
import org.hibernate.*;

/**
 *
 * @author shufange
 */
public class Service {
    
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    
    public void addCompany(String companyname){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Set employees = null;
        try {
            tx = session.beginTransaction();
            Company company = new Company(companyname);
            session.save(company);
            tx.commit();
        }catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            ex.printStackTrace();
        }finally {
            session.close();
        }
    }
    
    public void deleteCompany(String searchinput){
        Session session = sessionFactory.openSession();
        Transaction tx = null;    
        List<Company> companies = new ArrayList();
        
        try {
            tx = session.beginTransaction();
            String query = null;
            if(isValidInteger(searchinput)){               
                query = "where companyId = " + searchinput.trim();
            }else{
                query = "where companyName = " + searchinput.trim();
            }
            companies = session.createQuery("from Company "+query).list();   
            for(Company company : companies){
                session.delete(company);
            }
            tx.commit();
        }catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            ex.printStackTrace();
        }finally {           
            session.close();
        }
    }
    
    public List<Company> listCompany(){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List<Company> companies = new ArrayList();
        try {
            tx = session.beginTransaction();
            companies = session.createQuery("from Company").list();           
            tx.commit();
        }catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            ex.printStackTrace();
        }finally {           
            session.close();
            return companies;
        }
    }
    
    
    public boolean isValidInteger(String input){
        try{
            int num = Integer.parseInt(input.trim());
            return true;
        }catch(Exception ex){
            return false;
        }
    }
    
    public List<Company> searchCompany(String searchinput){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List<Company> companies = new ArrayList();       
        
        try {
            tx = session.beginTransaction();
            String query = null;
            if(isValidInteger(searchinput)){               
                query = "where companyId = " + searchinput.trim();
            }else{
                query = "where companyName like " + "'%" + searchinput.trim()+"%'";
            }
            companies = session.createQuery("from Company "+query).list();           
            tx.commit();
        }catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            ex.printStackTrace();
        }finally {           
            session.close();
            return companies;
        }
    }
    
    //list all employees
    public List<Employee> listEmployee(){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List<Employee> employees = new ArrayList();       
        
        try {
            tx = session.beginTransaction(); 
            Query query = session.createQuery("from Employee");
            employees = query.list();
            tx.commit();
        }catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            ex.printStackTrace();
        }finally {           
            session.close();
            return employees;
        }
    }
    
    //list all employees from one company
    public List<Employee> listEmployee(Company company){
        Session session = sessionFactory.openSession();
        List<Employee> employees = new ArrayList();
        employees.addAll(company.getEmployees());       
        return employees;       
    }
    
    public void addEmployee(String companyId, String employeeFirstName, 
            String employeeLastName, int salary){

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();  
            List<Company> companies = session.createQuery("from Company where companyId = "+companyId).list();
            Company company = companies.get(0);
            
            Employee employee = new Employee();
            employee.setFirstName(employeeFirstName);
            employee.setLastName(employeeLastName);
            employee.setSalary(salary);
            employee.setCompany(company);
             
            Set employees = new HashSet();
            employees.add(employee);
            company.setEmployees(employees);                       
            
            session.getTransaction().commit();
        }catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            ex.printStackTrace();
        }finally {           
            session.close();
        }
        
        
    }
    
    public void deleteEmployee(String searchinput){
        Session session = sessionFactory.openSession();
        Transaction tx = null;             
        
        try {
            tx = session.beginTransaction();
            if(!isValidInteger(searchinput.trim())) return;                           
            String query = "where employeeId = " + searchinput.trim();
            List<Employee> employees = session.createQuery("from Employee "+query).list();
            session.delete(employees.get(0));
            tx.commit();
        }catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            ex.printStackTrace();
        }finally {           
            session.close();
        }
        
    }
    
    public List<Employee> searchEmployee(String searchinput){
        List<Employee> employees = new ArrayList();
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            String queryparam = null;
            if(isValidInteger(searchinput)){
                queryparam = "employeeId="+searchinput;
            }else{
                queryparam = "firstName='"+searchinput+"' or lastName='"+searchinput+"'";
            }
            Query query = session.createQuery("from Employee where "+queryparam);
            employees = query.list();
            tx.commit();
        }catch(HibernateException ex){
            if (tx!=null) tx.rollback();
            ex.printStackTrace();
        }finally {           
            session.close();
            return employees;
        }
    }
        
}
