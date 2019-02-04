package com.practice.francisco.validaciondeconectividad

import android.os.AsyncTask
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class DescargarURL(var completadoListener: CompletadoListener?): AsyncTask<String, Void, String>() {

    override fun doInBackground(vararg params: String): String? {
        try {
            return decargarDatos(params[0])
        }catch (e:IOException){
            return null
        }
    }

    override fun onPostExecute(result: String) {
        try {
            completadoListener?.descargaCompleta(result)
        }catch (e:Exception){

        }
    }

    @Throws(IOException::class)
    private fun decargarDatos(url:String):String{

        var inputSteam: InputStream? = null

        try {
            val url = URL(url)
            val connect = url.openConnection() as HttpURLConnection
            connect.requestMethod = "GET"
            connect.connect()

            inputSteam = connect.inputStream
            return inputSteam.bufferedReader().use {
                it.readText()
            }
        } finally {
            if (inputSteam != null){
                inputSteam.close()
            }
        }
    }

}