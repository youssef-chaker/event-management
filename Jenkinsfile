pipeline {
    agent any 
    environment{
        DOCKERHUB_CREDS = credentials("dockerhub")
    }
    tools {
        maven 'maven 3.8.4'
        jdk 'jdk9'
    }
    stages {
        stage('build') {
            steps {
                sh 'mvn install -DskipTests'
            }
        }
        
    }

}