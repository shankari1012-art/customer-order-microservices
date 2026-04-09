pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    stages {

        stage('Clone') {
            steps {
                git 'https://github.com/shankari1012-art/customer-order-microservices.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Docker Build') {
            steps {
                bat 'docker build -t order-service ./order-service'
            }
        }

        stage('Deploy') {
            steps {
                bat 'docker run -d -p 8087:8082 order-service'
            }
        }
    }
}
