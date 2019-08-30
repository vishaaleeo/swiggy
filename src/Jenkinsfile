pipeline {
    agent any

    stages {
        stage ('Compile Stage') {

            steps {
                withMaven(maven : 'maven_3_5_0') {
                    sh 'mvn clean compile'
                }
            }
        }


        stage ('Deployment Stage') {
            steps {
                withMaven(maven : 'maven_3_5_0') {
                    sh 'mvn package -Dmaven.test.skip=True'
                    sh 'java -jar target/swiggy-1.0-SNAPSHOT.jar --server.port=9000'
'
                }
            }
        }
    }
}