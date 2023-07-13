package com.csi.service;

import com.csi.model.Employee;
import com.csi.repo.EmployeeRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    EmployeeRepo employeeRepoImpl;

    public void signUp(Employee employee) {
        employeeRepoImpl.save(employee);

    }

    public boolean signIn(String empEmailId, String empPassword) {
        Employee employee = employeeRepoImpl.findByEmpEmailIdAndEmpPassword(empEmailId, empPassword);
        boolean flag = false;
        if (employee != null && employee.getEmpEmailId().equals(empEmailId) && employee.getEmpPassword().equals(empPassword)) {
            flag = true;
        }
        return flag;
    }

    public List<Employee> saveAllData(List<Employee> employeeList) {

        return employeeRepoImpl.saveAll(employeeList);
    }


    @Cacheable(value = "empId")
    public Optional<Employee> getDataById(int empId) {

        log.info("###Trying to fetch data from DB");

        return employeeRepoImpl.findById(empId);
    }

    public List<Employee> getAllData() {

        return employeeRepoImpl.findAll();
    }

    public List<Employee> getByName(String empName) {

        return getAllData().stream().filter(emp -> emp.getEmpName().equals(empName)).collect(Collectors.toList());

    }

    public Employee getDataByEmailId(String empEmailId) {

        return getAllData().stream().filter(emp -> emp.getEmpEmailId().equals(empEmailId)).collect(Collectors.toList()).get(0);

    }

    public Employee getDataByContactNumber(long empContactNumber) {

        return getAllData().stream().filter(employee -> employee.getEmpContactNumber() == empContactNumber).collect(Collectors.toList()).get(0);
    }

    public List<Employee> getDataByDOB(String empDOB) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        List<Employee> employeeList = new ArrayList<>();

        for (Employee employee : getAllData()) {

            String empDBDOB = simpleDateFormat.format(employee.getEmpDOB());

            if (empDBDOB.equals(empDOB)) {

                employeeList.add(employee);
            }

        }
        return employeeList;
    }

    public List<Employee> getDataByAnyInput(String input) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        List<Employee> employeeList = new ArrayList<>();

        for (Employee employee : getAllData()) {

            String empDBDOB = simpleDateFormat.format(employee.getEmpDOB());

            if (empDBDOB.equals(input)
                    || String.valueOf(employee.getEmpId()).equals(input)
                    || employee.getEmpName().equals(input)
                    || employee.getEmpAddress().equals(input)
                    || String.valueOf(employee.getEmpContactNumber()).equals(input)
                    || String.valueOf(employee.getEmpSalary()).equals(input)
                    || employee.getEmpAddress().equals(input)
                    || employee.getEmpDOB().equals(input)
                    || employee.getEmpEmailId().equals(input)


            ) {

                employeeList.add(employee);
            }

        }
        return employeeList;
    }

    public List<Employee> sortById() {

        return getAllData().stream().sorted(Comparator.comparingInt(Employee::getEmpId)).collect(Collectors.toList());
    }

    public List<Employee> sortByName() {

        return getAllData().stream().sorted(Comparator.comparing(Employee::getEmpName)).collect(Collectors.toList());
    }

    public List<Employee> sortBySalary() {

        return getAllData().stream().sorted(Comparator.comparingDouble(Employee::getEmpSalary)).collect(Collectors.toList());

    }

    public List<Employee> sortByDOB() {
        return getAllData().stream().sorted(Comparator.comparing(Employee::getEmpDOB)).collect(Collectors.toList());
    }

    public List<Employee> filterDataBySalary(double empSalary) {

        return getAllData().stream().filter(emp -> emp.getEmpSalary() >= empSalary).collect(Collectors.toList());
    }

    public Employee updateDataById(Employee employee) {


        return employeeRepoImpl.save(employee);
    }

    public boolean checkLoanEligibilty(int empId) {

        boolean flag = false;

        for (Employee employee : getAllData()) {
            if (employee.getEmpId() == empId && employee.getEmpSalary() >= 3000) {
                flag = true;
                break;
            }
        }
        return flag;

    }

    public void deleteDataById(int empId) {

        employeeRepoImpl.deleteById(empId);
    }

    public void deleteAllData() {
        employeeRepoImpl.deleteAll();
    }


}



