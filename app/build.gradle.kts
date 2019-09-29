import config.Config
import config.Dependencies

plugins{
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
}


android {
    compileSdkVersion(Config.compileSDKVersion)
    buildToolsVersion = Config.buildToolsVersion
    defaultConfig {
        applicationId = Config.applicationID
        minSdkVersion(Config.minSdkVersion)
        targetSdkVersion(Config.targetSdkVersion)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release"){
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    sourceSets{
        getByName("main").java.srcDirs("src/main/kotlin")
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.stdlib)
    implementation(Dependencies.appcompat)
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.constraintlayout)
    implementation(Dependencies.coroutinesCore)
    implementation(Dependencies.coroutinesAndroid)
    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.junitExt)
    androidTestImplementation(Dependencies.espressoCore)
}
