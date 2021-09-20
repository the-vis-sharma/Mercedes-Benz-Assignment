package com.mb.service2.service;

import com.mb.service2.model.Employee;
import com.mb.service2.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    FileUtil fileUtil;

    public List getAllEmployee() throws IOException, JAXBException {
        List<Employee> employees = new ArrayList<>();
        employees.addAll(fileUtil.getEmployeeListFromCSV());
        employees.addAll(fileUtil.getEmployeeListFromXml().getEmployees());
        employees.addAll(fileUtil.getEmployeeListFromJSON());
        return employees;
    }


}
