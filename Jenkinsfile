pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    stages {

        stage('Build') {
            steps {
                dir('microservices-assignment2/order-service') {
                    bat 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Test') {
            steps {
                dir('microservices-assignment2/order-service') {
                    bat 'mvn test'
                }
            }
        }

        stage('Docker Build') {
            steps {
                dir('microservices-assignment2/order-service') {
                    bat 'docker build -t order-service .'
                }
            }
        }

        stage('Deploy') {
            steps {
                bat '''
                docker stop order-service-container || exit 0
                docker rm order-service-container || exit 0
                docker run -d -p 8087:8082 --name order-service-container order-service
                '''
            }
        }
    }

    post {
        success {
            echo 'Pipeline executed successfully!'
        }
        failure {
            echo 'Pipeline failed. Check logs.'
        }
    }
}
