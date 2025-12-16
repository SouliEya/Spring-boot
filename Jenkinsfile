pipeline {
    
    agent any
    
    
    environment {
        
        registry = "karim369/8cfa1afuck"
        //registryCredential = 'dockerHub'
        //dockerImage = ''
        NEXUS_VERSION = "nexus3"
        // This can be http or https
        NEXUS_PROTOCOL = "http"
        // Where your Nexus is running. 'nexus-3' is defined in the docker-compose file
        NEXUS_URL = "192.168.122.1:8081"
        // Repository where we will upload the artifact
        NEXUS_REPOSITORY = "nexus_devops"
        // Jenkins credential id to authenticate to Nexus OSS
        NEXUS_CREDENTIAL_ID = "NEXUS_CRED"
    }

    stages {
        stage('checkout GIT') {
            steps{
                echo 'Pulling ... ';
            git branch:'karim_chakroun' ,
            url : 'https://github.com/OussemaAbderrahmen/SpringFinalAchat.git';
            }
        }
        stage("Mvn clean") {
      
      steps {
        echo 'cleaning the application ...'
        sh "mvn clean"
      }
    }
    stage("Mvn compile") {
      
      steps {
        echo 'compiling the application ...'
        sh "mvn compiler:compile"
      }
    }
    stage('Unit Test') {
            steps{
                sh 'mvn test'
            }
    }
    stage("test statique sonar") {
      
        steps {
        sh "mvn sonar:sonar \
  -Dsonar.projectKey=sonarqube \
  -Dsonar.host.url=http://192.168.122.1:9000 \
  -Dsonar.login=eaf1b0f65cca8542dfa740fbfda27ba454b7bf2f"
      }
    }
    stage("DEPLOY with Nexus") {
            steps { 
                sh'mvn clean deploy -Dmaven.test.skip=true -Dresume=false'
            }
        }
        stage("Building Docker Image") {
                steps{
                    sh 'docker build -t karim369/achat .'
                }
        }
        stage("Login to DockerHub") {
                steps{
                
                    sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u karim369 -p 8cfa1afuck'
                }
        }
        stage("Push to DockerHub") {
                steps{
                    sh 'docker push karim369/achat'
                }
        }
    }
    
}
