def folderName = "simple"
folder(folderName) {
    displayName(folderName)
}

pipelineJob('simple/hello-jenkins-pipeline') {
    description("A simple hello world job, pipeline version")
    definition {
        cps {
            script("""\
                node {
                    stage('info')
                    echo 'Hello Jenkins (pipeline)'
                }
            """.stripIndent())
            sandbox()
        }
    }
}
