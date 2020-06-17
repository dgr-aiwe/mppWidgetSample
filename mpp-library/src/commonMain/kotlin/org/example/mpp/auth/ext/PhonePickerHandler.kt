package org.example.mpp.auth.ext

import dev.icerock.moko.widgets.screen.Screen
import kotlin.properties.ReadOnlyProperty

expect class PhonePickerHandler

expect fun Screen<*>.registerPhonePickerHandler(
    code: Int,
    handler: (phone: String) -> Unit
) : ReadOnlyProperty<Screen<*>, PhonePickerHandler>

expect fun Screen<*>.showPhonePicker(
    pickerHandler: PhonePickerHandler
)
