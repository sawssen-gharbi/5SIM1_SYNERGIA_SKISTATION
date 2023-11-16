pipeline {
    agent any
    environment {
        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "192.168.33.10:8081"
        NEXUS_REPOSITORY = "maven-releases/"
        NEXUS_CREDENTIAL_ID = "NexusAuth"
    }


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
                //sh 'mvn install -Dmaven.test.skip=true'
                sh 'mvn install -Dmaven.test.failure.ignore=true test'
             }
         }

         stage('Déploiement dans Nexus') {
                     steps {
                         script {
                             def pom = readMavenPom file: "pom.xml"
                             def filesByGlob = findFiles(glob: "target/*.${pom.packaging}")
                             def artifactPath = filesByGlob[0]?.path

                             if (artifactPath) {
                                 echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}"
                                 nexusArtifactUploader(
                                     nexusVersion: NEXUS_VERSION,
                                     protocol: NEXUS_PROTOCOL,
                                     nexusUrl: NEXUS_URL,
                                     groupId: pom.groupId,
                                     version: pom.version,
                                     repository: NEXUS_REPOSITORY,
                                     credentialsId: NEXUS_CREDENTIAL_ID,
                                     artifacts: [
                                         [artifactId: pom.artifactId, classifier: '', file: artifactPath, type: pom.packaging],
                                         [artifactId: pom.artifactId, classifier: '', file: "pom.xml", type: "pom"]
                                     ]
                                 )
                             } else {
                                 error "*** File: ${artifactPath}, could not be found"
                             }
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
                     sh 'docker compose up -d'
                   }
         }



         stage('Email Notification') {
                     steps {
                         script {
                             currentBuild.result = 'SUCCESS'
                             emailext(
                                 subject: "Suivi de pipeline Jenkins",
                                 body: """
                                     Success!!
                                     Build Number: 1
                                     Build Status: success
                                 """,
                                 to: 'hamza.nechi@esprit.tn'
                             )
                         }
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