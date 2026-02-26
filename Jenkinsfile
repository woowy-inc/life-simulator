pipeline {
    agent any

    environment {
        REGISTRY = "ghcr.io/dnartysh"
        DEPLOY_USER = "root"
        DEPLOY_HOST = "lifesim.woowy.ru"
        DEPLOY_PATH = "/opt/woowy/lifesim"
    }

    stages {
        stage('Detect service') {
            steps {
                script {
                    def tag = env.REF ?: ''

                    def matcher = tag =~ /^(.+-service)\/(v.+)$/
                    if (!matcher.matches()) {
                        currentBuild.result = 'NOT_BUILT'
                        error("'${tag}' is not a valid service tag, skipping")
                    }

                    env.SERVICE_NAME = matcher[0][1]
                    env.SERVICE_VERSION = matcher[0][2]
                    echo "Service: ${env.SERVICE_NAME}, Version: ${env.SERVICE_VERSION}"
                }
            }
        }

        stage('Test') {
            steps {
                sh "./gradlew :${env.SERVICE_NAME}:test"
            }
            post {
                always {
                    junit "**/build/test-results/test/*.xml"
                }
            }
        }

        stage('Build JAR') {
            steps {
                sh "./gradlew :${env.SERVICE_NAME}:clean bootJar -x test"
            }
        }

        stage('Build & Push Docker image') {
            steps {
                script {
                    def image = "${env.REGISTRY}/${env.SERVICE_NAME}:${env.SERVICE_VERSION}"
                    withCredentials([usernamePassword(
                        credentialsId: 'ghcr-credentials',
                        usernameVariable: 'GHCR_USER',
                        passwordVariable: 'GHCR_TOKEN'
                    )]) {
                        sh "echo $GHCR_TOKEN | docker login ghcr.io -u $GHCR_USER --password-stdin"
                        sh "docker build -t ${image} -f ${env.SERVICE_NAME}/Dockerfile ."
                        sh "docker push ${image}"
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                withCredentials([
                    sshUserPrivateKey(
                        credentialsId: 'deploy-ssh-key',
                        keyFileVariable: 'SSH_KEY'
                    ),
                    usernamePassword(
                        credentialsId: 'ghcr-credentials',
                        usernameVariable: 'GHCR_USER',
                        passwordVariable: 'GHCR_TOKEN'
                    )
                ]) {
                    script {
                        def image = "${env.REGISTRY}/${env.SERVICE_NAME}:${env.SERVICE_VERSION}"
                        def serviceDir = getServiceDir(env.SERVICE_NAME)
                        sh """
                            ssh -i $SSH_KEY -o StrictHostKeyChecking=no ${env.DEPLOY_USER}@${env.DEPLOY_HOST} '
                                echo $GHCR_TOKEN | docker login ghcr.io -u $GHCR_USER --password-stdin
                                cd ${env.DEPLOY_PATH}/${serviceDir}
                                export SERVICE_IMAGE=${image}
                                docker compose pull
                                docker compose up -d --no-deps
                                docker images ghcr.io/dnartysh/${env.SERVICE_NAME} --format "{{.Tag}}" | grep -v ${env.SERVICE_VERSION} | xargs -I {} docker rmi ghcr.io/dnartysh/${env.SERVICE_NAME}:{} 2>/dev/null || true
                                docker logout ghcr.io
                            '
                        """
                    }
                }
            }
        }
    }

    post {
        always {
            sh 'docker logout ghcr.io || true'
            sh 'docker image prune -f || true'
        }
        success {
            withCredentials([string(credentialsId: 'discord-webhook', variable: 'DISCORD_URL')]) {
                sh """
                    curl -H "Content-Type: application/json" \
                    -X POST \
                    -d '{
                        "embeds": [{
                            "title": "✅ Backend Deploy Successful",
                            "color": 3066993,
                            "fields": [
                                {"name": "Service", "value": "${env.SERVICE_NAME}", "inline": true},
                                {"name": "Version", "value": "${env.SERVICE_VERSION}", "inline": true},
                                {"name": "Build", "value": "#${env.BUILD_NUMBER}", "inline": true},
                                {"name": "Image", "value": "ghcr.io/dnartysh/${env.SERVICE_NAME}:${env.SERVICE_VERSION}", "inline": false},
                                {"name": "URL", "value": "${env.BUILD_URL}", "inline": false}
                            ]
                        }]
                    }' \
                    \$DISCORD_URL
                """
            }
        }
        failure {
            withCredentials([string(credentialsId: 'discord-webhook', variable: 'DISCORD_URL')]) {
                sh """
                    curl -H "Content-Type: application/json" \
                    -X POST \
                    -d '{
                        "embeds": [{
                            "title": "❌ Backend Deploy Failed",
                            "color": 15158332,
                            "fields": [
                                {"name": "Service", "value": "${env.SERVICE_NAME}", "inline": true},
                                {"name": "Version", "value": "${env.SERVICE_VERSION}", "inline": true},
                                {"name": "Build", "value": "#${env.BUILD_NUMBER}", "inline": true},
                                {"name": "Logs", "value": "${env.BUILD_URL}console", "inline": false}
                            ]
                        }]
                    }' \
                    \$DISCORD_URL
                """
            }
        }
    }
}

def getServiceDir(String serviceName) {
    def dirs = [
        'auth-service'        : 'auth',
        'gateway-service'     : 'gateway',
        'eureka-service'      : 'eureka',
        'notification-service': 'notification'
    ]
    def dir = dirs[serviceName]
    if (!dir) error("Unknown service: ${serviceName}")
    return dir
}