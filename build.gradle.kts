plugins {

}

tasks.create(name = "clean", type = Delete::class) {
    doLast { delete(rootProject.layout.buildDirectory) }
}

val libVersion: String by extra("0.0.1")
val libGroup: String by extra("com.github.chr56")
val libNamespacePrefix: String by extra("util.theme")