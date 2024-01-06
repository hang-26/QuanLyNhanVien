package com.example.quanlynhanvienactivity.list_view.adapter

import android.app.Activity
import android.app.Notification
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.ActionMode
import android.view.View
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.quanlynhanvienactivity.R
import com.example.quanlynhanvienactivity.list_view.adapter.data.StaffData
import com.example.quanlynhanvienactivity.databinding.ActivityMainBinding
import com.example.quanlynhanvienactivity.list_view.fragment.AddFragment

//const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var staffAdapter: StaffAdapter
    var list = mutableListOf<StaffData>()
    private lateinit var actionMode: ActionMode
    lateinit var adapterItem: ArrayAdapter<String>
    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
        if (it.resultCode == Activity.RESULT_OK){
            val e: String? = it.data?.getStringExtra("result")
//            binding.editTextBeforResult.setText(e)

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createStaffList()
        setEvent()
        receiveData()

    }

    //Staff List
    fun createStaffList(){
        list.add(StaffData(R.drawable.ic_avt1, "12", "Nguyễn Anh", 21, "Hà Nội", "Dev", "Chính thức", false))
        list.add(StaffData(R.drawable.ic_avt1, "13", "Nguyễn Hưng", 22, "Hà Nội", "Marketing", "Chính thức", false))
        list.add(StaffData(R.drawable.ic_avt1, "14", "Tô Ngọc", 24, "Hà Nội", "Tester", "Thực tập", false))
        list.add(StaffData(R.drawable.ic_avt1, "15", "Phùng Hưng", 35, "Hà Nội", "Design", "Thực thập", false))
        list.add(StaffData(R.drawable.ic_avt1, "16", "Nguyễn Hưng", 22, "Hà Nội", "Marketing", "Chính thức", false))
        list.add(StaffData(R.drawable.ic_avt1, "17", "Tô Ngọc", 24, "Hà Nội", "Tester", "Chính thức", false))
        list.add(StaffData(R.drawable.ic_avt1, "18", "Phùng Hưng", 35, "Hà Nội", "Design", "Thực tập", false))
        list.add(StaffData(R.drawable.ic_avt1, "19", "Nguyễn Hưng", 22, "Hà Nội", "Dev", "Chính thức", false))

        staffAdapter = StaffAdapter(this, list)
        val listStaff = binding.lvStaff
        listStaff.adapter = staffAdapter
        binding.lvStaff.isClickable = true
    }

    //set Event
    fun setEvent(){
        //setOnItem ListView
        binding.lvStaff.setOnItemClickListener { parent, view, position, id ->
            val  user_id = list[position].userId
            val  user_name = list[position].userName
            val  address = list[position].address
            val  age = list[position].age
            val  department = list[position].department
            val  status = list[position].status
            val image = list[position].imageAvatar
            val intent: Intent = Intent(this, DetailStaffActivity::class.java)
            intent.putExtra("id",  user_id)
            intent.putExtra("name",  user_name)
            intent.putExtra("address",  address)
            intent.putExtra("age",  age)
            intent.putExtra("department",  department)
            intent.putExtra("status",  status)
            intent.putExtra("image",  image)
            startForResult.launch(intent)

        }
        //setOnItemOnLong ListView
        binding.lvStaff.setOnItemLongClickListener { parent, view, position, id ->
            popupMenus(view, position)

            true
        }
        //Add listView
        binding.btAdd.setOnClickListener(View.OnClickListener {
            var dialog = AddFragment()
            dialog.show(supportFragmentManager,"customDialog")

        })

//        Search
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
//                Log.d(TAG, "beforeEditChange")
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
//                Log.d(TAG, "onTextChanged: ${charSequence.toString()}")
                staffAdapter.filter(charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable?) {
//                val d = Log.d(TAG, "afterTextChanged: ")
                val text = editable.toString()
                staffAdapter.filter(text)
            }
        })
        //End
        //Item  menu
        binding.ivSelect.setOnClickListener{
            showMenuSelection(it)
            true
        }

    }
//Show menu Item
    private fun popupMenus(view: View, position: Int) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate((R.menu.show_menu),popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.it_delete -> {
                    Toast.makeText(this, "Bạn đã lựa chọn xóa", Toast.LENGTH_SHORT).show()
                    list.removeAt(position)
                    staffAdapter.notifyDataSetChanged()
                    true
                }
                R.id.it_edit -> {
                    Toast.makeText(this, "Bạn đã lựa chọn sửa", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }
//Add List
    fun receiveData(){
        val intent = getIntent()
        val id_new = intent.getStringExtra("id")
        val name_new = intent.getStringExtra("name")
        val age_new = intent.getIntExtra("age",0)
        val address_new = intent.getStringExtra("address")
        val department_new = intent.getStringExtra("department")
        val status_new = intent.getStringExtra("status")
        val image_avt_new = intent.getIntExtra("image", R.drawable.user)
        if (id_new != null && name_new != null && address_new != null
            && department_new != null && status_new != null){
            list.add(
                StaffData(image_avt_new, id_new, name_new, age_new, address_new,
                    department_new, status_new, false)
            )
            Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show()
        }
    createStaffList()
    staffAdapter.notifyDataSetChanged()
    }

    fun showMenuSelection(view: View): Boolean {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate((R.menu.menu_select),popupMenu.menu)
//        popupMenu.setOnMenuItemClickListener {
//            when(it.itemId){
//                R.id.it_destroy -> {
//                    Toast.makeText(this, "Bạn đã lựa chọn tắt", Toast.LENGTH_SHORT).show()
//                    true
//                }
//                R.id.it_select_all -> {
//                    Toast.makeText(this, "Bạn đã lựa chọn tất cả", Toast.LENGTH_SHORT).show()
//                    true
//                }
//                else -> false
//            }
//        }
        return true
    }




}