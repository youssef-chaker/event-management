pipeline {
    agent any 
    environment{
        DOCKERHUB_CREDS = credentials("dockerhub")
    }
    stages {
        stage('BUILD DOCKER IMAGE') {
            steps {
                sh 'docker build -t thiccmoustache/configserver:$BUILD_NUMBER .'
            }
        }
        stage('LOGIN TO DOCKERHUB'){
            steps {
                sh 'docker login -u $DOCKERHUB_CREDS_USR -p $DOCKERHUB_CREDS_PSW'
            }
        }
        stage('PUSH TO DOCKERHUB') {
            steps {
            sh 'docker push thiccmoustache/configserver:$BUILD_NUMBER'
            }
        }
    }
    post{
        always{
            sh 'docker logout'
        }
    }
}