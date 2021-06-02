package com.example.githubuserapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapi.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel : ViewModel
    private lateinit var adapter : Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener{
            Toast.makeText(this, "Navigation Menu Clicked", Toast.LENGTH_SHORT)
        }
        adapter = Adapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCalback(object: Adapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, DetailUser::class.java).also {
                    it.putExtra(DetailUser.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUser.EXTRA_ID, data.id)
                    it.putExtra(DetailUser.EXTRA_URL, data.avatar_url)
                    startActivity(it)
                }
            }

        })
        viewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(ViewModel::class.java)
        binding.searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Loading(true)
                viewModel.setSearchUser(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        binding.apply {
            rvlistuser.layoutManager= LinearLayoutManager(this@MainActivity)
            rvlistuser.setHasFixedSize(true)
            rvlistuser.adapter = adapter
            searchview.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }
        viewModel.getSearchUser().observe(this, {
            if (it!=null){
                adapter.setList(it)
                Loading(false)
            }
        })
    }
    private fun Loading (state: Boolean){
        if (state){
            binding.progress.visibility = View.VISIBLE
        } else {
            binding.progress.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.favoriteUserMenu -> {
                Intent(this, Favorite::class.java).also {
                    startActivity(it)
                }
            }
            R.id.settingMenu -> {
                Intent(this, Settings::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
