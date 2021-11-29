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
                pwd()
                sh 'ls -la'
            }

        }

        
    }

    post{
        always {
            sh 'docker logout'
        }
    }

}