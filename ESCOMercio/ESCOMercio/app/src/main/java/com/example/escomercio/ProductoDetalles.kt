package com.example.escomercio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_producto_detalles.*

class ProductoDetalles : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto_detalles)

        val Producto_detalle = intent.getParcelableExtra<Producto>("Producto")

        tv_np.setText(Producto_detalle?.nombre_producto)
        tv_dp.setText(Producto_detalle?.descripcion_producto)
        tv_pp.setText("$"+Producto_detalle?.precio_producto.toString()+".00")
        tv_idp.setText("ID: " + Producto_detalle?.id_producto)
        if (Producto_detalle != null) {
            IV_pd.setImageResource(Producto_detalle.imagen_producto)
        }

    }
}