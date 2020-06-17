package org.example.mpp.auth.ext

import android.app.Activity
import android.content.Intent
import android.provider.ContactsContract
import dev.icerock.moko.widgets.screen.Screen
import kotlin.properties.ReadOnlyProperty

actual fun Screen<*>.registerPhonePickerHandler(
    code: Int,
    handler: (phone: String) -> Unit
): ReadOnlyProperty<Screen<*>, PhonePickerHandler> {
    return registerActivityResultHook(
        requestCode = code,
        value = PhonePickerHandler(code)
    ) { result, data ->
        if (result == Activity.RESULT_OK) {
            val contactUri = data?.data ?: return@registerActivityResultHook

            val contentResolver = requireContext().contentResolver
            val projection = arrayOf(ContactsContract.Contacts._ID)
            val cursor = contentResolver.query(
                contactUri, projection,
                null, null, null
            )

            if (cursor != null && cursor.moveToFirst()) {
                val idIdx = cursor.getColumnIndex(ContactsContract.Contacts._ID)
                val id = cursor.getInt(idIdx)

                val phones = contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null
                )
                if (phones?.moveToFirst() == true) {
                    val numberIdx = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    val number = phones.getString(numberIdx)

                    handler(number)
                }
                phones?.close()
            }
            cursor?.close()
        }
    }
}

actual class PhonePickerHandler(val requestCode: Int)

actual fun Screen<*>.showPhonePicker(pickerHandler: PhonePickerHandler) {
    val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
    startActivityForResult(intent, pickerHandler.requestCode)
}