node {
    stage("test") {
        sh "./gradle wrapper --gradle-version 2.4"
        checkout scm
        echo "${BRANCH_NAME}"
        
        sh "./gradlew sonarqube -Dsonar.host.url=http://localhost:9000 -Dsonar.login=f88fd0a19b4bd4f52a3c7b53dc3eeeb0aac7405f"
    }
}
