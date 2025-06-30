buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.2")
        classpath("com.android.tools.build:gradle:8.2.1")
    }
}

plugins {
    id("com.google.gms.google-services") version "4.4.2" apply false
    id("com.android.application") version "8.2.1" apply false
    id("com.android.library") version "8.2.1" apply false  //8.3.0
}
