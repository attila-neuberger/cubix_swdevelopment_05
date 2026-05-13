pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK-21'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean compile'
            }
        }

        stage('Unit Tests + Coverage') {
            steps {
                bat 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'

                    recordCoverage(
                        tools: [[parser: 'JACOCO', pattern: '**/target/site/jacoco/jacoco.xml']],
                        sourceCodeRetention: 'EVERY_BUILD',
                        qualityGates: [
                            [threshold: 50.0, metric: 'LINE', baseline: 'PROJECT', criticality: 'FAILURE'],
                            [threshold: 80.0, metric: 'LINE', baseline: 'PROJECT', criticality: 'UNSTABLE']
                        ]
                    )
                }
            }
        }

        stage('Checkstyle') {
            steps {
                bat 'mvn checkstyle:checkstyle'
            }
            post {
                always {
                    recordIssues(
                        tools: [checkStyle(pattern: '**/target/checkstyle-result.xml')],
                        qualityGates: [
                            [threshold: 50, type: 'TOTAL', criticality: 'FAILURE']
                        ]
                    )
                }
            }
        }

        stage('Archive') {
            steps {
                bat 'mvn package -DskipTests'
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            }
        }
    }

    post {
        success {
            echo 'Pipeline done!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
