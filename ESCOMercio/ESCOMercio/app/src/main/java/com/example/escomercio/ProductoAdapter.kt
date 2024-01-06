package com.example.escomercio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_producto.view.*

class ProductoAdapter(val producto: MutableList<Producto>):RecyclerView.Adapter<ProductoAdapter.ProductoHolder>(){

    var onItemClick : ((Producto) -> Unit)? = null

    class ProductoHolder (val view: View):RecyclerView.ViewHolder(view){

        fun render(producto: Producto) {
            view.text_nombrep.text = producto.nombre_producto
            view.text_preciop.text = "$"+producto.precio_producto.toString()
            view.IV_p.setImageDrawable(view.context.getDrawable(producto.imagen_producto))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoHolder {
        val layout = LayoutInflater.from(parent.context)
        return ProductoHolder(layout.inflate(R.layout.item_producto,parent,false))
    }

    override fun onBindViewHolder(holder: ProductoHolder, position: Int) {

        val pro = producto[position]

        holder.render(pro)

        holder.itemView.setOnClickListener {
                onItemClick?.invoke(pro)
        }
    }

    override fun getItemCount(): Int {
        return producto.size
    }

}
