package org.example.mpp.auth.ext

import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.widgets.screen.Screen

expect fun Screen<*>.openUrl(url: String)
expect fun Screen<*>.showDialog(title: StringDesc, message: StringDesc)