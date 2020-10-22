folder ("infra")

pipelineJob('infra/seed') {
    description("A simple pipeline job")
    definition {
        cps {
            script("""\
node {
    git 'https://github.com/neuezieljr/jenkins.git'
    files = findFiles(glob: '**/*_dsl.groovy')
    files.each { f ->
        jobName = sh(returnStdout: true, script: "grep -o '@job:.*' ${f.path} | cut -d: -f2").trim()
        viewName = sh(returnStdout: true, script: "grep -o '@view:.*' ${f.path} | cut -d: -f 2").trim()
        jobDsl targets: f.path,
            removedJobAction: 'DELETE',
            lookupStrategy: 'SEED_JOB'
        if (jobName && viewName) {
            instance = Jenkins.getInstance()
            job = instance.getItem(jobName)
            view = instance.getView(viewName)
            if (view && job)
                view.add(job)
        }
    }
}
            """.stripIndent())
            sandbox()
        }
    }
}
