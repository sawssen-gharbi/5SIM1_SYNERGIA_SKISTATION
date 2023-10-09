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

                sh 'mvn clean'


                sh 'mvn compile'
            }
        }
    }

     post {
        success {
            // Actions à effectuer en cas de succès
             echo "Success!"
        }
        failure {
            // Actions à effectuer en cas d'échec
            echo "Failure!"
        }
    }
}