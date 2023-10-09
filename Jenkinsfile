pipeline {
    agent any

    stages {
        stage('Récupération du code de la branche') {
            steps {
                // Vous pouvez spécifier ici comment récupérer le code de la branche
                // Par exemple, en utilisant Git :
                sh 'git checkout <achref>'
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
    }

    post {
        success {
            // Vous pouvez ajouter ici des actions à effectuer en cas de succès
        }
        failure {
            // Vous pouvez ajouter ici des actions à effectuer en cas d'échec
        }
    }
}