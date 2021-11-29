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
                echo "building docker image"
            }
        }

        
    }

    post{
        always {
            sh 'docker logout'
        }
    }

}