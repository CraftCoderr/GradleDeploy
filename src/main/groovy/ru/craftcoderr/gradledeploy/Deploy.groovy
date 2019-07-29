package ru.craftcoderr.gradledeploy

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

class Deploy extends DefaultTask {

    Object config = 'deploy.list'
    String artifactNameDelimiter = '-'
    String deployExtension = 'jar'

    @TaskAction
    void deployArtifacts() {
        String[] paths = project.file(config).readLines()
        project.configurations.shadow.allArtifacts.each { artifact ->
            String name = artifact.file.name.split(artifactNameDelimiter)[0] + '.' + deployExtension
            paths.each { path ->
                Path deployPath = project.file(path).toPath().resolve(name)
                try {
                    long start = System.currentTimeMillis()
                    Files.copy(artifact.file.toPath(), deployPath, StandardCopyOption.REPLACE_EXISTING)
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