object Versions {
    const val KOTLIN_VERSION = "1.6.10"
    const val KOTLINX_COROUTINES = "1.6.10"
    const val BUILD_GRADLE = "4.2.1"
    const val COMPILE_SDK_VERSION = 31
    const val BUILD_TOOLS_VERSION = "30.0.3"
    const val MIN_SDK_VERSION = 23
    const val TARGET_SDK_VERSION = 30
    const val CORE_KTX = "1.5.0"
    const val APP_COMPAT = "1.3.0"
    const val ACTIVITY_KTX = "1.2.3"
    const val FRAGMENT_KTX = "1.3.4"
    const val LIFECYCLE_KTX = "2.3.1"
    const val ROOM = "2.3.0"
    const val HILT = "2.35.1"
    const val MATERIAL = "1.3.0"
    const val RETROFIT = "2.7.1"
    const val JUNIT = "4.13.2"
    const val OKHTTP = "4.3.1"
    const val ANDROID_JUNIT = "1.1.2"
    const val ESPRESSO_CORE = "3.3.0"
    const val CONSTRAINTLAYOUT = "2.0.4"
    const val LEGACY_SUPPORT = "1.0.0"
    const val NAVIGATION = "2.3.5"
    const val GOOGLE_AUTH = "19.2.0"
    const val DATASTORE = "1.0.0"
    const val RX_BINDING = "4.0.0"
    const val RX_JAVA = "3.0.6"
    const val RX = "3.0.0"
    const val PAGING = "3.1.0"


}

object Kotlin {
    const val KOTLIN_STDLIB =
        "org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN_VERSION}"
    const val COROUTINES_CORE =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KOTLINX_COROUTINES}"
    const val CORE_KTX =
        "androidx.core:core-ktx:${Versions.KOTLIN_VERSION}"
    const val COROUTINES_ANDROID =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.KOTLINX_COROUTINES}"
}


object AndroidX {
    const val CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"
    const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"
    const val CONSTRAINTLAYOUT =
        "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINTLAYOUT}"
    const val LEGACY_SUPPORT = "androidx.legacy:legacy-support-v4:${Versions.LEGACY_SUPPORT}"
    const val NAVIGATION_FRAGMENT =
        "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION}"
    const val NAVIGATION_SUPPORT =
        "androidx.navigation:navigation-ui-ktx${Versions.NAVIGATION}"
    const val ACTIVITY_KTX = "androidx.activity:activity-ktx:${Versions.ACTIVITY_KTX}"
    const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${Versions.FRAGMENT_KTX}"
    const val LIFECYCLE_VIEWMODEL_KTX =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE_KTX}"
    const val LIFECYCLE_LIVEDATA_KTX =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIFECYCLE_KTX}"
    const val ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.ROOM}"
    const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM}"
    const val ROOM_COMPILER =
        "androidx.room:room-compiler:${Versions.ROOM}"
    const val DATASTORE = "androidx.datastore:datastore-preferences:${Versions.DATASTORE}"
}

object Google {
    const val HILT_ANDROID = "com.google.dagger:hilt-android:${Versions.HILT}"
    const val HILT_ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
    const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
    const val GOOGLE_AUTH = "com.google.android.gms:play-services-auth:${Versions.GOOGLE_AUTH}"
}

object RX {
    const val RX_ANDROID = "io.reactivex.rxjava3:rxandroid:${Versions.RX}"
    const val RX_KOTLIN = "io.reactivex.rxjava3:rxandroid:${Versions.RX}"
    const val RX_BINDING = "io.reactivex.rxjava3:rxandroid:${Versions.RX_BINDING}"
    const val RX_JAVA = "io.reactivex.rxjava3:rxandroid:${Versions.RX_JAVA}"
}

object Libraries {
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val RETROFIT_CONVERTER_GSON =
        "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"

    const
    val OKHTTP = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP}"

    const
    val OKHTTP_LOGGING_INTERCEPTOR =
        "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}"
    const val PAGING_RUNTIME = ("androidx.paging:paging-runtime:${Versions.PAGING}")
    const val PAGING_GUAVA = ("androidx.paging:paging-guava:${Versions.PAGING}")
}

object UnitTest {
    const val JUNIT = "junit:junit:${Versions.JUNIT}"
}

object AndroidTest {
    const val ANDROID_JUNIT = "androidx.test.ext:junit:${Versions.ANDROID_JUNIT}"

    const
    val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
}


