pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                url: 'https://github.com/shankari1012-art/customer-order-microservices.git'
            }
        }

        stage('Build - Order Service') {
            steps {
                dir('microservices-assignment2/order-service') {
                    bat 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Test - Order Service') {
            steps {
                dir('microservices-assignment2/order-service') {
                    bat 'mvn test'
                }
            }
        }

        stage('Docker Build - Order Service') {
            steps {
                dir('microservices-assignment2/order-service') {
                    bat 'docker build -t order-service .'
                }
            }
        }

        stage('Deploy - Order Service') {
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
            echo '✅ Pipeline executed successfully!'
        }
        failure {
            echo '❌ Pipeline failed. Check logs.'
        }
    }
}
