package utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CsvDataReader {
    
    public static class TestData {
        public String testSuite;
        public String testMethod;
        public String testDataType;
        public String userId;
        public String name;
        public String email;
        public int expectedStatus;
        public String description;
        
        public TestData(String testSuite, String testMethod, String testDataType, 
                     String userId, String name, String email, int expectedStatus, String description) {
            this.testSuite = testSuite;
            this.testMethod = testMethod;
            this.testDataType = testDataType;
            this.userId = userId;
            this.name = name;
            this.email = email;
            this.expectedStatus = expectedStatus;
            this.description = description;
        }
    }
    
    public static List<TestData> getTestData(String testMethod) {
        List<TestData> testDataList = new ArrayList<>();
        
        try (Reader reader = new FileReader("src/test/resources/test_data.csv");
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                 .withFirstRecordAsHeader()
                 .withIgnoreHeaderCase()
                 .withTrim())) {
            
            for (CSVRecord csvRecord : csvParser) {
                if (csvRecord.get("Test Method").equals(testMethod)) {
                    TestData testData = new TestData(
                        csvRecord.get("Test Suite"),
                        csvRecord.get("Test Method"),
                        csvRecord.get("Test Data Type"),
                        csvRecord.get("User ID"),
                        csvRecord.get("Name"),
                        csvRecord.get("Email"),
                        Integer.parseInt(csvRecord.get("Expected Status")),
                        csvRecord.get("Description")
                    );
                    testDataList.add(testData);
                }
            }
            
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
        
        return testDataList;
    }
    
    public static TestData getTestData(String testMethod, String testDataType) {
        List<TestData> testDataList = getTestData(testMethod);
        
        return testDataList.stream()
                .filter(data -> data.testDataType.equals(testDataType))
                .findFirst()
                .orElse(null);
    }
    
    public static List<TestData> getAllTestData() {
        List<TestData> testDataList = new ArrayList<>();
        
        try (Reader reader = new FileReader("src/test/resources/test_data.csv");
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                 .withFirstRecordAsHeader()
                 .withIgnoreHeaderCase()
                 .withTrim())) {
            
            for (CSVRecord csvRecord : csvParser) {
                TestData testData = new TestData(
                    csvRecord.get("Test Suite"),
                    csvRecord.get("Test Method"),
                    csvRecord.get("Test Data Type"),
                    csvRecord.get("User ID"),
                    csvRecord.get("Name"),
                    csvRecord.get("Email"),
                    Integer.parseInt(csvRecord.get("Expected Status")),
                    csvRecord.get("Description")
                );
                testDataList.add(testData);
            }
            
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
        
        return testDataList;
    }
}
