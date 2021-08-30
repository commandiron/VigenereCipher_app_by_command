package com.demirli.a31vigenerecipher

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var alphabetList: List<Char>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        alphabetList = listOf('A','B','C','D','E','F','G','H','I','J','K','L','M'
            ,'N','O','P','Q','R','S','T','U','V','W','X','Y','Z')

        setInputFilterForEditText()

        encrypt_btn.setOnClickListener {

            val key = generateKey(input_et.text.toString(),encryptionKey_et.text.toString())
            val cipherText = getCipherText(input_et.text.toString(),key)

            result_et.setText(cipherText)

            input_et.setText("")
            encrypt_btn.visibility = View.GONE
            decrypt_btn.visibility = View.VISIBLE
        }

        decrypt_btn.setOnClickListener {

            val key = generateKey(result_et.text.toString(), encryptionKey_et.text.toString())
            val originalText = getOriginalText(result_et.text.toString(), key)

            input_et.setText(originalText)

            result_et.setText("")
            encrypt_btn.visibility = View.VISIBLE
            decrypt_btn.visibility = View.GONE
        }
    }

    fun setInputFilterForEditText(){
        input_et.filters = arrayOf(object: InputFilter.AllCaps() {
        })
        result_et.filters = arrayOf(object: InputFilter.AllCaps() {
        })
        encryptionKey_et.filters = arrayOf(object: InputFilter.AllCaps() {
        })
    }

    fun generateKey(str: String, key: String): String{
        val x = str.length
        var key = key

        for(i in 0 until x){
            if(key.length != str.length){
                key += key[i]
            }
        }
        return key
    }

    fun getCipherText(str: String, key: String): String {
        var cipherText = StringBuilder()

        for(i in 0 until str.length){

            var x: Int = (str[i].toInt() + key[i].toInt()) %26

            println(x)

            cipherText.append(alphabetList[x])
        }
        return cipherText.toString()
    }

    fun getOriginalText(cipher_text: String, key: String): String{
        var originalText = StringBuilder()

        for (i in 0 until cipher_text.length){

            var x: Int = (cipher_text[i].toInt() - key[i].toInt() + 26) %26

            originalText.append(alphabetList[x])
        }
        return originalText.toString()
    }

}
