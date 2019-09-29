package config

object Config {
    const val targetSdkVersion = 28
    const val minSdkVersion = 21
    const val applicationID = "ru.test.moviesapp"
    const val buildToolsVersion = "29.0.0"
    const val compileSDKVersion = 28
}

object DepVersions {
    const val retrofit = "2.6.1"
    const val viewModelKtxVersion = "2.1.0"
    const val coroutinesVersion = "1.3.1"
    const val constraintVersion = "1.1.3"
    const val ktxVersion = "1.1.0"
    const val kotlinVersion = "1.3.50"
    const val appCompatVersion = "1.1.0"
    const val junitVersions = "4.12"
    const val daggerVersion = "2.24"
}

object Dependencies {
    const val espressoCore = "androidx.test.espresso:espresso-core:3.2.0"
    const val junitExt = "androidx.test.ext:junit:1.1.1"
    const val junit = "junit:junit:${DepVersions.junitVersions}"
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${DepVersions.kotlinVersion}"
    const val appcompat = "androidx.appcompat:appcompat:${DepVersions.appCompatVersion}"
    const val coreKtx = "androidx.core:core-ktx:${DepVersions.ktxVersion}"
    const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${DepVersions.viewModelKtxVersion}"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:${DepVersions.constraintVersion}"
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${DepVersions.coroutinesVersion}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${DepVersions.coroutinesVersion}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${DepVersions.retrofit}"
    const val retrofitGsonConverterFactory = "com.squareup.retrofit2:converter-gson:${DepVersions.retrofit}"
    const val dagger = "com.google.dagger:dagger:${DepVersions.daggerVersion}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${DepVersions.daggerVersion}"

}