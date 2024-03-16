plugins {

}

tasks.create(name = "clean", type = Delete::class) {
    doLast { delete(rootProject.layout.buildDirectory) }
}
