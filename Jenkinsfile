pipeline{
    agent any
    tools {
        jdk 'jdk17'
        maven 'Maven-3'
    }
    environment {
        APP_NAME = "demoapp"
        RELEASE = "1.0.0"
        DOCKER_USER = "danish880"
        DOCKER_PASS = 'dockerhub'
        IMAGE_NAME = "${DOCKER_USER}" + "/" + "${APP_NAME}"
        IMAGE_TAG = "${RELEASE}-${BUILD_NUMBER}"

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
        stage("Nexus") {
            steps {
                script {
                    nexusArtifactUploader artifacts: [
                            [
                                artifactId: 'demoapp', 
                                classifier: '', 
                                file: 'target/demoapp.jar', 
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
        stage("Build & Push Docker Image") {
            steps {
                script {
                    docker.withRegistry('',DOCKER_PASS) {
                        docker_image = docker.build "${IMAGE_NAME}"
                    }
                    docker.withRegistry('',DOCKER_PASS) {
                        docker_image.push("${IMAGE_TAG}")
                        docker_image.push('latest')
                    }
                }
            }
        }
    }
}