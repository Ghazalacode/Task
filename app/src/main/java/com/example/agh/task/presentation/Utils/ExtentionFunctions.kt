package com.example.agh.task.presentation.Utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun Any.logd(){
    Log.d(this::class.java.simpleName, this.toString())
}

fun Any.toast(context: Context, length:Int = Toast.LENGTH_LONG) =
        Toast.makeText(context, this.toString()
                , length).show()

