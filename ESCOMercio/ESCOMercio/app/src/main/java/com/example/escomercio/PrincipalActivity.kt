package com.example.escomercio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_perfil.*
import kotlinx.android.synthetic.main.activity_principal.*

class PrincipalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        productos_list.add(Producto("001A","FunkoPOP",
            "Figuras FunkoPop, con distintos personajes \n-The Flash \n-Batman \nKid Flash",300,R.drawable.pfunko))

        productos_list.add(Producto("002A","Dulces",
            "Buenas tardes compañeros. \n" +
                    "Nuevamente traigo dulces, para que no se duerman en sus clases. :3 \n" +
                    "Llevo a salones y cualquier lugar dentro de la escuela. \n" +
                    "Acepto efectivo y transferencias.",10,R.drawable.pdulces))

        productos_list.add(Producto("003A","Control",
            "Buenas bandicta.\n" +
                    "Vendo DualShock 4 en \$600, lo usé poco tiempo porque la neta me aburrió jugar con el play. \n" +
                    "Manden DM",600,R.drawable.pcontrol))

        productos_list.add(Producto("004A","Termos",
            "Todo a excelente precio y con garantía, pregunta, no te quedes con la duda",150,R.drawable.ptermos))

        productos_list.add(Producto("005A","Sudaderas",
            "HODDIES PASTEL \uD83D\uDC99 \uD83D\uDC9A \uD83D\uDC9C \uD83D\uDC97\n" +
                    "Desde\n" +
                    "\uD83D\uDCB2 490. - c/u\n" +
                    "✨ Personaliza con nombre o carrera sin costo adicional \uD83D\uDE01\n" +
                    "✨ Elige o personaliza con el escudo de tu escuela\n" +
                    "✨ Tallas de Dama y Caballero desde xxch-xxl\n" +
                    "Realiza tu pedido vía WhatsApp al num 5567997947 con Sandy",490,R.drawable.psudaderas))


        rv_productos.layoutManager = LinearLayoutManager(this)
        val adapter = ProductoAdapter(productos_list)
        rv_productos.adapter = adapter

        adapter.onItemClick = {

            val intent = Intent(this,ProductoDetalles::class.java).apply {
                putExtra("Producto",it)
            }

            startActivity(intent)
        }

        boton_perfil.setOnClickListener {

            val user: String = intent.getStringExtra("Usuario").toString()

            val intent = Intent(this,PerfilActivity::class.java).apply {

                putExtra("Usuario",user)
            }

            startActivity(intent)
        }

        boton_ap.setOnClickListener {
            val user: String = intent.getStringExtra("Usuario").toString()

            val intent = Intent(this,AgregarProducto::class.java).apply {

                putExtra("Usuario",user)
            }

            startActivity(intent)
        }


    }
}