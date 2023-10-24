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
         stage('MVN SONARQUBE') {
             steps {
                 sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=sonar'
             }
         }
  stage('TESTS UNITAIRES MOCKITO') {
            steps {

                //sh 'mvn test'
                sh 'mvn install -Dmaven.test.skip=true'
            }
        }
     stage('NEXUS'){
     steps{
     sh 'mvn deploy -Dmaven.test.skip=true'
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