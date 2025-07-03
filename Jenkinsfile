pipeline{
    agent any
    tools {
        jdk 'jdk17'
        maven 'Maven-3'
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
                      waitForQualityGate abortPipeline: true, credentialsId: 'sonar-secret'
                    }
                }
            }
        }
        
    }

