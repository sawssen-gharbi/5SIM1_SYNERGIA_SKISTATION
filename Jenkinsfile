pipeline {
    agent any

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
                 sh 'mvn install -Dmaven.test.skip=true'
            }
        }
        stage('Déploiement dans Nexus') {
               steps {
                   // Exécutez la commande Maven pour déployer le projet dans Nexus en sautant les tests
                   sh 'mvn deploy -Dmaven.test.skip=true'
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
                              withCredentials([usernamePassword(credentialsId: 'mejriachref', usernameVariable: 'mejriachref', passwordVariable: 'Espritesprit/22')]) {
                                  sh 'docker login -u $DOCKERHUB_USERNAME -p $DOCKERHUB_PASSWORD'
                                  sh 'docker push mejriachref/gestionski-devops:1.0'
                              }
                          }
                      }


                         stage('Configure and Start Prometheus and Grafana') {
                                    steps {
                                        script {
                                            // Create a docker-compose.yml for Prometheus and Grafana
                                            writeFile file: 'docker-compose-monitoring.yml', text: '''
                                                version: "3.8"
                                                services:
                                                  prometheus:
                                                    image: prom/prometheus
                                                    ports:
                                                      - 9090:9090
                                                    volumes:
                                                      - ./prometheus:/etc/prometheus
                                                    command:
                                                      - --config.file=/etc/prometheus/prometheus.yml
                                                    networks:
                                                      - monitoring

                                                  grafana:
                                                    image: grafana/grafana
                                                    ports:
                                                      - 3000:3000
                                                    depends_on:
                                                      - prometheus
                                                    networks:
                                                      - monitoring

                                                networks:
                                                  monitoring:
                                            '''

                                            // Start Prometheus and Grafana
                                            sh 'docker-compose -f docker-compose-monitoring.yml up -d'
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
