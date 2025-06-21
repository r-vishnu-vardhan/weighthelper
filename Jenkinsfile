pipeline {
    agent any

    tools {
        jdk 'JDK 8'
        maven 'Maven-3.9.10'
    }

    stages {
        stage('Check Java Version') {
            steps {
                sh 'java -version'
                sh 'echo JAVA_HOME=$JAVA_HOME'
            }
        }

        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/r-vishnu-vardhan/weighthelper.git'
            }
        }

        stage('Build') {
            steps {
                echo 'Compiling project...'
                sh 'mvn clean compile'
            }
        }

        stage('Generate Tests with EvoSuite') {
            steps {
                echo 'Generating tests using EvoSuite...'
                sh 'mvn evosuite:generate evosuite:export -DtestDir=src/test/java'
            }
        }

        stage('Run Tests') {
            steps {
                echo 'Running tests...'
                sh 'mvn test'
                junit 'target/surefire-reports/*.xml'
            }
        }

        stage('Run Mutation Tests (PITest)') {
            steps {
                echo 'Running PITest mutation testing...'
                sh 'mvn org.pitest:pitest-maven:mutationCoverage'
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging JAR...'
                sh 'mvn package -DskipTests'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }
}
