pipeline {

  agent any
  
    environment {
        // Define Quay.io credentials for pushing images
        QUAY_IO_CREDENTIALS = 'quay-credentials'
        QUAY_IO_REGISTRY = 'https://quay.io'
        QUAY_IO_USERNAME = 'udaykirank'
        QUAY_IO_REPOSITORY_PREFIX = 'csye7125group3/webapp-images' // Customize this as needed
        IMAGE_NAME = 'quay.io/csye7125group3/webapp-images:1.1'
    }

  stages {

    stage('Checkout') {
      steps {
        git branch: 'main', 
            credentialsId: 'github-token-jenkins',
            url: 'https://github.com/cyse7125-fall2023-group03/webapp.git'
      }
    }

    stage('Build Image') {
      steps {  
        script {
        //   def imageName = "myapp"
          docker.build("${env.IMAGE_NAME}", '.')
        }
      }
    }
    
    stage('Login to Quay.io') {
        steps {
            withCredentials([usernamePassword(credentialsId: QUAY_IO_CREDENTIALS, passwordVariable: 'QUAY_IO_PASSWORD', usernameVariable: 'QUAY_IO_USERNAME')]) {
                script {
                        sh "docker login -u $QUAY_IO_USERNAME -p $QUAY_IO_PASSWORD ${env.QUAY_IO_REGISTRY}"
                    }
                }
        }
    }
    
    stage('Push Docker Image to Quay.io') {
        steps {
            script {
                // docker.image('${env.IMAGE_NAME}').push()
                docker.withRegistry("https://quay.io/", QUAY_IO_CREDENTIALS) {
                    docker.image("${env.IMAGE_NAME}").push()
                }
            }
        }
    }

  }

}
