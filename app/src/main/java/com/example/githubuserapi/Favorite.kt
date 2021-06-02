package com.example.githubuserapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isGone
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapi.databinding.ActivityFavoriteBinding

class Favorite : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: Adapter
    private lateinit var viewModel: FavoriteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = Adapter()
        adapter.notifyDataSetChanged()
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        adapter.setOnItemClickCalback(object: Adapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                Intent(this@Favorite, DetailUser::class.java).also {
                    it.putExtra(DetailUser.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUser.EXTRA_ID, data.id)
                    it.putExtra(DetailUser.EXTRA_URL, data.avatar_url)
                    startActivity(it)
                }
            }
        })
        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(this@Favorite)
            rvUser.adapter = adapter
        }
        viewModel.getFavoriteUser()?.observe(this,{
            if (it!=null){
                val list = mapList(it)
                adapter.setList(list)
            }
        })
    }
    private fun mapList(users: List<FavoriteUser>): ArrayList<User>{
        val list = ArrayList<User>()
        for (user in users){
            val userMapped = User(
                user.login,
                user.id,
                user.avatar_url
            )
            list.add(userMapped)
        }
        return list
    }
}