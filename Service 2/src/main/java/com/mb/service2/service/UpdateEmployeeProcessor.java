package com.mb.service2.service;

import com.mb.service2.kafka.event.UpdateEmployeeEvent;
import com.mb.service2.kafka.event.UpdateEmployeeEventBody;
import com.mb.service2.model.Employee;
import com.mb.service2.model.EmployeeList;
import com.mb.service2.util.Constants;
import com.mb.service2.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

@Service
public class UpdateEmployeeProcessor {

    @Autowired
    FileUtil fileUtil;

    public void process(UpdateEmployeeEvent event) throws IOException, JAXBException {
        UpdateEmployeeEventBody body = event.getBody();
        String fileType = body.getFileType();

        Employee employee = body.getEmployee();

        List<Employee> employeeList = null;
        switch (fileType.toUpperCase()) {
            case Constants.FILE_TYPE_CSV:
                employeeList = fileUtil.getEmployeeListFromCSV();
                employeeList.removeIf(emp -> emp.getId().equals(employee.getId()));
                employeeList.add(employee);
                fileUtil.writeEmployeeListToCSV(employeeList);
                break;

            case Constants.FILE_TYPE_XML:
                EmployeeList employees = fileUtil.getEmployeeListFromXml();
                employeeList = employees.getEmployees();
                employeeList.removeIf(emp -> emp.getId().equals(employee.getId()));
                employeeList.add(employee);
                employees.setEmployees(employeeList);
                fileUtil.writeEmployeeListToXml(employees);
                break;

            case Constants.FILE_TYPE_JSON:
                employeeList = fileUtil.getEmployeeListFromJSON();
                employeeList.removeIf(emp -> emp.getId().equals(employee.getId()));
                employeeList.add(employee);
                fileUtil.writeEmployeeListToJSON(employeeList);
                break;
        }
    }

}
