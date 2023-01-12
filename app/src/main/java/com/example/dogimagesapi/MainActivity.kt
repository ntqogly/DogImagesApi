package com.example.dogimagesapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.dogimagesapi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.loadDogImage()
        viewModel.dogImage.observe(this) {
            Glide.with(this)
                .load(it.message)
                .into(binding.imageView)
        }
//        viewModel.isLoading.observe(this) {
//            if (it) {
//                binding.progressBar.visibility = VISIBLE
//            } else {
//                binding.progressBar.visibility = INVISIBLE
//            }
//        }
        viewModel.isLoadingRuslan.observe(this) {
            if (it) {
                binding.imageViewRuslan.visibility = VISIBLE
            } else {
                binding.imageViewRuslan.visibility = INVISIBLE
            }
        }
        viewModel.isError.observe(this) {
            if (it) {
                Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_SHORT).show()
            }
        }
        binding.getImageButton.setOnClickListener {
            viewModel.loadDogImage()
        }


    }
}