package com.example.anotheruserapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anotheruserapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: Adapter
    private lateinit var viewModel: FavoriteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = Adapter()
        adapter.notifyDataSetChanged()
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.adapter = adapter
        }
        viewModel.setFavoriteUser(this)
        viewModel.getFavoriteUser()?.observe(this,{
            if (it!=null){
                adapter.setList(it)
            }
        })
    }

}
