package com.example.neighborss.fragment

import android.content.Context
import android.net.SocketKeepalive
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.neighborss.NavigationListener
import com.example.neighborss.R
import com.example.neighborss.adapters.ListNeighborHandler
import com.example.neighborss.dto.NeighborDTO
import com.example.neighborss.models.Neighbor
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.function.Predicate

class AddNeighbourFragment(private val handler: ListNeighborHandler, private val listener: NavigationListener, private val neighbor: Neighbor?) : Fragment() {

    private lateinit var register : Button
    private lateinit var image : TextInputEditText
    private lateinit var name : TextInputEditText
    private lateinit var tel : TextInputEditText
    private lateinit var web : TextInputEditText
    private lateinit var mail : TextInputEditText
    private lateinit var me : TextInputEditText
    private lateinit var icon : ImageView

    private val modeEdit = neighbor != null

    private val model: NeighborDTO = if(neighbor == null) NeighborDTO("", "", "", "", "", "", null)
                                     else NeighborDTO(neighbor.name, neighbor.avatarUrl, neighbor.address, neighbor.phoneNumber, neighbor.aboutMe, neighbor.webSite, neighbor.id)

    private val validators = mutableListOf<Validator>();



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_neighbor, container, false)

        register = view.findViewById(R.id.add)
        image = view.findViewById(R.id.createImage)
        name = view.findViewById(R.id.createName)
        tel = view.findViewById(R.id.createTel)
        web = view.findViewById(R.id.createWeb)
        mail = view.findViewById(R.id.createMail)
        me = view.findViewById(R.id.createMe)
        icon = view.findViewById(R.id.person)

        initValidator()

        if (modeEdit){
            name.setText(model.name)
            image.setText(model.avatarUrl)
            tel.setText(model.phoneNumber)
            web.setText(model.webSite)
            mail.setText(model.address)
            me.setText(model.aboutMe)

            profileImage()
            buttonEnabled()
        }


        image.doAfterTextChanged {
            model.avatarUrl = it.toString();
            profileImage()
            buttonEnabled()
            //validateUrl(model.avatarUrl, image)
        }

        name.doAfterTextChanged {

            model.name = it.toString();
            buttonEnabled()
        }

        tel.doAfterTextChanged {
            model.phoneNumber = it.toString();
            buttonEnabled()
            //validateTel()
        }

        web.doAfterTextChanged {

            model.webSite = it.toString();
            buttonEnabled()

            //validateUrl(model.webSite, web)
        }

        mail.doAfterTextChanged {

            model.address = it.toString();
            buttonEnabled()
            //validateMail(model.address, mail)
        }

        me.doAfterTextChanged {
            model.aboutMe = it.toString()
            buttonEnabled()
        }



        registerButton()

        setTitle()

        return view;

    }

    private fun initValidator() {

        validators += Validator("Url non valide", { it.isNotEmpty() && Patterns.WEB_URL.matcher(it).matches() }, image);
        validators += Validator("Url non valide", { it.isNotEmpty() && Patterns.WEB_URL.matcher(it).matches() }, web);
        validators += Validator("Mail non valide", { it.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(it).matches() }, mail)
        validators += Validator("Numero non valide", {it.isNotEmpty() && validateTel(it) }, tel)
        validators += Validator("Description non valide", { it.isNotEmpty() && it.length <= 30 }, me)
        validators += Validator("Champ requis", {it.isNotEmpty()}, name)

    }

    /*fun validateUrl(url : String, input: TextInputEditText){
        if (!URLUtil.isValidUrl(url)){
            input.error="Url non valide"
        }
    }

    fun validateMail(mailAdress : String, input: TextInputEditText){
        val isValidated = Patterns.EMAIL_ADDRESS.matcher(mailAdress).matches()
        if(! isValidated){
            input.error="Mail non valide"
        }
    }*/

    fun validateTel(numTel: String): Boolean{
        return (numTel.startsWith("06") || numTel.startsWith("07")) && (numTel.length == 10)
    }

    fun registerButton(){
        register.isEnabled= false;
        register.setOnClickListener {
            if(!modeEdit){
                handler.onCreateNeibor(model);
            }
            else{
                handler.onUpdateNeibor(model)
            }

            Toast.makeText(context, "Bien enregistré!", Toast.LENGTH_LONG).show()
            (activity as? NavigationListener)?.let {
                it.showFragment(ListNeighborsFragment(listener, ))}
        }
    }

    fun buttonEnabled(){

        var enable = true;

        for(validator in validators){
            val isValidated = validator.validate();
            if(!isValidated){
                enable = false;
            }
        }
        register.isEnabled = enable;


        //register.isEnabled = validators.map { validator -> validator.validate() }.any { !it };



       /* if(!getStringOfEditText(name).isEmpty() && !getStringOfEditText(image).isEmpty()
            && !getStringOfEditText(tel).isEmpty() && !getStringOfEditText(web).isEmpty()
            && !getStringOfEditText(mail).isEmpty() && !getStringOfEditText(me).isEmpty()){
            register.isEnabled
        }
        else{
            register.isEnabled = false
            Toast.makeText(context,"Required field", Toast.LENGTH_LONG).show()
        }*/
    }

    fun setTitle(){
        if(!modeEdit){
            listener.updateTitle(R.string.titleAdd)
        }
        else {
            listener.updateTitle(R.string.updateNeighbor)
        }
    }

    fun profileImage(){
        val context: Context? = context
        if (context != null) {
            Glide.with(context)
                .load(model.avatarUrl)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.ic_baseline_person_outline_24)
                .error(R.drawable.ic_baseline_person_outline_24)
                .skipMemoryCache(false)
                .into(icon)
        }
    }

    /*fun getStringOfEditText(edit: TextInputEditText): String{
        
        return edit.text.toString().trim()
        //.trim() enleve les espaces avant et apres la string

    }*/

    private class Validator(val errorMessage: String, val condition: Predicate<String>, val input: TextInputEditText){

        fun validate(): Boolean {
            val textOfInput = input.text.toString();
            val isValide = condition.test(textOfInput)
            if(isValide){
                input.error = null
            }
            else{
                input.error = errorMessage
            }
            return isValide
        }
    }
}