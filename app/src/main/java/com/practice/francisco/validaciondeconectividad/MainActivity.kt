package com.practice.francisco.validaciondeconectividad

import android.app.DownloadManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import okhttp3.Call
import okhttp3.OkHttpClient
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity(), CompletadoListener {

    override fun descargaCompleta(resultado: String) {
        Log.d("descargaCompleta", resultado)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bValidarRed = findViewById<Button>(R.id.bValidadRed)
        val bSolicitudHTTP = findViewById<Button>(R.id.bSolicitudHTTP)
        val bVolley = findViewById<Button>(R.id.bVolley)
        val bOk = findViewById<Button>(R.id.bOk)

        bValidarRed.setOnClickListener{
            if(Network.hayRed(this)){
                Toast.makeText(this, "Si hay red", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "No hay red", Toast.LENGTH_SHORT).show()
            }
        }

        bSolicitudHTTP.setOnClickListener{
            if(Network.hayRed(this)){
                DescargarURL(this).execute("http://www.google.com")
                //Log.d("bSolicitudOnClick",decargarDatos("http://google.com"))
            }else{
                Toast.makeText(this, "No hay red", Toast.LENGTH_SHORT).show()
            }
        }

        bVolley.setOnClickListener{
            if(Network.hayRed(this)){
                solicitudHTTPVolley("http://www.google.com")
            }else{
                Toast.makeText(this, "No hay red", Toast.LENGTH_SHORT).show()
            }
        }

        bOk.setOnClickListener{
            if(Network.hayRed(this)){

            }else{
                Toast.makeText(this, "No hay red", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Metodo para Volley
    private fun solicitudHTTPVolley(url:String){
        val queue = Volley.newRequestQueue(this)
        val solicitud = StringRequest(Request.Method.GET, url, Response.Listener<String> {
            response ->
            try {
                Log.d("SolicitudHTTPVolley", response)
            }catch (e:Exception){

            }
        }, Response.ErrorListener {  })
        queue.add(solicitud)
    }

    //Metodo para OkHttp
    private fun solicitudHTTPOkHTTP(url: String){
        val cliente = OkHttpClient()
        val solicitud = okhttp3.Request.Builder().url(url).build()

        cliente.newCall(solicitud).enqueue(object : okhttp3.Callback{
            override fun onFailure(call: Call?, e: IOException?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call?, response: okhttp3.Response) {
                val result = response.body().string()

                this@MainActivity.runOnUiThread{
                    try {
                        Log.d("SolicitudHTTPOKHTTP", result)
                    }catch (e: Exception){

                    }
                }
            }

        })
    }
}
