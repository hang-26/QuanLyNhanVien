package com.example.quanlynhanvienactivity.list_view.adapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quanlynhanvienactivity.R
import com.example.quanlynhanvienactivity.databinding.ActivityDetailStaffBinding

class DetailStaffActivity : AppCompatActivity() {
    lateinit var binding:ActivityDetailStaffBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStaffBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val  i = intent
        val id = i.getStringExtra("id")
        val image = i.getIntExtra("image", R.drawable.user)
        val name = i.getStringExtra("name")
        val address = i.getStringExtra("address")
        val status = i.getStringExtra("status")
        val department = i.getStringExtra("department")
        val age = i.getIntExtra("age",1)
        binding.tvStaffId.setText(id)
        binding.ivAvatar.setImageResource(image)
    }
}