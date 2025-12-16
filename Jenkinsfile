pipeline {

    agent any

    tools {
        maven 'Maven3'
    }

    environment {
        REGISTRY = "karim369/achat"

        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "192.168.122.1:8081"
        NEXUS_REPOSITORY = "nexus_devops"
        NEXUS_CREDENTIAL_ID = "NEXUS_CRED"

        SONAR_HOST_URL = "http://192.168.122.1:9000"
        SONAR_PROJECT_KEY = "sonarqube"
    }

    stages {

        stage("Mvn clean") {
            steps {
                echo 'Cleaning the application...'
                sh 'mvn clean'
            }
        }

        stage("Mvn compile") {
            steps {
                echo 'Compiling the application...'
                sh 'mvn compile'
            }
        }

        stage("Unit Test") {
            steps {
                sh 'mvn test'
            }
        }

        stage("Static code analysis - SonarQube") {
            environment {
                SONAR_TOKEN = credentials('SONAR_TOKEN')
            }
            steps {
                sh """
                mvn sonar:sonar \
                -Dsonar.projectKey=${SONAR_PROJECT_KEY} \
                -Dsonar.host.url=${SONAR_HOST_URL} \
                -Dsonar.login=${SONAR_TOKEN}
                """
            }
        }

        stage("Deploy to Nexus") {
            steps {
                sh 'mvn deploy -Dmaven.test.skip=true'
            }
        }

        stage("Build Docker Image") {
            steps {
                sh 'docker build -t ${REGISTRY} .'
            }
        }

        stage("Login to DockerHub") {
            environment {
                DOCKERHUB_CREDS = credentials('dockerHub')
            }
            steps {
                sh '''
                echo $DOCKERHUB_CREDS_PSW | docker login -u $DOCKERHUB_CREDS_USR --password-stdin
                '''
            }
        }

        stage("Push Docker Image") {
            steps {
                sh 'docker push ${REGISTRY}'
            }
        }
    }
}
