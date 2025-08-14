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
        stage('Compile') {
            steps {
                sh 'mvn compile'
            }
        }
        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube-server') {
                    sh '''$SONARQUBE_HOME/bin/sonar-scanner \
                        -Dsonar.projectKey=Boardgame \
                        -Dsonar.sources=. \
                        -Dsonar.java.binaries=target/classes'''
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
