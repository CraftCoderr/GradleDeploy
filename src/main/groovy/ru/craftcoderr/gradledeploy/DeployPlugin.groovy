package ru.craftcoderr.gradledeploy

import org.gradle.api.Plugin
import org.gradle.api.Project

class DeployPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.extensions.add('deploy', DeployExtension)
        project.getTasks().register('deploy', Deploy)
    }
}
