package com.mb.service2.service;

import com.mb.service2.kafka.event.AddEmployeeEvent;
import com.mb.service2.kafka.event.AddEmployeeEventBody;
import com.mb.service2.model.Employee;
import com.mb.service2.model.EmployeeList;
import com.mb.service2.util.Constants;
import com.mb.service2.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class AddEmployeeProcessor {

    @Autowired
    FileUtil fileUtil;

    public void process(AddEmployeeEvent event) throws IOException, JAXBException {
        AddEmployeeEventBody body = event.getBody();
        String fileType = body.getFileType();

        Employee employee = body.getEmployee();
        employee.setId(UUID.randomUUID());

        List<Employee> employeeList = null;
        switch (fileType.toUpperCase()) {
            case Constants.FILE_TYPE_CSV:
                employeeList = fileUtil.getEmployeeListFromCSV();
                employeeList.add(employee);
                fileUtil.writeEmployeeListToCSV(employeeList);
                break;
            case Constants.FILE_TYPE_XML:
                EmployeeList employees = fileUtil.getEmployeeListFromXml();
                employeeList = employees.getEmployees();
                employeeList.add(employee);
                employees.setEmployees(employeeList);
                fileUtil.writeEmployeeListToXml(employees);
                break;
            case Constants.FILE_TYPE_JSON:
                employeeList = fileUtil.getEmployeeListFromJSON();
                employeeList.add(employee);
                fileUtil.writeEmployeeListToJSON(employeeList);
                break;
        }
    }

}
