package com.example.neighborss.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
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
import java.util.function.Predicate

class DetailsNeighborFragment(private val listener: NavigationListener, private val neighbor:Neighbor) : Fragment() {

    private lateinit var name : TextView
    private lateinit var tel : TextView
    private lateinit var web : TextView
    private lateinit var mail : TextView
    private lateinit var me : TextView
    private lateinit var icon : ImageView
    private lateinit var favorite : ImageView

    private val neighbour = Neighbor (neighbor.id, neighbor.name, neighbor.avatarUrl, neighbor.address, neighbor.phoneNumber, neighbor.aboutMe, neighbor.favorite, neighbor.webSite)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.neighbor_details, container, false)

        name = view.findViewById(R.id.name)
        tel = view.findViewById(R.id.tel)
        web = view.findViewById(R.id.web)
        mail = view.findViewById(R.id.mail)
        me = view.findViewById(R.id.me)
        icon = view.findViewById(R.id.person)
        favorite = view.findViewById(R.id.item_list_favorite)


        if (neighbour != null){
            name.setText(neighbour.name)
            tel.setText(neighbour.phoneNumber)
            web.setText(neighbour.webSite)
            mail.setText(neighbour.address)
            me.setText(neighbour.aboutMe)

            if(!neighbour.favorite){
                favorite.setImageResource(R.drawable.ic_baseline_star_border_24)
            }
            else{
                favorite.setImageResource(R.drawable.ic_baseline_star_24)
            }

            profileImage()
        }

        setTitle()

        return view;
    }

    fun setTitle(){
        listener.updateTitle(R.string.details)
    }

    fun profileImage(){
        val context: Context? = context
        if (context != null) {
            Glide.with(context)
                .load(neighbour.avatarUrl)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.ic_baseline_person_outline_24)
                .error(R.drawable.ic_baseline_person_outline_24)
                .skipMemoryCache(false)
                .into(icon)
        }
    }
}