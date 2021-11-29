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
                retry{
                    timeout(time:3,unit:'MINUTES'){
                        sh 'mvn install -DskipTests'
                    }
                }
            }
        }
        
    }

    post{
        always {
            cleanWs()
        }
    }

}