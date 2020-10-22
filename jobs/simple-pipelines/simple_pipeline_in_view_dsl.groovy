pipelineJob('simple/simple-pipeline') {
    description("A simple pipeline job")
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
