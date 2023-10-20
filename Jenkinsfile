pipeline {
    agent none
    stages {
        stage('Docker Build webapp') {
            steps {
                sh 'docker build --no-cache -t webapp:latest .'
            }
        }
        stage('Docker Compose up') {
            steps {
                sh 'docker compose up'
            }
        }
    }
}
