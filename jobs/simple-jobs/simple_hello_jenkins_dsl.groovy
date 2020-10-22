/* 
 * @job: simple/hello-jenkins
 * @view: simple-projects
 */
def folderName = "simple"
folder(folderName) {
    displayName(folderName)
}

job('simple/hello-jenkins') {
    description("Simple hello world job")
    steps {
        shell("echo Hello Jenkins")
    }
    publishers {
    }
}
