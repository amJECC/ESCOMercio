package com.example.escomercio

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_agregar_producto.*
import kotlinx.android.synthetic.main.activity_registrarse.*
import kotlinx.android.synthetic.main.activity_registro.*

var REQUESTCODE1 = 1001
lateinit var image_producto: Uri

class AgregarProducto : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_producto)

        val usuario: String = intent.getStringExtra("Usuario").toString()


        IV_selP.setOnClickListener {

            val intent = Intent()

            intent.type = "image/*"

            intent.action = Intent.ACTION_GET_CONTENT

            startActivityForResult(intent, REQUESTCODE1)

        }

        boton_agregarp.setOnClickListener {

            val nombrep = et_np.text.toString()
            val descrip = et_dp.text.toString()
            val precio = et_pp.text.toString()

            verificarnombre(nombrep)
            verificardescripcion(descrip)
            verificarprecio(precio)

            if(::image_producto.isInitialized) {

                if (!(verificarnombre(nombrep) && verificardescripcion(descrip) &&
                            verificarprecio(precio))
                ) {

                    Toast.makeText(this, "Ingrese los datos correctamente", Toast.LENGTH_SHORT)
                        .show()

                } else {

                    productos_list.add(Producto("$usuario" + "$nombrep", "$nombrep", "$descrip", precio.toInt(), image_producto.port)
                    )

                    finish()
                }
            } else {

                Toast.makeText(this,"No se ha seleccionado ninguna imagen",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun verificarnombre(nombrep: String): Boolean {

        return if(nombrep.isEmpty()){

            et_np.error = "Debes ingresar un nombre"
            false

        }
        else{
            et_np.error = null
            true
        }

    }
    private fun verificardescripcion(descrip: String) : Boolean{
        return if(descrip.isEmpty()){

            et_dp.error = "Debes ingresar una descripcion"
            false

        }
        else{
            et_dp.error = null
            true
        }

    }
    private fun verificarprecio(precio: String) : Boolean {
        return if(precio.isEmpty()){

            et_pp.error = "Debes ingresar un precio"
            false

        }
        else{
            et_pp.error = null
            true
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUESTCODE1 && resultCode == RESULT_OK){
            image_producto = data?.data!!
            IV_selP.setImageURI(image_producto)
        }
    }
}