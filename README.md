
# 🧪 LEAN Software Testing Pipeline with CI/CD and Mutation Testing

This project demonstrates a lean automated testing pipeline combining **JUnit**, **PIT Mutation Testing**, **Maven**, and **Jenkins CI**. It reflects core LEAN software testing principles—simplicity, fast feedback, automation, and minimal waste—using a BMI calculator example.

## 📁 Project Structure

```
bmi-calculator/
├── src/
│   ├── main/java/org/example/WeightHelper.java
│   └── test/java/org/example/TestWeightHelper.java
├── pom.xml
├── pitest-config.xml
├── Jenkinsfile
├── Dockerfile
├── .gitignore
└── README.md
```

## ⚙️ Requirements

- Java 8 or higher
- Maven 3.8+
- Git
- Docker Desktop for Windows (https://www.docker.com/products/docker-desktop/)

## 🚀 Local Setup & Test

1. **Clone the repository**
   ```bash
   git clone https://github.com/r-vishnu-vardhan/weighthelper.git
   cd project
   ```

2. **Run Unit Tests**
   ```bash
   mvn clean test
   ```

3. **Run Mutation Tests**
   ```bash
   mvn org.pitest:pitest-maven:mutationCoverage
   ```

   Reports will be generated under `target/pit-reports/`.

## 🛠️ Local Jenkins Setup Using Docker Desktop

To simulate CI/CD locally using Jenkins:

1. **Run Jenkins in Docker**
   ```bash
   docker run -d --name jenkins-maven -p 8080:8080 -v jenkins_home:/var/jenkins_home jenkins/jenkins-maven:lts
   ```
docker run -d --name jenkins-maven -p 8080:8080 -p 50000:50000 -v jenkins_home:/var/jenkins_home jc21/jenkins-maven
docker run -d --name jenkins-maven -p 8080:8080 -p 50000:50000 -v jenkins_home:/var/jenkins_home jenkins-maven


2. **Open Jenkins in your browser**
   - URL: [http://localhost:8080](http://localhost:8080)
   - Unlock Jenkins:
     ```bash
     docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
     ```

3. **Complete Setup**
   - Install suggested plugins.
   - Create a new admin user.
   - Create a *New Item* → *Pipeline* project.

4. **Configure Jenkins Pipeline**
   - In Pipeline config, use the following:
     ```groovy
     pipeline {
         agent any
         stages {
             stage('Checkout') {
                 steps {
                     git 'https://github.com/r-vishnu-vardhan/weighthelper.git'
                 }
             }
             stage('Build') {
                 steps {
                     sh 'mvn clean compile'
                 }
             }
             stage('Test') {
                 steps {
                     sh 'mvn test'
                 }
             }
             stage('Mutation Testing') {
                 steps {
                     sh 'mvn org.pitest:pitest-maven:mutationCoverage'
                 }
             }
         }
         post {
             always {
                 junit 'target/surefire-reports/*.xml'
                 archiveArtifacts artifacts: 'target/pit-reports/**', fingerprint: true
             }
         }
     }
     ```

   > ⚠️ If using Windows, ensure Git Bash or WSL is used to enable shell commands (`sh`) in Jenkins. Alternatively, change `sh` to `bat` for native `.bat` commands.

## 🧪 What’s Included

- **JUnit 5** for unit testing.
- **PITest** for mutation testing, verifying test robustness.
- **Maven** as the build and test automation tool.
- **Jenkins** for automated CI/CD pipeline execution.

## 📊 Mutation Testing Configuration

`pitest-config.xml` specifies what classes and tests to target:

```xml
<configuration>
    <targetClasses>
        <param>org.*</param>
    </targetClasses>
    <targetTests>
        <param>org.*</param>
    </targetTests>
</configuration>
```

This is also mirrored in the `pom.xml` plugin configuration.

## 🧼 Clean Up

To stop and remove Jenkins container:

```bash
docker stop jenkins-maven
docker rm jenkins-maven
```

## ✅ Summary

This project provides:

- A lean test pipeline with CI/CD and mutation testing.
- Local development validation with JUnit.
- Quality checks using PITest.
- Jenkins automation, reproducible via Docker Desktop on Windows.

---
© 2025 Vishnu Vardhan Revalapalli. MIT License.
