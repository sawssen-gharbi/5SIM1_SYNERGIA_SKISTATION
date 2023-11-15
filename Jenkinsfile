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
                withCredentials([usernamePassword(credentialsId: 'NexusAuth', usernameVariable: 'admin', passwordVariable: 'rania')]) {
                     sh 'mvn deploy -Dmaven.test.skip=true -Dmaven.deploy.skip=true -DaltDeploymentRepository=deploymentRepo::default::http://localhost:8081/repository/maven-releases/'
                }
            }
         }

         stage('DOCKER BUILD') {
            steps{
                 sh 'docker build -t hamzanechi/gestionski-devops:1.0 .'
            }
         }

         stage('DOCKER DEPLOY') {
             steps {
                 withCredentials([usernamePassword(credentialsId: 'DockerHubId', usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD')]) {
                             sh 'docker login -u $DOCKERHUB_USERNAME -p $DOCKERHUB_PASSWORD'
                             sh 'docker push hamzanechi/gestionski-devops:1.0'
                         }
             }
         }

         stage('DOCKER COMPOSE') {
             steps {
                     sh 'docker-compose up'
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