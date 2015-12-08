# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\NEEL\AppData\Local\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
# This example uses Picasso, see: https://github.com/square/picasso/blob/master/README.md

-dontwarn com.squareup.okhttp.**

-dontwarn org.apache.http.**
-dontwarn android.net.http.**
-keep class com.google.android.gms.** { *; }
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient
-dontwarn com.google.android.gms.**

# Required by IndoorAtlas SDK
-keep public class com.indooratlas.algorithm.ClientProcessingManager { *; }
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}