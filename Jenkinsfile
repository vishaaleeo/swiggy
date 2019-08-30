pipeline {
    agent { docker 'maven:3-alpine' }
    stages {
        stage('Example Build') {
            steps {
                sh 'mvn clean'
                sh 'mvn package -Dmaven.test.skip=True'
                sh ' java -jar target/swiggy-1.0-SNAPSHOT.jar --server.port=9011'
            }
        }
    }
}