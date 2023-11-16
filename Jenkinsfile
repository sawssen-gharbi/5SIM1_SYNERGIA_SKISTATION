pipeline {
    agent any
environment {
    NEXUS_VERSION = "nexus3"
    NEXUS_PROTOCOL = "http"
    NEXUS_URL = "192.168.33.10:8081"
    NEXUS_REPOSITORY = "maven-releases/"
    NEXUS_CREDENTIAL_ID = "achref_nexus"
}
    stages {
        stage('Récupération du code de la branche') {
            steps {
                // Vous pouvez spécifier ici comment récupérer le code de la branche
                // Par exemple, en utilisant Git, remplacez '<achref>' par le nom réel de la branche :
                sh 'git checkout achref'
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
sh 'mvn install -Dmaven.test.failure.ignore=true test'            }
        }
   stage("NEXUS") {
              steps {
                  script {
                      pom = readMavenPom file: "pom.xml";
                      filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                      echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                      artifactPath = filesByGlob[0].path;
                      artifactExists = fileExists artifactPath;
                      if(artifactExists) {
                          echo "* File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
                          nexusArtifactUploader(
                              nexusVersion: NEXUS_VERSION,
                              protocol: NEXUS_PROTOCOL,
                              nexusUrl: NEXUS_URL,
                              groupId: pom.groupId,
                              version: pom.version,
                              repository: NEXUS_REPOSITORY,
                              credentialsId: NEXUS_CREDENTIAL_ID,
                              artifacts: [
                                  [artifactId: pom.artifactId,
                                  classifier: '',
                                  file: artifactPath,
                                  type: pom.packaging],
                                  [artifactId: pom.artifactId,
                                  classifier: '',
                                  file: "pom.xml",
                                  type: "pom"]
                              ]
                          );
                      } else {
                          error "* File: ${artifactPath}, could not be found";
                      }
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
                                 withCredentials([usernamePassword(credentialsId: 'dockernew', usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD')]) {
                                             sh 'docker login -u $DOCKERHUB_USERNAME -p $DOCKERHUB_PASSWORD'
                                             sh 'docker push mejriachref/gestionski-devops:1.0'
                                         }
                             }
                         }

stage('DOCKER COMPOSE') {
    steps {
        sh ' docker-compose up -d'
    }
}
stage('email notification') {
    steps {
  script {
                emailext body: 'Successful build',
                         subject: 'Pipeline Build',
                         to: 'mejri.achref@espritt.tn'
            }    }
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
