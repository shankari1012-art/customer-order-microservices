pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    environment {
        IMAGE_NAME = "order-service"
    }

    stages {

        stage('Clone') {
            steps {
                git branch: 'main', url: 'https://github.com/shankari1012-art/customer-order-microservices.git'
            }
        }

        stage('Build') {
            steps {
                dir('microservices-assignment2') {
                    bat 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Test') {
            steps {
                dir('microservices-assignment2') {
                    bat 'mvn test'
                }
            }
        }

        stage('Docker Build') {
            steps {
                dir('microservices-assignment2/order-service') {
                    bat 'docker build -t %IMAGE_NAME% .'
                }
            }
        }

        stage('Deploy') {
            steps {
                bat '''
                docker stop order-service-container || true
                docker rm order-service-container || true
                docker run -d -p 8087:8082 --name order-service-container %IMAGE_NAME%
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
