package com.example.escomercio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.util.PatternsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_registrarse.*
import kotlinx.android.synthetic.main.activity_registro.*
import kotlinx.coroutines.flow.callbackFlow

public val productos_list: MutableList<Producto> = mutableListOf()

class RegistroActivity : AppCompatActivity() {
    private lateinit var dbreference : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        dbreference = FirebaseDatabase.getInstance().getReference("Data_Usuarios")

        button_ingresar.setOnClickListener {

            val user: String = edit_user_in.text.toString()
            val contra: String = edit_contra_in.text.toString()

            comprobarContra(contra)
            comprobarUsuario(user,contra)
        }

        button_registrarse.setOnClickListener {
            val intent = Intent(this, RegistrarseActivity::class.java)

            startActivity(intent)
        }
    }

    private fun ingresar(user: String, correo: String, contrasenia : String){

        FirebaseAuth.getInstance().signInWithEmailAndPassword(correo,contrasenia).addOnCompleteListener {

            if(it.isSuccessful){

                val intent = Intent(this,VerificarGPS::class.java).apply {

                    putExtra("Usuario",user)
                }

                startActivity(intent)

                finish()
            }

        }.addOnFailureListener {

            showAlertProblemUsuario()

        }
    }

    private fun getcorreo(usuario: String, contra: String) {
        dbreference.child("$usuario").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if(dataSnapshot.exists()){
                    val correo_ingresar = dataSnapshot.child("correo_user").getValue().toString()
                    ingresar(usuario, correo_ingresar,contra)

                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


    private fun comprobarContra(contra: String) :Boolean{


        return if(contra.isEmpty()){

            edit_contra_in.error = "Debes ingresar una contraseña"
            false

        }else if(contra.length < 8){

            edit_contra_in.error = "Debe contener por lo menos 8 caractaeres"
            false

        }else{
            edit_contra_in.error = null
            true
        }

    }
    private fun comprobarUsuario(usuario: String, contra: String) :Boolean{

        dbreference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if(dataSnapshot.hasChild("$usuario")){
                    getcorreo(usuario, contra)
                }else{
                    edit_user_in.error = "No se ha encontrado el usuario"
                }

            }

            override fun onCancelled(error: DatabaseError) {


            }
        })

        return if(usuario.isEmpty()){
            // edit_usuario.error = "El Usuario ya existe"
            edit_user_in.error = "Debes ingresar un Usuario"
            false

        }else{
            edit_user_in.error = null
            true
        }
    }

    private fun showAlertProblemUsuario() {

        val builder = AlertDialog.Builder(this)

        builder.setTitle("No se ha podido Ingresar")
        builder.setMessage("Comprueba que las credenciales sean correctas")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }
}