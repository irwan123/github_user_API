package com.example.githubuserapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuserapi.databinding.ActivityDetailUserBinding

import kotlinx.android.synthetic.main.activity_detail_user.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUser : AppCompatActivity() {
    companion object{
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
    }
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        username?.let { viewModel.setDetailUser(it) }
        viewModel.getDetailUser().observe(this, {
            if (it != null) {
                binding.apply {
                    tvname.text = it.name
                    tv_username.text = it.login
                    tvfollowers.text = "Followers ${it.followers}"
                    tvfollowing.text = "Following ${it.following}"
                    Glide.with(this@DetailUser)
                        .load(it.avatar_url)
                        .centerCrop()
                        .into(imgprofile)
                }
            }
        })
        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count!=null){
                    if (count>0){
                        binding.toggleFavorite.isChecked = true
                        _isChecked = true
                    } else {
                        binding.toggleFavorite.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }
        binding.toggleFavorite.setOnClickListener{
            _isChecked = !_isChecked
            if (_isChecked){
                username?.let { it1 -> avatarUrl?.let { it2 ->
                    viewModel.addToFavorite(it1, id,
                        it2
                    )
                } }
            } else {
                viewModel.removeFromFavorite(id)
            }
            binding.toggleFavorite.isChecked = _isChecked
        }
        val sectionAdapter = SectionAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewpager.adapter = sectionAdapter
            tablayout.setupWithViewPager(viewpager)
        }
    }
}