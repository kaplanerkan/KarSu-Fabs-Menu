plugins {
    alias(libs.plugins.android.library)
    `maven-publish`
    signing
}

val libraryVersion = "1.1.0"
val libraryGroupId = "io.github.kaplanerkan"
val libraryArtifactId = "karsu-fabs-menu"

android {
    namespace = "karsu.libs.fabsmenu"
    compileSdk = 36

    defaultConfig {
        minSdk = 30

        consumerProguardFiles("proguard-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    lint {
        abortOnError = false
        checkReleaseBuilds = false
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    implementation("androidx.cardview:cardview:1.0.0")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components.findByName("release")!!)

                groupId = libraryGroupId
                artifactId = libraryArtifactId
                version = libraryVersion

                pom {
                    name.set("KarSu FABs Menu")
                    description.set("A customizable Floating Action Button menu library for Android")
                    url.set("https://github.com/kaplanerkan/KarSu-Fabs-Menu")

                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }

                    developers {
                        developer {
                            id.set("kaplanerkan")
                            name.set("Erkan Kaplan")
                            email.set("kaplan.erkan@gmail.com")
                        }
                    }

                    scm {
                        connection.set("scm:git:git://github.com/kaplanerkan/KarSu-Fabs-Menu.git")
                        developerConnection.set("scm:git:ssh://github.com:kaplanerkan/KarSu-Fabs-Menu.git")
                        url.set("https://github.com/kaplanerkan/KarSu-Fabs-Menu")
                    }
                }
            }
        }

        repositories {
            maven {
                name = "staging"
                url = uri(layout.buildDirectory.dir("staging-deploy"))
            }
        }
    }

    signing {
        useGpgCmd()
        sign(publishing.publications["release"])
    }
}

// Task to create bundle for Central Portal upload
tasks.register<Zip>("createMavenCentralBundle") {
    dependsOn("publishReleasePublicationToStagingRepository")

    archiveFileName.set("$libraryArtifactId-$libraryVersion-bundle.zip")
    destinationDirectory.set(layout.buildDirectory.dir("bundle"))

    from(layout.buildDirectory.dir("staging-deploy"))
}
