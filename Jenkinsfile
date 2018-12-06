node {
    stage("test") {
        checkout scm
        echo "${BRANCH_NAME}"
        
        sh "./gradlew sonarqube -Dsonar.host.url=http://localhost:9000 -Dsonar.login=f88fd0a19b4bd4f52a3c7b53dc3eeeb0aac7405f"
    }
}
