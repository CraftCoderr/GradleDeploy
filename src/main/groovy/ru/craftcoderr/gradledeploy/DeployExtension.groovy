package ru.craftcoderr.gradledeploy

import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles

class DeployExtension {

    Object config = 'deploy.list'
    String artifactNameDelimiter = '-'
    String deployExtension = 'jar'
    Object buildTask = 'build'
    FileCollection artifacts

}
