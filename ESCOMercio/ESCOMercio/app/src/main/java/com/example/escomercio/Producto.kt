package com.example.escomercio

import android.os.Parcel
import android.os.Parcelable

data class Producto(
    val id_producto : String,
    val nombre_producto:String,
    val descripcion_producto: String,
    val precio_producto:Int,
    val imagen_producto:Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id_producto)
        parcel.writeString(nombre_producto)
        parcel.writeString(descripcion_producto)
        parcel.writeInt(precio_producto)
        parcel.writeInt(imagen_producto)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Producto> {
        override fun createFromParcel(parcel: Parcel): Producto {
            return Producto(parcel)
        }

        override fun newArray(size: Int): Array<Producto?> {
            return arrayOfNulls(size)
        }
    }
}