package com.example.quanlynhanvienactivity.list_view.adapter

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import com.example.quanlynhanvienactivity.R
import com.example.quanlynhanvienactivity.databinding.ListViewStaffBinding
import com.example.quanlynhanvienactivity.list_view.adapter.data.StaffData

private const val TAG = "StaffAdapter"

class StaffAdapter(
    val activity: Activity,
    val listStaff: List<StaffData>
) : ArrayAdapter<StaffData>(activity, R.layout.list_view_staff) {

    var filteredData: List<StaffData> = ArrayList(listStaff)
    val originalData: List<StaffData> = listStaff
    override fun getCount(): Int {
        return filteredData.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var binding: ListViewStaffBinding
        binding = ListViewStaffBinding.inflate(activity.layoutInflater, parent, false)
        val imageAvatar = binding.ivAvatar
        val userId = binding.tvUserId
        val userName = binding.tvUserName
        val userDepartment = binding.tvUserDepartment
        val status = binding.tvUserStatus
        val staff = filteredData[position]
        val address: String =staff.address
        val age: Int = staff.age
        userId.text = staff.userId
        userName.text = staff.userName
        userDepartment.text = staff.department
        status.text = staff.status
        imageAvatar.setImageResource(staff.imageAvatar)
        return binding.root
    }
//    Handle find by name

    fun filter(text: String) {
        Log.d(TAG, "filter: ${originalData.size}")
        if (text.isEmpty()) {
            filteredData = originalData
        } else {
            filteredData = originalData.filter {
                it.userName.toLowerCase().contains(text.toLowerCase())
            }
            Log.d(TAG, "filter: ${filteredData.size}")

        }
        notifyDataSetChanged()

    }
//    Handle checked

}