pipeline {
    agent any

    stages {
        stage('GIT') {
            steps {

                sh 'git checkout sawsen'
                sh 'git pull'
            }
        }

        stage('MVN CLEAN AND COMPILE') {
            steps {
                // Nettoyage du projet avec Maven
                sh 'mvn clean'

                // Compilation du projet avec Maven
                sh 'mvn compile'
            }
        }
    }

    post {
        success {
            // Actions à effectuer en cas de succès
            sh 'echo "Success!"'
        }
        failure {
            // Actions à effectuer en cas d'échec
            sh 'echo "Failure!"'
        }
    }
}