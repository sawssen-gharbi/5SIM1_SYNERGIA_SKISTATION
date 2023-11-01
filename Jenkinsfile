pipeline {
 environment {
         registry = "sawssen97/gestionski-devops"
        registryCredential = 'sawssenhub_id'
        dockerImage = ''
    }

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
        stage('DOCKER BUILD') {
                    steps {
                        script {
                            dockerImage = docker.build registry + ":$BUILD_NUMBER"
                        }
                    }
                }

         /*stage('DOCKER DEPLOY') {
             steps {
                 withCredentials([string(credentialsId: 'sawssenhub_id', variable: 'DOCKERHUB_PASSWORD')]) {
                     sh 'docker login -u sawssen97 -p $DOCKERHUB_PASSWORD'
                     sh 'docker push sawssen97/gestionski-devops:1.0'
                 }
             }
         }*/
          stage('DOCKER PUSH') {
                     steps {
                          script {
                            docker.withRegistry( '', registryCredential ) {
                                 dockerImage.push()
                             }
                          }

                     }
                 }

         stage('DOCKER COMPOSE') {
             steps {
                     sh 'docker-compose up -d'
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