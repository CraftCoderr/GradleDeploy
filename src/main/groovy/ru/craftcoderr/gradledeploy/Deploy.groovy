package ru.craftcoderr.gradledeploy

import org.gradle.api.DefaultTask
import org.gradle.api.Task
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.TaskAction

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

class Deploy extends DefaultTask {

    Object config = 'deploy.list'
    String artifactNameDelimiter = '-'
    String deployExtension = 'jar'
    Object buildTask
    FileCollection artifacts

    Deploy() {
        super()
        buildTask = project.tasks.getByName('build')
        artifacts = project.configurations.getByName('default').allArtifacts.files
        configure {}
    }

    @Override
    Task configure(Closure closure) {
        super.configure(closure)
        getDependsOn().clear()
        dependsOn(buildTask)
        return this
    }

    @TaskAction
    void deployArtifacts() {
        String[] paths = project.file(config).readLines()
        artifacts.each { artifact ->
            String name = artifact.name.split(artifactNameDelimiter)[0] + '.' + deployExtension
            paths.each { path ->
                Path deployPath = project.file(path).toPath().resolve(name)
                try {
                    long start = System.currentTimeMillis()
                    Files.copy(artifact.toPath(), deployPath, StandardCopyOption.REPLACE_EXISTING)
                    long time = System.currentTimeMillis() - start
                    println(artifact.name + " deployed to " + deployPath + " successfully in "
                            + Integer.toString(time / 1000 as int) + "." + Integer.toString(time / 100 as int) + "s")
                } catch (Exception e) {
                    println("Deploy " + artifact.name + " to " + deployPath +
                            " failed! Maybe deploy path is invalid or file alredy in use? Error: " + e.getMessage())
                }
            }
        }
    }

}