package com.example.quanlynhanvienactivity.recycler_view

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupMenu
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quanlynhanvienactivity.R
import com.example.quanlynhanvienactivity.databinding.ActivityRecyclerViewBinding
import com.example.quanlynhanvienactivity.databinding.AddItemBinding
import com.example.quanlynhanvienactivity.list_view.adapter.data.StaffData
import com.example.quanlynhanvienactivity.list_view.fragment.AddFragment
import com.example.quanlynhanvienactivity.recycler_view.adapter.AdapterRecyclerView
import java.lang.Exception

class RecyclerViewActivity : AppCompatActivity() {
    lateinit var binding: ActivityRecyclerViewBinding
    var staffList = mutableListOf<StaffData>()
    lateinit var listAdapter: AdapterRecyclerView

    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
        if (it.resultCode == Activity.RESULT_OK){
            val e: String? = it.data?.getStringExtra("result")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        staffList()
        addEvent()
    }
    fun addEvent() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d(com.example.quanlynhanvienactivity.list_view.adapter.TAG, "beforeEditChange")
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d(com.example.quanlynhanvienactivity.list_view.adapter.TAG, "onTextChanged: ${charSequence.toString()}")
            }

            override fun afterTextChanged(editable: Editable?) {
                val text = editable.toString()
                listAdapter.filter(text)
            }
        })

        binding.ivBtAdd.setOnClickListener(){
            addInfor()
        }

        binding.ivSelectTool.setOnClickListener {
            binding.lnNavbarTool.visibility = View.VISIBLE
            binding.lnTool.visibility = View.GONE
            listAdapter.isEnable = !listAdapter.isEnable
            listAdapter.notifyDataSetChanged()
        }

        binding.ivCancel.setOnClickListener {
            binding.lnNavbarTool.visibility = View.GONE
            binding.lnTool.visibility = View.VISIBLE
            listAdapter.isEnable = !listAdapter.isEnable
            listAdapter.notifyDataSetChanged()
        }

        binding.ivIcDelete.setOnClickListener {
//            deleteSelect ()
            listAdapter.deleteSelectedItems()
        }

        binding.ivIcSelect.setOnClickListener {
            listAdapter.setAllChecked(!listAdapter.isSelect)
        }
    }

    //Show list
    fun staffList() {
        staffList.add(StaffData(R.drawable.ic_avt1, "12", "Nguyễn Anh", 21, "Hà Nội", "Dev", "Chính thức", false))
        staffList.add(StaffData(R.drawable.ic_avt1, "13", "Nguyễn Hưng", 22, "Hà Nội", "Marketing", "Chính thức", false))
        staffList.add(StaffData(R.drawable.ic_avt1, "14", "Tô Ngọc", 24, "Hà Nội", "Tester", "Thực tập", false))
        staffList.add(StaffData(R.drawable.ic_avt1, "15", "Phùng Hưng", 35, "Hà Nội", "Design", "Thực thập", false))
        staffList.add(StaffData(R.drawable.ic_avt1, "16", "Nguyễn Hưng", 22, "Hà Nội", "Marketing", "Chính thức", false))
        staffList.add(StaffData(R.drawable.ic_avt1, "17", "Tô Ngọc", 24, "Hà Nội", "Tester", "Chính thức", false))
        staffList.add(StaffData(R.drawable.ic_avt1, "18", "Phùng Hưng", 35, "Hà Nội", "Design", "Thực tập", false))
        staffList.add(StaffData(R.drawable.ic_avt1, "19", "Nguyễn Hưng", 22, "Hà Nội", "Dev", "Chính thức", false))



        listAdapter = AdapterRecyclerView(this, staffList, object :StaffInterface{
            override fun onClick(position: Int) {
                super.onClick(position)
                detailStaff(position)
            }

            override fun onLongClick(position: Int) {
                Toast.makeText(this@RecyclerViewActivity,"onLongClick", Toast.LENGTH_SHORT).show()

            }

            override fun staffShowSelected(isSelected: Boolean) {

            }
        })


        binding.rvStaff.adapter = listAdapter
        binding.rvStaff.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
    }
    /**
     * Xem thông tin chi tiết
     */
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
        val binding1: AddItemBinding = AddItemBinding.inflate(layoutInflater)
        val viewEdit = binding1.root
        val useName = binding1.etName
        val useId = binding1.etId
        val useAge = binding1.etAge
        val useAddress = binding1.etAddress
        val useDepartment = binding1.etDepartment
        val useStatus = binding1.etStatus
        val ivAvat = binding1.ivAvt
        val addDialog = AlertDialog.Builder(this)
        addDialog.setView(viewEdit)
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
                staffList.add(StaffData(R.drawable.user, id, names, age.toInt(), address, department, status, false))
                val avt = ivAvat.setImageResource(R.drawable.ic_avt1)
            }
            else {
                staffList.add(StaffData(R.drawable.ic_avt1, id, names, age.toInt(), address, department, status, false))
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

    fun deleteSelectedItems() {
        val listRemovePositon = listAdapter.filteredData1
        listRemovePositon.removeIf { it.isChecked == true }
        listAdapter.notifyDataSetChanged()
    }

}


