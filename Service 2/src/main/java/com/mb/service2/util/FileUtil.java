package com.mb.service2.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mb.service2.model.Employee;
import com.mb.service2.model.EmployeeList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileUtil {

    private ObjectMapper mapper;
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    public FileUtil() throws JAXBException {
        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        JAXBContext jaxbContext = JAXBContext.newInstance(EmployeeList.class);
        marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        unmarshaller = jaxbContext.createUnmarshaller();
    }

    public List<Employee> getEmployeeListFromCSV() throws IOException {
        List<Employee> employeeList = new ArrayList<>();
        File file = new File("data.csv");
        if (!file.exists()) {
            System.out.println("File doesn't exists");
            return employeeList;
        }
        Reader in = new FileReader(file);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withHeader(Constants.CSV_HEADERS)
                .withFirstRecordAsHeader()
                .parse(in);
        for (CSVRecord record : records) {
            Employee employee = new Employee();
            employee.setId(UUID.fromString(record.get("id")));
            employee.setName(record.get("name"));
            employee.setDob(record.get("dob"));
            employee.setSalary(Double.parseDouble(record.get("salary")));
            employee.setAge(Integer.parseInt(record.get("age")));
            employeeList.add(employee);
        }
        return employeeList;
    }

    public void writeEmployeeListToCSV(List<Employee> employeeList) throws IOException {
        FileWriter out = new FileWriter("data.csv");
        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT
                .withHeader(Constants.CSV_HEADERS))) {
            employeeList.forEach(employee -> {
                try {
                    printer.printRecord(employee.getId(), employee.getName(),
                            employee.getDob(), employee.getSalary(), employee.getAge());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }


    public List<Employee> getEmployeeListFromJSON() throws IOException {
        List<Employee> employeeList = null;
        File file = new File("data.json");
        if (file.exists()) {
            employeeList = mapper.readValue(file, new TypeReference<>() {});
            System.out.println("employee list: " + employeeList);
        }
        employeeList = employeeList == null ? new ArrayList<>() : employeeList;

        return employeeList;
    }

    public void writeEmployeeListToJSON(List<Employee> employeeList) throws IOException {
        File file = new File("data.json");
        mapper.writeValue(file, employeeList);
    }

    public EmployeeList getEmployeeListFromXml() throws JAXBException {
        EmployeeList employees = null;
        File file = new File("data.xml");
        System.out.println("file exist: " + file.exists());
        if (file.exists()) {
            employees = (EmployeeList) unmarshaller.unmarshal(file);
        }

        if (employees == null) {
            employees = new EmployeeList();
            employees.setEmployees(new ArrayList<>());
        }

        return employees;
    }

    public void writeEmployeeListToXml(EmployeeList employees) throws JAXBException {
        File file = new File("data.xml");
        marshaller.marshal(employees, file);
    }

}
