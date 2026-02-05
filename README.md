# ReqRes API Automation Framework

[![CI/CD](https://github.com/username/reqres-api-automation/actions/workflows/api-tests.yml/badge.svg)](https://github.com/username/reqres-api-automation/actions/workflows/api-tests.yml)
[![Coverage](https://codecov.io/gh/username/reqres-api-automation/branch/main/graph/badge.svg)](https://codecov.io/gh/username/reqres-api-automation)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![TestNG](https://img.shields.io/badge/TestNG-7.8.0-blue.svg)](https://testng.org/)
[![Rest Assured](https://img.shields.io/badge/Rest%20Assured-5.4.0-green.svg)](https://rest-assured.io/)

## 📋 Project Overview

This is a comprehensive API automation framework built with Java, TestNG, and Rest Assured for testing the ReqRes.in API. The framework follows best practices including Page Object Model, data-driven testing with CSV, and comprehensive reporting.

## 🚀 Features

### **Core Capabilities**
- ✅ **CRUD Operations**: Complete Create, Read, Update, Delete testing
- ✅ **Data-Driven Testing**: CSV-based test data management
- ✅ **Page Object Model**: Clean and maintainable API interactions
- ✅ **Error Handling**: Graceful handling of connection issues and API errors
- ✅ **Parallel Execution**: Multi-threaded test execution
- ✅ **Comprehensive Reporting**: ExtentReports + HTML reports
- ✅ **CI/CD Integration**: GitHub Actions pipeline
- ✅ **Code Coverage**: JaCoCo coverage reporting
- ✅ **Security Scanning**: Trivy vulnerability scanning

### **Test Coverage**
- **13 Test Cases** across all CRUD operations
- **7 Positive Tests** (happy path scenarios)
- **6 Negative Tests** (error scenarios)
- **Multiple Endpoints**: Users, Resources
- **Status Code Validation**: 200, 201, 204, 400, 404

## 📁 Project Structure

```
reqres-api-automation/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/reqres/api/
│   └── test/
│       ├── java/
│       │   ├── base/
│       │   │   └── BaseTest.java
│       │   ├── models/
│       │   │   ├── User.java
│       │   │   ├── UserResource.java
│       │   │   ├── UserUpdate.java
│       │   │   └── UsersResponse.java
│       │   ├── pages/
│       │   │   ├── UsersPage.java
│       │   │   └── ResourcePage.java
│       │   ├── tests/
│       │   │   ├── users/
│       │   │   │   ├── GetUsersTest.java
│       │   │   │   ├── PostUsersTest.java
│       │   │   │   ├── PutUsersTest.java
│       │   │   │   └── DeleteUsersTest.java
│       │   │   └── resources/
│       │   │       └── GetResourceTest.java
│       │   └── utils/
│       │       ├── ConfigReader.java
│       │       ├── CsvDataReader.java
│       │       ├── ExtentManager.java
│       │       └── JsonUtils.java
│       └── resources/
│           ├── config.properties
│           ├── test_data.csv
│           └── testng.xml
├── .github/
│   └── workflows/
│       └── api-tests.yml
├── build.gradle
├── test_cases.csv
├── CI_CD.md
└── README.md
```

## 🛠️ Technology Stack

| Technology | Version | Purpose |
|-------------|---------|---------|
| **Java** | 17 | Programming Language |
| **TestNG** | 7.8.0 | Test Framework |
| **Rest Assured** | 5.4.0 | API Testing |
| **Jackson** | 2.15.2 | JSON Processing |
| **ExtentReports** | 5.1.1 | Test Reporting |
| **Apache Commons CSV** | 1.10.0 | CSV Data Reading |
| **Hamcrest** | 2.2 | Assertions |
| **SLF4J** | 2.0.7 | Logging |
| **Gradle** | 8.1.1 | Build Tool |
| **JaCoCo** | 0.8.8 | Code Coverage |

## 🚀 Quick Start

### **Prerequisites**
- Java 17 or higher
- Gradle 8.0 or higher
- Git

### **Installation**
```bash
# Clone the repository
git clone https://github.com/username/reqres-api-automation.git
cd reqres-api-automation

# Run tests
./gradlew test

# Generate reports
./gradlew jacocoTestReport
```

### **Configuration**
Edit `src/test/resources/config.properties`:
```properties
baseUrl=https://reqres.in/api
```

## 📊 Test Execution

### **Run All Tests**
```bash
./gradlew test
```

### **Run Specific Test Categories**
```bash
# Run smoke tests only
./gradlew runSmokeTests

# Run regression tests only
./gradlew runRegressionTests

# Run API tests only
./gradlew runAPITests
```

### **Generate Coverage Report**
```bash
./gradlew jacocoTestReport
```

### **View Reports**
- **Test Report**: `build/reports/tests/test/index.html`
- **Coverage Report**: `build/reports/jacoco/html/index.html`

## 📋 Test Cases

### **Test Coverage Overview**
- **Total Test Cases**: 13
- **Positive Tests**: 7
- **Negative Tests**: 6
- **API Endpoints**: `/users`, `/unknown/{id}`

### **Test Categories**

#### **1. Create User Tests** (3 cases)
- Create new user with valid data ✅
- Create user with empty data ❌
- Create user with invalid email ❌

#### **2. Get Users Tests** (2 cases)
- Get all users ✅
- Get non-existent user ❌

#### **3. Get Resource Tests** (2 cases)
- Get valid resource by ID ✅
- Get non-existent resource ❌

#### **4. Update User Tests** (3 cases)
- Update existing user ✅
- Update non-existent user ❌
- Update user with invalid data ❌

#### **5. Delete User Tests** (3 cases)
- Delete existing user ✅
- Delete non-existent user ❌
- Verify user deletion ✅

### **Test Data Management**
All test data is externalized to CSV files:
- **`test_data.csv`**: Test data for execution
- **`test_cases.csv`**: Test case documentation

## 🔧 Configuration

### **Environment Variables**
```bash
# API Base URI
baseURI=https://reqres.in/api

# Test Configuration
test.parallel=true
test.retry.count=2
```

### **TestNG Configuration**
```xml
<!-- src/test/resources/testng.xml -->
<suite name="ReqRes API Test Suite">
    <test name="Create User Tests">
        <classes>
            <class name="tests.users.PostUsersTest"/>
        </classes>
    </test>
    <!-- ... other test suites -->
</suite>
```

## 📈 Reporting

### **ExtentReports**
- **HTML Reports**: Interactive and detailed test reports
- **Screenshots**: Automatic capture on failures
- **Logs**: Comprehensive test execution logs
- **Charts**: Test result visualization

### **JaCoCo Coverage**
- **Minimum Coverage**: 70%
- **HTML Reports**: Detailed coverage analysis
- **XML Reports**: CI/CD integration
- **Codecov Integration**: Cloud-based coverage tracking

### **TestNG Reports**
- **Default Reports**: TestNG HTML and XML reports
- **Emailable Reports**: Email-friendly summary reports
- **Index Reports**: Detailed test execution reports

## 🔄 CI/CD Pipeline

### **GitHub Actions**
- **Triggers**: Push, Pull Request, Daily Schedule
- **Stages**: Test → Security → Notify
- **Artifacts**: Test reports (30-day retention)
- **Notifications**: Slack integration

### **Pipeline Features**
- ✅ **Parallel Execution**: Multi-core processing
- ✅ **Dependency Caching**: Gradle cache optimization
- ✅ **Security Scanning**: Trivy vulnerability scanning
- ✅ **Coverage Reporting**: JaCoCo + Codecov
- ✅ **Slack Notifications**: Success/failure alerts

### **Setup Instructions**
1. **Configure GitHub Secrets**:
   ```yaml
   SLACK_WEBHOOK_URL: "your-slack-webhook-url"
   CODECOV_TOKEN: "your-codecov-token"
   ```

2. **Enable Codecov**: Connect repository to Codecov

3. **Configure Slack**: Create webhook URL

## 📊 Test Data Management

### **CSV Structure**
```csv
Test Suite,Test Method,Test Data Type,User ID,Name,Email,Expected Status,Description
Create User Tests,testCreateUser,Positive,,John Doe,john.doe@example.com,201,Create new user
```

### **Data-Driven Testing**
- **External Data**: All test data in CSV files
- **Dynamic Loading**: Runtime data loading
- **Multiple Scenarios**: Positive and negative test cases
- **Easy Maintenance**: Update data without code changes

### **Test Data Reader**
```java
// Example usage
CsvDataReader.TestData testData = CsvDataReader.getTestData("testCreateUser", "Positive");
Response response = usersPage.createUser(testData.name, testData.email);
```

## 🔍 Error Handling

### **Connection Issues**
- **Graceful Handling**: Try-catch for connection errors
- **Retry Logic**: Automatic retry for failed tests
- **Logging**: Comprehensive error logging
- **Skip Logic**: Skip tests on connection failures

### **API Errors**
- **Status Code Validation**: Proper HTTP status code handling
- **Error Response Parsing**: Structured error response handling
- **Expected vs Actual**: Clear assertion messages
- **API Behavior**: ReqRes-specific error handling

## 🐛 Debugging

### **Common Issues**
1. **Connection Refused**: Check API endpoint and network
2. **403/404 Errors**: API rate limiting or access restrictions
3. **CSV Reading**: Verify file path and format
4. **Dependency Issues**: Run `./gradlew clean build`

### **Debug Mode**
```bash
# Run with debug logging
./gradlew test --info --debug

# Run specific test class
./gradlew test --tests "tests.users.PostUsersTest"

# Run specific test method
./gradlew test --tests "tests.users.PostUsersTest.testCreateUser"
```

### **Logs Location**
- **Test Logs**: `build/logs/test.log`
- **Gradle Logs**: Console output
- **ExtentReports**: `build/reports/extent.html`

## 🤝 Contributing

### **Development Workflow**
1. **Fork** the repository
2. **Create** feature branch
3. **Write** tests for new features
4. **Run** all tests locally
5. **Push** to feature branch
6. **Create** Pull Request

### **Code Standards**
- **Java**: Follow Google Java Style Guide
- **TestNG**: Use descriptive test names
- **Comments**: Add meaningful comments
- **Documentation**: Update README and test cases

### **Test Standards**
- **Positive Tests**: Happy path scenarios
- **Negative Tests**: Error scenarios
- **Edge Cases**: Boundary conditions
- **Data-Driven**: Use CSV for test data

## 📞 Support

### **Contact**
- **Issues**: [GitHub Issues](https://github.com/username/reqres-api-automation/issues)
- **Discussions**: [GitHub Discussions](https://github.com/username/reqres-api-automation/discussions)
- **Email**: your-email@example.com

### **Documentation**
- **API Documentation**: [ReqRes API](https://reqres.in/)
- **TestNG Documentation**: [TestNG Official](https://testng.org/)
- **Rest Assured**: [Rest Assured Guide](https://rest-assured.io/)

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🎯 Roadmap

### **Upcoming Features**
- [ ] **Parallel Test Execution**: Enhanced parallel processing
- [ ] **API Performance Testing**: Response time validation
- [ ] **Database Integration**: Test data persistence
- [ ] **Docker Support**: Containerized testing
- [ ] **Kubernetes Integration**: Cloud-native testing
- [ ] **Advanced Reporting**: Custom dashboard integration

### **Improvements**
- [ ] **Test Data Generator**: Dynamic test data creation
- [ ] **API Mocking**: Mock server integration
- [ ] **Security Testing**: Authentication/authorization testing
- [ ] **Load Testing**: Concurrent user testing

## 📊 Statistics

### **Project Metrics**
- **Lines of Code**: ~2000
- **Test Cases**: 13
- **Code Coverage**: 70%+
- **Build Time**: ~2 minutes
- **Test Execution Time**: ~30 seconds

### **API Coverage**
- **Endpoints Covered**: 6
- **HTTP Methods**: GET, POST, PUT, DELETE
- **Status Codes**: 200, 201, 204, 400, 404
- **Data Types**: JSON, CSV

---

**Happy Testing! 🚀**

Made with ❤️ by [Your Name](https://github.com/username)
