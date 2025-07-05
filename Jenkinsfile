pipeline{
    agent any
    tools {
        jdk 'jdk17'
        maven 'Maven-3'
    }
     environment {
        APP_NAME = "complete-prodcution-e2e-pipeline"
        RELEASE = "1.0.0"
        DOCKER_USER = "dmancloud"
        DOCKER_PASS = 'dockerhub'
        IMAGE_NAME = "${DOCKER_USER}" + "/" + "${APP_NAME}"
        IMAGE_TAG = "${RELEASE}-${BUILD_NUMBER}"
        JENKINS_API_TOKEN = credentials("JENKINS_API_TOKEN")
    }
    stages{
        stage("Cleanup Workspace"){
            steps {
                cleanWs()
            }
        }
        stage("Checkout from SCM"){
            steps {
                git branch: 'main', credentialsId: 'github', url: 'https://github.com/infracodemaster/javaProject2.git'
            }
        }
        stage("Build Application"){
            steps {
                sh "mvn clean package"
            }
        }
        stage("Buid Test"){
            steps{
                script{
                    sh "mvn test"
                }
            }
        }
        stage("Sonarqube Analysis") {
            steps {
                script {
                    withSonarQubeEnv(credentialsId: 'sonar-secret') {
                        sh "mvn sonar:sonar"
                    }
                }
            }
        }
        stage("Quality Gate") {
            steps {
                script {
                      waitForQualityGate abortPipeline: false, credentialsId: 'sonar-secret'
                    }
                }
            }
         stage("Nexuxs Upload") {
            steps {
                script {
                      nexusArtifactUploader artifacts: [
                            [
                                artifactId: 'demoapp', 
                                classifier: '', 
                                file: 'target/demoapp', 
                                type: 'jar'
                            ]], 
                            credentialsId: 'nexus-secret', 
                            groupId: 'com.dmancloud.dinesh', 
                            nexusUrl: '74.225.245.251:8081', 
                            nexusVersion: 'nexus3', 
                            protocol: 'http', 
                            repository: 'javaapp-relese', 
                            version: '1.0.0'
                    }
                }
            }    
        }
        
        
    }

