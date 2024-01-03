package com.example.quanlynhanvienactivity.recycler_view

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quanlynhanvienactivity.R
import com.example.quanlynhanvienactivity.databinding.ActivityRecyclerViewBinding
import com.example.quanlynhanvienactivity.databinding.AddItemBinding
import com.example.quanlynhanvienactivity.list_view.adapter.data.StaffData
import com.example.quanlynhanvienactivity.list_view.fragment.AddFragment
import com.example.quanlynhanvienactivity.recycler_view.adapter.AdapterRecyclerView

class RecyclerViewActivity : AppCompatActivity() {
    lateinit var binding: ActivityRecyclerViewBinding
    val staffList = mutableListOf<StaffData>()
    lateinit var listAdapter: AdapterRecyclerView
//Result API
    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
        if (it.resultCode == Activity.RESULT_OK){
            val e: String? = it.data?.getStringExtra("result")
    //            binding.editTextBeforResult.setText(e)

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        staffList()

    }
    //Show list
    fun staffList(){
        staffList.add(StaffData(R.drawable.ic_avt1,"12", "Nguyễn Anh", 21, "Hà Nội", "Kế Toán", "Chính thức"))
        staffList.add(StaffData(R.drawable.ic_avt1,"13", "Nguyễn Hưng", 22, "Hà Nội", "Marketing", "Chính thức"))
        staffList.add(StaffData(R.drawable.ic_avt1,"14", "Tô Ngọc", 24, "Hà Nội", "Tester", "Chính thức"))
        staffList.add(StaffData(R.drawable.ic_avt1,"15", "Phùng Hưng", 35, "Hà Nội", "Kế Toán", "Trưởng phòng"))
        listAdapter = AdapterRecyclerView(this, staffList, object :StaffInterface{
//            setOnClick
            override fun onClick(position: Int) {
                super.onClick(position)
                Toast.makeText(this@RecyclerViewActivity, "Thông tin chi tiết" , Toast.LENGTH_SHORT).show()
                detailStaff(position)
            }
//            SetOnLongClick
            override fun onLongClick(position: Int) {
                Toast.makeText(this@RecyclerViewActivity,"onLongClick", Toast.LENGTH_SHORT).show()
//                var dialog = AddFragment()
//                dialog.show(supportFragmentManager,"customDialog")
            }

        })

        binding.ivBtAdd.setOnClickListener(){
            addInfor()
        }
        binding.rvStaff.adapter = listAdapter
        binding.rvStaff.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
    }
    //Add Event
    fun detailStaff(i: Int){
        val intent = Intent(this, InfortationStaffActivity::class.java)
        val staffDetail = staffList[i]
        intent.putExtra("id", staffDetail.userId)
        intent.putExtra("name", staffDetail.userName)
        intent.putExtra("age", staffDetail.age)
        intent.putExtra("address", staffDetail.address)
        intent.putExtra("department", staffDetail.department)
        intent.putExtra("status", staffDetail.status)
        intent.putExtra("image", staffDetail.imageAvatar)
        startForResult.launch(intent)
    }

    fun addInfor(){
//        val inflter = LayoutInflater.from(this)
        val binding1: AddItemBinding = AddItemBinding.inflate(layoutInflater)
        val v = binding1.root
/*set view*/
        val useName = binding1.etName
        val useId = binding1.etId
        val useAge = binding1.etAge
        val useAddress = binding1.etAddress
        val useDepartment = binding1.etDepartment
        val useStatus = binding1.etStatus
        val ivAvat = binding1.ivAvt
        val  addDialog = AlertDialog.Builder(this)
        addDialog.setView(v)
        addDialog.setPositiveButton("Ok"){
            dialog,_ ->
            val names = useName.text.toString()
            val id = useId.text.toString()
            val age = useAge.text.toString()
            val address = useAddress.text.toString()
            val department = useDepartment.text.toString()
            var status = useStatus.text.toString()
            val avt = ivAvat.setImageResource(R.drawable.ic_avt1)
            Log.d(TAG, "addInfor: $status")
            if (status.toString().toLowerCase() == "nhân viên "){
                staffList.add(StaffData(R.drawable.user, id, names, age.toInt(), address, department, status))
                val avt = ivAvat.setImageResource(R.drawable.ic_avt1)
            }
            else {
                staffList.add(StaffData(R.drawable.ic_avt1, id, names, age.toInt(), address, department, status))
                Log.d(TAG, "addInfor: $status")
            }
            listAdapter.notifyDataSetChanged()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel"){
            dialog,_->
            dialog.dismiss()
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
        }
        addDialog.create()
        addDialog.show()
    }

}