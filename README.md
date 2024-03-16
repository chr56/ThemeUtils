# ThemeUtils


## What's This?

This was once a part of [app-theme-helper](https://github.com/kabouzeid/app-theme-helper).

This is used for [Phonograph Plus](https://github.com/chr56/Phonograph_Plus).

I reorganized and refactor these codes, now this is just library to caculate theme color and handle
view tint.

Besides, [material-tools](https://github.com/chr56/material-tools) is an old version of this library.

## Setup

[![](https://jitpack.io/v/chr56/ThemeUtils.svg)](https://jitpack.io/#chr56/ThemeUtils)

```groovy
dependencies {
    // color-util: calculate colors
    implementation("com.github.chr56.ThemeUtils:color-util:0.1.0")
    // materials-util: most material color values resource (non-kotlin)
    implementation("com.github.chr56.ThemeUtils:materials-util:0.1.0")
    // tint-util: tint the Views
    implementation("com.github.chr56.ThemeUtils:tint-util:0.1.0")

    // It is suggested to add Google material design library if you use tint-util
    implementation("com.google.android.material:material:1.4.0")
}
```

---

## Original README digest

### App Theme Helper

This is basically a copy of [App Theme Engine](https://github.com/afollestad/app-theme-engine)
by [Aidan Follestad](https://github.com/afollestad) which only includes the "Config" part. This
library is only for saving and querying theme values. The user is responsible to use those values (
applying to Views), unlike ATE this library won't automatically theme your views. As an extra this
library includes a few Util methods from ATE and myself to make theming easy.

---