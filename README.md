### Usage ###

`build.gradle`
```groovy
plugins {
    id 'ru.craftcoderr.gradledeploy' version '0.2.5-SNAPSHOT'
}

deploy {
    buildTask = tasks.shadowJar
    artifacts = configurations.shadow.allArtifacts.files
}
```
