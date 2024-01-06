package com.example.escomercio

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.util.PatternsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_registrarse.*
import java.util.regex.Pattern

lateinit var image_user: Uri
lateinit var databasereference: DatabaseReference
var REQUESTCODE = 1000

lateinit var USER: String

class RegistrarseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarse)

        val spiner = findViewById<Spinner>(R.id.spinner_escuela)

        val lista = listOf("ESCUELA PRUEBA")

        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,lista)

        spiner.adapter = adapter

        databasereference = FirebaseDatabase.getInstance().getReference("Data_Usuarios")


        foto_user_IV.setOnClickListener{

            val intent = Intent()

            intent.type = "image/*"

            intent.action = Intent.ACTION_GET_CONTENT

            startActivityForResult(intent, REQUESTCODE)

        }

        button_regis_usu.setOnClickListener {

            val usuario_String: String = edit_usuario.text.toString()
            val nombre_String: String = edit_nombre.text.toString()
            val correo_string: String = edit_correo.text.toString()
            val contra_string: String = edit_contra.text.toString()
            val escuela_string:String = spiner.selectedItem.toString()

                comprobarUsuario(usuario_String)
                comprobarNombre(nombre_String)
                comprobarCorreo(correo_string)
                comprobarContra(contra_string)

            if(::image_user.isInitialized){

                if(!(comprobarUsuario(usuario_String) && comprobarNombre(nombre_String) &&
                            comprobarCorreo(correo_string) && comprobarContra(contra_string))
                ){

                    Toast.makeText(this,"Ingrese los datos correctamente",Toast.LENGTH_SHORT).show()

                }else{

                    val user = Usuario(usuario_String,nombre_String,correo_string,escuela_string)

                            FirebaseAuth.getInstance().createUserWithEmailAndPassword(correo_string,contra_string).addOnCompleteListener {

                                if(it.isSuccessful){

                                    Toast.makeText(this,"El usuario se ha registrado correctamente",Toast.LENGTH_SHORT).show()

                                    databasereference.child(usuario_String).setValue(user)


                                    val storage = FirebaseStorage.getInstance().getReference("Usuario_foto/$usuario_String")
                                    storage.putFile(image_user).addOnSuccessListener{

                                        Toast.makeText(this,"Se ha subido la imagen correctamente",Toast.LENGTH_SHORT).show()

                                    }.addOnFailureListener{

                                        Toast.makeText(this,"La imagen no se ha podido cargar",Toast.LENGTH_SHORT).show()

                                    }

                                    vaciarText()

                                    val intent = Intent(this,PrincipalActivity::class.java).apply {

                                        putExtra("Usuario", usuario_String)
                                    }

                                    startActivity(intent)

                                    finish()
                                }


                            }.addOnFailureListener {

                                showAlertProblemUsuario()

                            }


                }

            }else{

                Toast.makeText(this,"No se ha seleccionado ninguna imagen",Toast.LENGTH_LONG).show()
            }

        }


    }

    private fun showAlertProblemUsuario() {

        val builder = AlertDialog.Builder(this)

        builder.setTitle("No se ha podido registrar el usuario")
        builder.setMessage("El usuario no se pudo registrar, recuerda no usar un correo ya registrado")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

    private fun comprobarUsuario(usuario: String) :Boolean{

        databasereference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if(dataSnapshot.hasChild("$usuario")){
                    edit_usuario.error = "El Usuario ya existe"
                }else{
                    USER = usuario
                }

            }

            override fun onCancelled(error: DatabaseError) {


            }
        })

            return if(usuario.isEmpty()){
               // edit_usuario.error = "El Usuario ya existe"
                edit_usuario.error = "Debes ingresar un Usuario"
                false

            }else{
                edit_usuario.error = null
                true
            }
        }

    private fun comprobarNombre(nombre: String) : Boolean{

        val nombreregex = Pattern.compile("""\D+""")

        return if(nombre.isEmpty()){

            edit_nombre.error = "Debes ingresar un nombre"
            false

        }else if(!nombreregex.matcher(nombre).matches()){

            edit_nombre.error = "No puede contener numeros"
            false

        }else{
            edit_nombre.error = null
            true
        }
    }

    private fun vaciarText() {
        foto_user_IV.setImageResource(R.drawable.selimg)
        edit_usuario.setText("")
        edit_nombre.setText("")
        edit_correo.setText("")
        edit_correo.setText("")
        edit_contra.setText("")
    }

    private fun comprobarContra(contra: String) :Boolean{


        return if(contra.isEmpty()){

            edit_contra.error = "Debes ingresar una contraseña"
            false

        }else if(contra.length < 8){

            edit_contra.error = "Debe contener por lo menos 8 caractaeres"
            false

        }else{
            edit_contra.error = null
            true
        }

    }
    private fun comprobarCorreo(correo: String) : Boolean{

        return if(correo.isEmpty()){

            edit_correo.error = "Ingresa un correo"
            false

        }else if(!PatternsCompat.EMAIL_ADDRESS.matcher(correo).matches()){

            edit_correo.error = "Correo no valido"
            false
        }else{
            edit_correo.error = null
            true
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUESTCODE && resultCode == RESULT_OK){
            image_user = data?.data!!
            foto_user_IV.setImageURI(image_user)
        }
    }
    }