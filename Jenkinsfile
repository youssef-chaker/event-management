pipeline {
    agent any 
    environment{
        DOCKERHUB_CREDS = credentials("dockerhub")
    }
    tools {
        maven 'maven3.8.4'
    }
    stages {
        stage('maven install') {
            // this is the only way the mvn install does not get stuck (gets stuck inside docker container )
            // todo replace to use docker container agent in the future 
            steps {
                retry(4){
                    timeout(time:6,unit:'MINUTES'){
                        sh 'mvn install -DskipTests'
                    }
                }
                sh 'pwd'
                sh 'ls -la'
            }
        }

        stage('extract jar file'){
            steps {
                sh "mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)"
                sh 'pwd'
                sh 'ls -la'
            }
        }

        stage('build docker image'){
            steps {
                sh 'docker build -t thiccmoustache/eventmicroservice:$BUILD_NUMBER .'
            }
        }

        stage('dockerhub login') {
            steps {
                sh 'docker login -u $DOCKERHUB_CREDS_USR -p $DOCKERHUB_CREDS_PSW'
            }
        }

        stage('push to dockerhub'){
            steps {
                sh 'docker push thiccmoustache/eventmicroservice:$BUILD_NUMBER'
            }
        }

        
    }

    post{
        always {
            sh 'docker logout'
        }
    }

}