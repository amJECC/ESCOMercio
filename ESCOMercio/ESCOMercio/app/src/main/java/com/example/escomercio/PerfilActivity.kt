package com.example.escomercio

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.disklrucache.DiskLruCache
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_perfil.*
import kotlinx.android.synthetic.main.activity_registro.*
import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnFailureListener

import com.google.android.gms.tasks.OnSuccessListener




class PerfilActivity : AppCompatActivity() {
    private lateinit var dbreference : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        dbreference = FirebaseDatabase.getInstance().getReference("Data_Usuarios")

        val usuario: String = intent.getStringExtra("Usuario").toString()

        user_user.setText(usuario)

        setnombre(usuario)
        setcorreo(usuario)
        setescuela(usuario)

        imagen_user_perfil.setImageResource(R.drawable.p_user)

    }

    private fun setescuela(usuario: String) {
        dbreference.child("$usuario").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if(dataSnapshot.exists()){
                    val correo_ingresar = dataSnapshot.child("escuela_user").getValue().toString()
                    escuela_user.setText(correo_ingresar)
                }

            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun setnombre(usuario: String) {
        dbreference.child("$usuario").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if(dataSnapshot.exists()){
                    val correo_ingresar = dataSnapshot.child("nombre_user").getValue().toString()
                    nombre_user.setText(correo_ingresar)
                }

            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun setcorreo(usuario: String) {
        dbreference.child("$usuario").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if(dataSnapshot.exists()){
                    val correo_ingresar = dataSnapshot.child("correo_user").getValue().toString()
                    correo_user.setText(correo_ingresar)
                }

            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}
