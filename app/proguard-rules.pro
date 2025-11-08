# --- Compose rule ---
-dontwarn androidx.compose.**
-keep class androidx.compose.** { *; }
-keep class androidx.activity.compose.** { *; }

# --- Material3 & UI ---
-keep class androidx.compose.material3.** { *; }
-keep class androidx.compose.ui.tooling.preview.PreviewParameterProvider { *; }

# --- Iban4j ---
-keep class org.iban4j.** { *; }

# --- MLKit & Play Services ---
-keep class com.google.mlkit.** { *; }
-keep interface com.google.mlkit.** { *; }
-dontwarn com.google.mlkit.**

# --- Reflection / Kotlin ---
-keepattributes *Annotation*
-keep class kotlin.Metadata { *; }

# --- App entry point ---
-keep class com.mohammadsml.cardscanner.** { *; }

# --- Reduce logs ---
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}

# --- Clean up ---