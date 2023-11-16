pipeline {
    agent any

    stages {
        stage('Récupération du code de la branche') {
            steps {
                // Vous pouvez spécifier ici comment récupérer le code de la branche
                // Par exemple, en utilisant Git, remplacez '<achref>' par le nom réel de la branche :
                sh 'git checkout nadine'
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
        withCredentials([
            string(credentialsId: 'sonarToken', variable: 'SONAR_TOKEN')
        ]) {
            sh "mvn sonar:sonar -Dsonar.login=$SONAR_TOKEN"
        }
    }
}


  stage('Tests unitaires avec Mockito') {
            steps {
                // Exécutez les tests unitaires pour chaque module ici
                sh 'mvn install -Dmaven.test.failure.ignore=true test'
            }
        }
 stage('Package Artifact ') {
           steps {

                    sh "mvn package -DskipTests"

           }
        }
       stage('Deploiment Artifact a Nexus') {
            steps {
                script {

                        sh "mvn deploy -DskipTests"

                }
            }
        }

               stage('DOCKER BUILD') {
                                          steps {
                                              // Arrêter les conteneurs Docker précédents s'ils sont en cours d'exécution
                                              sh 'docker stop gestionski-devops || true'
                                              sh 'docker stop nexus-container || true'
                                              sh 'docker stop sonarqube-container || true'

                                              // Construire le nouveau conteneur Docker
                                              sh 'docker build -t gestionski-devops:1.0 .'
                                          }
                                      }


                         stage('DOCKER DEPLOY') {
                                               steps {
                                                   script {
                                                       withCredentials([usernamePassword(credentialsId: 'rania28', usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD')]) {

                                                           sh "docker tag gestionski-devops:1.0 ranianadine/gestionski-devops:1.0"

                                                           sh "docker login -u $DOCKERHUB_USERNAME -p $DOCKERHUB_PASSWORD"

                                                           sh "docker push ranianadine/gestionski-devops:1.0"
                                                       }
                                                   }
                                               }
                                           }


                         stage('DOCKER COMPOSE') {
                                      steps {
                                             sh ' docker compose pull'
                                              sh 'docker compose up -d'
                                            }
                                  }
                                       stage('Email Notification') {
                                           steps {
                                                emailext(
                                                                     subject: "Build #${currentBuild.number} Successful: ${currentBuild.fullDisplayName}",
                                                                     body: """
                                                                         The build was successful!
                                                                         Build Details: ${BUILD_URL}
                                                                         Build Number: ${currentBuild.number}
                                                                         Build Status: ${currentBuild.currentResult}
                                                                     """,
                                                                     to: 'rania99belhajyoussef@gmail.com'
                                                                 )
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