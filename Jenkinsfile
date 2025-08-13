 pipeline {
    agent any
    tools {
        maven "Maven3"
        jdk "jdk17"
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
    }
}
