pipeline {
    agent any
    stages {
        stage('Example Build') {
            steps {
                sh 'mvn clean'
                sh 'mvn package -Dmaven.test.skip=True'
                sh 'JENKINS_NODE_COOKIE=dontKillMe nohup java -jar target/swiggy-1.0-SNAPSHOT.jar --server.port=9011'
            }
        }
    }
}