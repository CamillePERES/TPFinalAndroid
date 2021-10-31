package com.example.neighborss.ui.fragment

import android.app.AlertDialog
import android.app.Application
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neighborss.NavigationListener
import com.example.neighborss.R
import com.example.neighborss.adapters.ListNeighborHandler
import com.example.neighborss.adapters.ListNeighborsAdapter
import com.example.neighborss.repositories.NeighborRepository
import com.example.neighborss.dto.NeighborDTO
import com.example.neighborss.models.Neighbor
import com.example.neighborss.viewmodels.RepositoryViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListNeighborsFragment(private val listener: NavigationListener) : Fragment(), ListNeighborHandler{


    private lateinit var recyclerView: RecyclerView
    private lateinit var addNeighbor: FloatingActionButton
    private lateinit var viewModel: RepositoryViewModel

    /**
     * Fonction permettant de définir une vue à attacher à un fragment
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.list_neighbors_fragment, container, false)
        recyclerView = view.findViewById(R.id.neighbors_list)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        addNeighbor = view.findViewById(R.id.add)
        addNeighbor.setOnClickListener {
            listener.showFragment(AddNeighbourFragment(this, listener, null))
        }

        setTitle()

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(RepositoryViewModel::class.java)
        setData()
        //refreshList()
    }

    /*fun refreshList(){
        val neighbors = NeighborRepository.getInstance().getNeighbours()
        val adapter = ListNeighborsAdapter(neighbors, this)
        recyclerView.adapter = adapter
    }*/

    override fun onDeleteNeibor(neighbor: Neighbor) {
        // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }

        builder?.setMessage("Voulez-vous supprimer ce voisin ?")
               ?.setTitle("Suppression")

        builder?.apply {
            setPositiveButton("Oui",
                DialogInterface.OnClickListener { dialog, id ->
                    viewModel.delete(neighbor)
                    //refreshList()
                })
            setNegativeButton("Non",
                DialogInterface.OnClickListener { dialog, id ->
                    // User cancelled the dialog
                })
        }

// 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
        val dialog: AlertDialog? = builder?.create()

        dialog?.show()
    }

    override fun onCreateNeibor(neighbor: NeighborDTO) {
        viewModel.create(neighbor)
        //refreshList();
    }

    override fun onItemClick(neighbor: Neighbor) {
        println(neighbor.id)
        listener.showFragment(DetailsNeighborFragment(listener, neighbor))
    }

    override fun onUpdateNeibor(model: NeighborDTO) {
        viewModel.update(model)
        //refreshList();
    }

    override fun favoriteNeibor(neighbor: Neighbor) {
        viewModel.favorite(neighbor)
       /*var favoris = neighbor.favorite
       if (!favoris){

       }
       else{
           print("rien")
       }*/
    }

    fun setTitle(){
        listener.updateTitle(R.string.titleList)
    }

    private fun setData() {
        viewModel.neighbors.observe(viewLifecycleOwner) {
            it.observe(viewLifecycleOwner){ list ->
                val adapter = ListNeighborsAdapter(list, this, listener)
                recyclerView.adapter = adapter
            }
        }
    }


}