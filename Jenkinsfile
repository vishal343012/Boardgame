 pipeline {
    agent any
    tools {
        maven "Maven3"
        jdk "jdk17"
    }
    environment {
        SONARQUBE_HOME = tool 'sonarqube-scanner'
    }
    stages {
        stage('Git Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/vishal343012/Boardgame.git'
            }
        }
        stage('Clean') {
            steps {
                sh 'mvn clean'
            }
        }
        stage('Compile') {
            steps {
                sh 'mvn compile'
            }
        }
        stage('Trivy FS Scan') {
            steps {
                sh 'trivy fs --format table -o trivy-report.txt --severity HIGH,CRITICAL .'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube-server') {
                    sh """
                        $SONARQUBE_HOME/bin/sonar-scanner \
                        -Dsonar.projectKey=Boardgame \
                        -Dsonar.sources=. \
                        -Dsonar.java.binaries=.
                    """
                }
            }
        }
        // stage('QUALITY_GATE') {
        //   steps {
        //     waitForQualityGate abortPipeline: false, credentialsId: 'sonarqube-cred'
        //   }
        // }
        stage('package') {
            steps {
                sh 'mvn package'
            }
        }
        stage('Deploy Artifacts To Nexus') {
            steps {
                withMaven(globalMavenSettingsConfig: 'global-setting', jdk: 'jdk17', maven: 'Maven3', mavenSettingsConfig: '', traceability: true) {
                     sh 'mvn deploy' 
                }
            }
        }
        stage('Docker build and Tag Image') {
            steps {
                script {
                    withDockerRegistry(credentialsId: 'dockerhub', toolName: 'dockerhub')  {
                        sh 'docker build -t vishal431/boardgame1:latest .'
                    }
                }
            }
        }
        stage('Trivy scan Docker image') {
            steps {
                sh 'trivy image --format table -o trivy-fs-report.html vishal431/boardgame1:latest'
            }
        }
        stage('Push Docker image') {
            steps {
                script {
                    withDockerRegistry(credentialsId: 'dockerhub', toolName: 'dockerhub')  {
                        sh 'docker push vishal431/boardgame1:latest'
                    }
                }
            }
        }
        stage('Deploy to kubernets') {
            steps {
                withKubeConfig(caCertificate: '', clusterName: 'kubernetes', contextName: '', credentialsId: 'k8-cred', namespace: 'webapps', restrictKubeConfigAccess: false, serverUrl: 'https://172.31.10.245:6443') {
                    sh 'kubectl apply -f deployment-service.yaml'
                }
            }
        }
        stage('Verify Deployment') {
            steps {
                withKubeConfig(caCertificate: '', clusterName: 'kubernetes', contextName: '', credentialsId: 'k8-cred', namespace: 'webapps', restrictKubeConfigAccess: false, serverUrl: 'https://172.31.10.245:6443') {
                    sh 'kubectl get pods -n webapps'
                    sh 'kubectl get svc -n webapps'
                }
            }
        }
    }
}
  
      
    post {
        always {
            script {
                def jobName = env.JOB_NAME
                def buildNumber = env.BUILD_NUMBER
                def pipelineStatus = currentBuild.result ?: 'UNKNOWN'
                def bannerColor = pipelineStatus.toUpperCase() == 'SUCCESS' ? 'green' : 'red'

                def body = """
                    <html>
                    <body>
                    <div style="border: 4px solid ${bannerColor}; padding: 10px;">
                        <h2>${jobName} - Build ${buildNumber}</h2>
                        <div style="background-color: ${bannerColor}; padding: 10px;">
                            <h3 style="color: white;">Pipeline Status: ${pipelineStatus.toUpperCase()}</h3>
                        </div>
                        <p>Check the <a href="${env.BUILD_URL}">console output</a>.</p>
                    </div>
                    </body>
                    </html>
                """

                emailext(
                    subject: "${jobName} - Build ${buildNumber} - ${pipelineStatus.toUpperCase()}",
                    body: body,
                    to: 'itvishal30@gmail.com', // Replace with recipient email address
                    from: 'jenkins@example.com', // Replace with sender email address
                    replyTo: 'itvishal30@gmail.com', // Replace with reply-to email address
                    mimeType: 'text/html'
                )
            }
        }
    }
}
