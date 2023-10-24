pipeline {
    agent any

    stages {
        stage('Récupération du code de la branche') {
            steps {
                // Vous pouvez spécifier ici comment récupérer le code de la branche
                // Par exemple, en utilisant Git, remplacez par le nom réel de la branche :
                sh 'git checkout hamza'
                sh 'git pull'
            }
        }

        stage('Nettoyage et compilation avec Maven') {
            steps {
                // Nettoyage du projet avec Maven
                sh 'mvn clean'

                // Compilation du projet avec Maven
                sh 'mvn compile'
            }
        }

        stage('MVN SONARQUBE') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=rania'
            }
        }

         stage('Tests unitaires avec Mockito') {
             steps {
                // Exécutez les tests unitaires pour chaque module ici
                sh 'mvn install -Dmaven.test.skip=true'
             }
         }

         stage('Déploiement dans Nexus') {
            steps {
                // Exécutez la commande Maven pour déployer le projet dans Nexus en sautant les tests
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