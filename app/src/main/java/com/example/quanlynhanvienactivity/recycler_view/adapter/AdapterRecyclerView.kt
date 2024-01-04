package com.example.quanlynhanvienactivity.recycler_view.adapter

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.quanlynhanvienactivity.R
import com.example.quanlynhanvienactivity.databinding.LayoutListStaffBinding
import com.example.quanlynhanvienactivity.databinding.UpdateItemBinding
import com.example.quanlynhanvienactivity.list_view.adapter.data.StaffData
import com.example.quanlynhanvienactivity.recycler_view.StaffInterface

private const val TAG = "AdapterRecyclerView"
class AdapterRecyclerView (
    val context: Context,
    var list : List<StaffData>,
    val onItemClick: StaffInterface): RecyclerView.Adapter<AdapterRecyclerView.StaffViewHolder>() {

    var filteredData1 = list.toMutableList()
    var isEnable = false
    lateinit var binding: LayoutListStaffBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffViewHolder {
    Log.d(TAG, "onCreateViewHolder: ${filteredData1.size}")
        val view = LayoutInflater.from(parent.context)
        binding = LayoutListStaffBinding.inflate(view, parent, false)
        return StaffViewHolder( binding )

    }

    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {

        val listStaff = filteredData1[holder.bindingAdapterPosition]

        val binding = holder.binding

            binding.rTvUserId.text = listStaff.userId
            binding.rTvUserName.text = listStaff.userName
            binding.tvUserDepartment.text = listStaff.department
            binding.rTvUserStatus.text = listStaff.status
            binding.rIvAvatar.setImageResource(listStaff.imageAvatar)
            binding.cbCheckBox.visibility = View.GONE

        //setOnClick
        holder.itemView.setOnClickListener {
            onItemClick.onClick(holder.bindingAdapterPosition)
        }

        //setOnLongClick
       holder.itemView.setOnLongClickListener() {
           popupMenus(it, holder.bindingAdapterPosition)
           true
       }
//        // selected
//        holder.itemView.setOnClickListener {
//            selectItem(holder, listStaff, holder.bindingAdapterPosition)
//        }
    }


    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: ")
        return filteredData1.size
    }

    fun popupMenus(view: View, position: Int) {
        val popupMenu = PopupMenu(context.applicationContext, view)
        popupMenu.menuInflater.inflate((R.menu.show_menu),popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                //Xóa dữ liệu
                R.id.it_delete -> {
                    Toast.makeText(context.applicationContext, "Bạn đã lựa chọn xóa", Toast.LENGTH_SHORT).show()
                    filteredData1.removeAt(position)
                    notifyItemRemoved(position)
                    true
                }

                // Cập nhật dữ liệu
                R.id.it_edit -> {
                    updateData(position)
                    true
                }
                else -> false
            }
        }

        popupMenu.show()

    }

    /**
     * Hàm cập nhật
     */
    fun updateData(position: Int) {
    Toast.makeText(context.applicationContext, "Bạn đã lựa chọn sửa", Toast.LENGTH_SHORT).show()
    val bindingUpdate: UpdateItemBinding
    bindingUpdate = UpdateItemBinding.inflate(LayoutInflater.from(context),null,false)
    val viewItem = bindingUpdate.root
    val age = bindingUpdate.etAge
    val status = bindingUpdate.etStatus
    val updateDialog = AlertDialog.Builder(context)
        .setView(viewItem)
    updateDialog.setPositiveButton("Ok") {
            dialog,_->
                val update = list[position]
                update.age = age.text.toString().toInt()
                update.status = status.text.toString()
                update.imageAvatar = R.drawable.user
                notifyItemChanged(position)
                dialog.dismiss()
        }
    updateDialog.setNegativeButton("Cancel") {
            dialog,_->
                dialog.dismiss()
                Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show()
        }
    updateDialog.create()
    updateDialog.show()
    }

    /**
     * Hàm xử lý tìm kiếm
     */
    fun filter(text: String) {
        val textSearch = text.toLowerCase()
        Log.d("filter", "filter1: $textSearch")
        if (textSearch.isEmpty()) {
            filteredData1 = list.toMutableList()
        } else {
            filteredData1 = list.filter {
                it.userName.toLowerCase().contains(textSearch.toLowerCase())
            }.toMutableList()
        }
        notifyDataSetChanged()
    }

    private fun selectItem(holder: AdapterRecyclerView.StaffViewHolder, listStaff: StaffData, bindingAdapterPosition: Int) {
        isEnable = true
//        filteredData1.toMutableList().add(StaffData(bindingAdapterPosition))
    }

    class StaffViewHolder (val binding: LayoutListStaffBinding)
        :RecyclerView.ViewHolder(binding.root){
    }
}




