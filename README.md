### Usage ###

`build.gradle`
```groovy
plugins {
    id 'ru.craftcoderr.gradledeploy' version '0.1.5'
}

deploy {
    buildTask = tasks.shadowJar
    artifacts = configurations.shadow.allArtifacts.files
}
```
