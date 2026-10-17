plugins {
    alias(libs.plugins.android.library)
    `maven-publish`
}

group = "org.connecttag.libs.kotlin"
version = "1.0.2"

android {
    namespace = "org.connecttag.lib.integrity"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    // Google Play Integrity API
    implementation("com.google.android.play:integrity:1.4.0")

    // Kotlin Coroutines
    implementation(libs.kotlinx.coroutines.core)

    // Testing
    testImplementation(libs.junit4)
    testImplementation(libs.kotlinx.coroutines.test)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "org.connecttag.libs.kotlin"
                artifactId = "integrity-play-lib"
                version = "1.0.2"

                pom {
                    name.set("ConnectTag Integrity Play Lib")
                    description.set("Google Play Integrity API manager for requesting and verifying integrity verdict tokens, device environment checks, and fake testing engine")
                    url.set("https://github.com/connecttag/integrity-play-lib")
                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                    developers {
                        developer {
                            id.set("connecttagye")
                            name.set("Connect Tag")
                            email.set("connecttagye@gmail.com")
                        }
                    }
                    scm {
                        connection.set("scm:git:https://github.com/connecttag/integrity-play-lib.git")
                        developerConnection.set("scm:git:ssh://git@github.com:connecttag/integrity-play-lib.git")
                        url.set("https://github.com/connecttag/integrity-play-lib/tree/main")
                    }
                }
            }
        }
    }
}
