pipeline {

  agent any

  stages {

    stage('checkout') {
      steps {
        git 'https://github.com/fdiotalevi/cryptoeval.git'
      }
    }
    
    stage('build') {
      steps {
        sh './gradlew build'
      }
      
    }

  }

}
