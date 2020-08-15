package ru.craftcoderr.gradledeploy

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.Task
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.TaskAction

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

class Deploy extends DefaultTask {

    @Input
    Object config = 'deploy.list'
    @Input
    String artifactNameDelimiter = '-'
    @Input
    String deployExtension = 'jar'
    @Input
    Task buildTask = project.tasks.build
    @InputFiles
    FileCollection artifacts = project.configurations.default.allArtifacts.files

    @Override
    Task configure(Closure closure) {
        dependsOn(buildTask)
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
                    throw new GradleException("Deploy " + artifact.name + " to " + deployPath +
                            " failed! Maybe deploy path is invalid or file alredy in use? Error: " + e.getMessage())
                }
            }
        }
    }

}