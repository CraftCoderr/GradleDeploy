package ru.craftcoderr.gradledeploy

import org.gradle.api.Plugin
import org.gradle.api.Project

class DeployPlugin implements Plugin<Project> {
    void apply(Project project) {
		project.task("deploy", type: Deploy)
    }
}
