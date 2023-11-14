pipeline {
    agent any

    stages {
        stage('Git') {
            steps {
                echo 'Pulling from your git repository'
                git branch: 'achref', url: 'https://Token@github.com/your-username/your-repository.git'
            }
        }

        stage('Build Maven') {
            steps {
                sh 'mvn -Dmaven.test.failure.ignore=true clean package'
            }
        }

        stage('Maven Deploy') {
            steps {
                script {
                    // Modify this command based on your Maven deployment requirements
                    sh 'mvn deploy -Dmaven.test.skip=true'
                }
            }
        }

        stage("Build Docker Image") {
            steps {
                script {
                    sh "docker build -t gestionski-devops:1.0 ."
                }
            }
        }

        stage('Docker Hub') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'mejriachref', variable: 'DOCKERHUB_PASSWORD')]) {
                        sh "docker login -u mejriachref -p $DOCKERHUB_PASSWORD"
                        sh "docker push mejriachref/gestionski-devops:1.0"
                    }
                }
            }
        }

        stage('Run Sonar') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'Token', variable: 'SONAR_TOKEN')]) {
                        sh 'mvn sonar:sonar -Dsonar.host.url=http://localhost:9000/ -Dsonar.login=$SONAR_TOKEN'
                    }
                }
            }
        }

        stage('Unit Test') {
            steps {
                sh 'mvn -Dmaven.test.failure.ignore=true test'
            }
        }

        stage('JUNIT TEST with JaCoCo') {
            steps {
                sh 'mvn test jacoco:report'
                echo 'Test stage done'
            }
        }

        stage('Collect JaCoCo Coverage') {
            steps {
                jacoco(execPattern: '**/target/jacoco.exec')
            }
        }

        stage('Docker Compose') {
            steps {
                sh "docker-compose up -d"
            }
        }
    }

    post {
        success {
            sh 'echo "Success!"'
        }
        failure {
            sh 'echo "Failure!"'
        }
    }
}
