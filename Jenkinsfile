pipeline {
    agent any

    environment {
        REGISTRY = "registry.woowy.ru"
        DEPLOY_USER = "root"
        DEPLOY_HOST = "lifesim.woowy.ru"
        DEPLOY_PATH = "/opt/woowy/lifesim"
        DOCKER_HOST = "unix:///var/run/docker.sock"
        TESTCONTAINERS_DOCKER_SOCKET_OVERRIDE = "/var/run/docker.sock"
        TESTCONTAINERS_RYUK_DISABLED = "true"
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

        stage('Build JAR') {
            steps {
                sh "./gradlew :${env.SERVICE_NAME}:clean bootJar -x test"
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

        stage('Build & Push Docker image') {
            steps {
                script {
                    def image = "${env.REGISTRY}/${env.SERVICE_NAME}:${env.SERVICE_VERSION}"
                    sh "docker build -t ${image} -f ${env.SERVICE_NAME}/Dockerfile ."
                    sh "docker push ${image}"
                }
            }
        }

        stage('Deploy') {
            steps {
                withCredentials([
                    sshUserPrivateKey(
                        credentialsId: 'deploy-ssh-key',
                        keyFileVariable: 'SSH_KEY'
                    )
                ]) {
                    script {
                        def image = "${env.REGISTRY}/${env.SERVICE_NAME}:${env.SERVICE_VERSION}"
                        def serviceDir = getServiceDir(env.SERVICE_NAME)
                        sh """
                            ssh -i $SSH_KEY -o StrictHostKeyChecking=no ${env.DEPLOY_USER}@${env.DEPLOY_HOST} '
                                cd ${env.DEPLOY_PATH}/${serviceDir}
                                export SERVICE_IMAGE=${image}
                                docker compose pull
                                docker compose up -d --no-deps
                                docker images ${env.REGISTRY}/${env.SERVICE_NAME} --format "{{.Tag}}" | grep -v ${env.SERVICE_VERSION} | xargs -I {} docker rmi ${env.REGISTRY}/${env.SERVICE_NAME}:{} 2>/dev/null || true
                            '
                        """
                    }
                }
            }
        }
    }

    post {
        always {
            sh 'docker image prune -f || true'
        }
        success {
            withCredentials([string(credentialsId: 'discord-webhook', variable: 'DISCORD_URL')]) {
                script {
                    def total = 0
                    def failed = 0
                    def passed = 0
                    try {
                        def files = findFiles(glob: '**/build/test-results/test/*.xml')
                        files.each { f ->
                            def xml = readFile(f.path)
                            def m = xml =~ /tests="(\d+)"/
                            if (m) total += m[0][1].toInteger()
                            def fm = xml =~ /failures="(\d+)"/
                            if (fm) failed += fm[0][1].toInteger()
                            def em = xml =~ /errors="(\d+)"/
                            if (em) failed += em[0][1].toInteger()
                        }
                        passed = total - failed
                    } catch (e) {}
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
                                    {"name": "Tests", "value": "✅ ${passed}/${total} passed", "inline": true},
                                    {"name": "Image", "value": "${env.REGISTRY}/${env.SERVICE_NAME}:${env.SERVICE_VERSION}", "inline": false},
                                    {"name": "Test Results", "value": "${env.BUILD_URL}testReport", "inline": false},
                                    {"name": "URL", "value": "${env.BUILD_URL}", "inline": false}
                                ]
                            }]
                        }' \
                        \$DISCORD_URL
                    """
                }
            }
        }
        failure {
            withCredentials([string(credentialsId: 'discord-webhook', variable: 'DISCORD_URL')]) {
                script {
                    def total = 0
                    def failed = 0
                    def passed = 0
                    try {
                        def files = findFiles(glob: '**/build/test-results/test/*.xml')
                        files.each { f ->
                            def xml = readFile(f.path)
                            def m = xml =~ /tests="(\d+)"/
                            if (m) total += m[0][1].toInteger()
                            def fm = xml =~ /failures="(\d+)"/
                            if (fm) failed += fm[0][1].toInteger()
                            def em = xml =~ /errors="(\d+)"/
                            if (em) failed += em[0][1].toInteger()
                        }
                        passed = total - failed
                    } catch (e) {}
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
                                    {"name": "Tests", "value": "❌ ${passed}/${total} passed (${failed} failed)", "inline": true},
                                    {"name": "Test Results", "value": "${env.BUILD_URL}testReport", "inline": false},
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