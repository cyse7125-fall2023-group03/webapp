pipeline {
    agent any

    environment {
        // Define Quay.io credentials for pushing images
        QUAY_IO_CREDENTIALS = 'quay-io-credentials'
        QUAY_IO_REGISTRY = 'quay.io'
        QUAY_IO_USERNAME = 'udaykirank'
        QUAY_IO_REPOSITORY_PREFIX = 'csye7125group3/webapp-images' // Customize this as needed
        IMAGE_NAME = 'webapp'
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                // Checkout source code from your Git repository
                    git branch: 'main',
                        credentialsId: 'github-token-jenkins'
                        url: 'https://github.com/cyse7125-fall2023-group03/webapp.git'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                // Define the image name and tag based on your repository
                // def env.IMAGE_NAME = "${env.QUAY_IO_REGISTRY}/${env.QUAY_IO_REPOSITORY_PREFIX}${env.JOB_NAME}"
                def imageTag = "${BUILD_NUMBER}"
                // Build the Docker image
                docker.build("-t ${env.IMAGE_NAME}:${imageTag} .")
            }
        }

        stage('Login to Quay.io') {
            steps {
                withCredentials([usernamePassword(credentialsId: QUAY_IO_CREDENTIALS, passwordVariable: 'QUAY_IO_PASSWORD', usernameVariable: 'QUAY_IO_USERNAME')]) {
                    sh "docker login -u $QUAY_IO_USERNAME -p $QUAY_IO_PASSWORD ${env.QUAY_IO_REGISTRY}"
                }
            }
        }

        stage('Push Docker Image to Quay.io') {
            steps {
                docker.withRegistry("${env.QUAY_IO_REGISTRY}", QUAY_IO_CREDENTIALS) {
                    docker.image("${env.IMAGE_NAME}:${imageTag}").push()
                }
            }
        }
    }
}
