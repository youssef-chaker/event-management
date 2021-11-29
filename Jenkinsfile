pipeline {
    agent any 
    environment{
        DOCKERHUB_CREDS = credentials("dockerhub")
    }
    tools {
        maven 'maven3.8.4'
    }
    stages {
        stage('build') {
            steps {
                retry(4){
                    timeout(time:6,unit:'MINUTES'){
                        sh 'mvn install -DskipTests'
                    }
                }
            }

            steps{
                pwd()
            }

            steps {
                sh 'ls -la'
            }

        }

        stage('dockerhub login') {
            steps {
                sh 'docker login -u $DOCKERHUB_CREDS_USR -p $DOCKERHUB_CREDS_PSW'
            }
        }

        
    }

    post{
        always {
            sh 'docker logout'
        }
    }

}