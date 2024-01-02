package com.example.quanlynhanvienactivity.list_view.fragment

import android.content.Intent
import android.content.LocusId
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.example.quanlynhanvienactivity.R
import com.example.quanlynhanvienactivity.databinding.FragmentAddBinding
import com.example.quanlynhanvienactivity.list_view.adapter.MainActivity


class AddFragment : DialogFragment() {
    lateinit var mActivity: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAddBinding
        binding = FragmentAddBinding.inflate(layoutInflater, container, false)
        dialog?.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            dialog?.setCancelable(true)
        }
        binding.btSend.setOnClickListener(View.OnClickListener {
            val  id = binding.etId.text.toString()
            val  name = binding.etName.text.toString()
            val  age = binding.etAge.text.toString()
            val  address = binding.etAddress.text.toString()
            val  department = binding.etDepartment.text.toString()
            val  status = binding.etStatus.text.toString()
            val  image = binding.ivAvt.setImageResource(R.drawable.ic_avt1)
            val intent: Intent = Intent(requireContext(),MainActivity::class.java)
            intent.putExtra("id",id)
            intent.putExtra("name",name)
            intent.putExtra("age",age.toInt())
            intent.putExtra("address",address)
            intent.putExtra("department",department)
            intent.putExtra("status",status)
            startActivity(intent)
        })
        // Inflate the layout for this fragment
        return binding.root
    }

//    fun senDataToActivity(){
//        val intent: Intent = Intent(requireContext(),MainActivity::class.java)
//
//
//    }

}