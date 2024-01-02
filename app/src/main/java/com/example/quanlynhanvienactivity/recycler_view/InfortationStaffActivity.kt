package com.example.quanlynhanvienactivity.recycler_view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quanlynhanvienactivity.R
import com.example.quanlynhanvienactivity.databinding.ActivityInfortationStaffBinding

class InfortationStaffActivity : AppCompatActivity() {
    lateinit var binding: ActivityInfortationStaffBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfortationStaffBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val i = intent
        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")
        val age = intent.getIntExtra("age",0)
        val department = intent.getStringExtra("department")
        val status = intent.getStringExtra("status")
        val address = intent.getStringExtra("address")
        val image = i.getIntExtra("image", R.drawable.user)
        binding.tvStaffId.text = id
        binding.tvStaffName.text = name
        binding.tvStaffAge.text = age.toString()
        binding.tvStaffAddress.text = address
        binding.tvStaffDepartment.text = department
        binding.tvStaffStatus.text = status
        binding.ivAvatar.setImageResource(image)
    }
}