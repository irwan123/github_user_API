package com.example.githubuserapi

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapi.databinding.FollowBinding

class Followers: Fragment(R.layout.follow) {
    private var _binding : FollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: Adapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arguments = arguments
        username = arguments?.getString(DetailUser.EXTRA_USERNAME).toString()
        _binding = FollowBinding.bind(view)
        adapter = Adapter()
        adapter.notifyDataSetChanged()
        binding.apply {
            rvuser.setHasFixedSize(true)
            rvuser.layoutManager = LinearLayoutManager(activity)
            rvuser.adapter = adapter
        }
        Loading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)
        viewModel.setListUserFollowers(username)
        viewModel.getListUserFollowers().observe(viewLifecycleOwner, {
            if (it!=null){
                adapter.setList(it)
                Loading(false)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun Loading (state: Boolean){
        if (state){
            binding.progress.visibility = View.VISIBLE
        } else {
            binding.progress.visibility = View.GONE
        }
    }
}
