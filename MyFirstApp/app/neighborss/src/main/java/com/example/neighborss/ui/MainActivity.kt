package com.example.neighborss.ui

import android.content.ClipData
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.neighborss.NavigationListener
import com.example.neighborss.R
import com.example.neighborss.dal.room.RoomNeighborDataSource
import com.example.neighborss.di.DI
import com.example.neighborss.repositories.NeighborRepository
import com.example.neighborss.ui.fragment.ListNeighborsFragment
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import android.text.style.ForegroundColorSpan

import android.text.SpannableString




class MainActivity : AppCompatActivity(), NavigationListener {

    private lateinit var toolbar: Toolbar
    private lateinit var switchMemory : MenuItem
    private lateinit var switchDBA : MenuItem
    private lateinit var repository: NeighborRepository
    private var boolDataSource : Boolean = false
    private lateinit var menuToolbar: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        DI.inject(application)
        repository = DI.repository

        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar)

        showFragment(ListNeighborsFragment(this))

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)

        switchMemory = menu.findItem(R.id.memory)
        switchDBA = menu.findItem(R.id.database)
        menuToolbar = menu.findItem(R.id.app_bar_switch)

        updateMenuTitle(R.string.memoryMenu)
        refreshStateMenu()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.getItemId()
        if (id == R.id.app_bar_switch) {

            return true
        }
        if (id == R.id.database) {

            boolDataSource = true
            updateMenuTitle(R.string.dbMenu)
            refreshStateMenu()

            Toast.makeText(this, "DBS source chosen", Toast.LENGTH_LONG).show()
            return true

        }
        if (id == R.id.memory) {

            boolDataSource = false
            updateMenuTitle(R.string.memoryMenu)
            refreshStateMenu()

            /*switchMemory.setOnMenuItemClickListener {
                dataSource = InMemoryNeighborDataSource()
            }*/

            Toast.makeText(this, "Memory source chosen", Toast.LENGTH_LONG).show()
            return true
        }

        return super.onOptionsItemSelected(item)

    }

    fun refreshStateMenu(){

        repository.setSource(boolDataSource)
        switchMemory.setVisible(boolDataSource)
        switchDBA.setVisible(!boolDataSource)

        /*if(boolDataSource){
            switchMemory.setVisible(true)
            switchDBA.setVisible(false)
        }
        else{
            switchMemory.setVisible(false)
            switchDBA.setVisible(true)
        }*/

    }

    override fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
        }.commit()
    }

    override fun updateTitle(title: Int) {
        toolbar.setTitle(title)
    }

    fun updateMenuTitle(menuTitle: Int){
        menuToolbar.setTitle(menuTitle)
    }

}