package com.example.neighborss.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.neighborss.NavigationListener
import com.example.neighborss.R
import com.example.neighborss.dal.utils.toNeighborDTO
import com.example.neighborss.models.Neighbor
import com.example.neighborss.ui.fragment.AddNeighbourFragment
import kotlinx.coroutines.awaitAll

class ListNeighborsAdapter( items: List<Neighbor>, private val handler: ListNeighborHandler, private val listener: NavigationListener) : RecyclerView.Adapter<ListNeighborsAdapter.ViewHolder>() {

    private val mNeighbours: List<Neighbor> = items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.neighbor_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val neighbour: Neighbor = mNeighbours[position]
        // Display Neighbour Name
        holder.mNeighbourName.text = neighbour.name

        val context = holder.itemView.context
        Glide.with(context)
            .load(neighbour.avatarUrl)
            .apply(RequestOptions.circleCropTransform())
            .placeholder(R.drawable.ic_baseline_person_outline_24)
            .error(R.drawable.ic_baseline_person_outline_24)
            .skipMemoryCache(false)
            .into(holder.mNeighbourAvatar)

        holder.mDeleteButton.setOnClickListener {
            handler.onDeleteNeibor(neighbour)
        }

        holder.mFavoriteButton.setOnClickListener {
            // buttonPressed()
            handler.favoriteNeibor(neighbour)
        }

        holder.mEditButton.setOnClickListener{
            listener.showFragment(AddNeighbourFragment(handler, listener, neighbour))
        }

        if(!neighbour.favorite){
            holder.mFavoriteButton.setImageResource(R.drawable.ic_baseline_star_border_24)
        }
        else{
            holder.mFavoriteButton.setImageResource(R.drawable.ic_baseline_star_24)
        }
    }

    override fun getItemCount(): Int {
        return mNeighbours.size
    }

    inner class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view),
        View.OnClickListener {
        val mNeighbourAvatar: ImageView
        val mNeighbourName: TextView
        val mDeleteButton: ImageButton
        val mFavoriteButton : ImageButton
        val mEditButton : ImageButton

        init {
            // Enable click on item
            mNeighbourAvatar = view.findViewById(R.id.item_list_avatar)
            mNeighbourName = view.findViewById(R.id.item_list_name)
            mDeleteButton = view.findViewById(R.id.item_list_delete_button)
            mFavoriteButton = view.findViewById(R.id.item_list_favorite_button)
            mEditButton = view.findViewById(R.id.item_list_edit_button)

            view.setOnClickListener(this)
        }

        override fun onClick(v: View?){
            val position = absoluteAdapterPosition
            if(position != RecyclerView.NO_POSITION){
                handler.onItemClick(mNeighbours[position])
            }
        }
    }



}