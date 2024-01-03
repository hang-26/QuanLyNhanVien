package com.example.quanlynhanvienactivity.recycler_view.adapter

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.quanlynhanvienactivity.R
import com.example.quanlynhanvienactivity.databinding.AddItemBinding
import com.example.quanlynhanvienactivity.databinding.LayoutListStaffBinding
import com.example.quanlynhanvienactivity.databinding.UpdateItemBinding
import com.example.quanlynhanvienactivity.list_view.adapter.data.StaffData
import com.example.quanlynhanvienactivity.recycler_view.StaffInterface

class AdapterRecyclerView (val c: Context, var list : List<StaffData>, val onItemClick: StaffInterface)
    :RecyclerView.Adapter<AdapterRecyclerView.StaffViewHolder>() {
    lateinit var binding: LayoutListStaffBinding
//class View Holder
    class StaffViewHolder (binding: LayoutListStaffBinding)
        :RecyclerView.ViewHolder(binding.root){

        }

//conCreatViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffViewHolder {
        val view = LayoutInflater.from(parent.context)
        binding = LayoutListStaffBinding
            .inflate(view, parent, false)
        return StaffViewHolder( binding )

    }
//onBindViewHolder
    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {
        val listStaff = list[position]
        holder.itemView.apply {
            binding.rTvUserId.text = listStaff.userId
            binding.rTvUserName.text = listStaff.userName
            binding.tvUserDepartment.text = listStaff.department
            binding.rTvUserStatus.text = listStaff.status
            binding.rIvAvatar.setImageResource(listStaff.imageAvatar)
            //setItem Click

        }
//  Set item click
        holder.itemView.setOnClickListener {
            onItemClick.onClick(position)
        }
//      SetOnLongClick
       holder.itemView.setOnLongClickListener() {
           popupMenus(it, position)
           true
       }

    }
//    popupMenus
    fun popupMenus(view: View, position: Int){
        val popupMenu = PopupMenu(c.applicationContext, view)
        popupMenu.menuInflater.inflate((R.menu.show_menu),popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
//                Xóa dữ liệu
                R.id.it_delete -> {
                    Toast.makeText(c.applicationContext, "Bạn đã lựa chọn xóa", Toast.LENGTH_SHORT).show()
                    notifyItemRemoved(position)
                    true
                }
//                Chỉnh sủa dữ liệu
                R.id.it_edit -> {
                    updateData(position)
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
        val popup = PopupMenu::class.java.getDeclaredField("mPopup")
        popup.isAccessible = true
        val menu = popup.get(popupMenu)
        menu.javaClass.getDeclaredMethod("setForceShowIcon" ,Boolean::class.java)
            .invoke(menu, true)
    }
//    update data
    fun updateData(position: Int){
    Toast.makeText(c.applicationContext, "Bạn đã lựa chọn sửa", Toast.LENGTH_SHORT).show()
    val bindingUpdate: UpdateItemBinding
    bindingUpdate = UpdateItemBinding.inflate(LayoutInflater.from(c),null,false)
    val viewItem = bindingUpdate.root
    val age = bindingUpdate.etAge
    val status = bindingUpdate.etStatus
    val updateDialog = AlertDialog.Builder(c)
        updateDialog.setView(viewItem)
        updateDialog.setPositiveButton("Ok"){
            dialog,_->
            val update = list[position]
            update.age = age.text.toString().toInt()
            update.status = status.text.toString()
            update.imageAvatar = R.drawable.user
            notifyItemChanged(position)
            dialog.dismiss()
        }
    updateDialog.setNegativeButton("Cancel"){
            dialog,_->
            dialog.dismiss()
            Toast.makeText(c, "Cancel", Toast.LENGTH_SHORT).show()
        }
    updateDialog.create()
    updateDialog.show()
    }




    //getItemCount
    override fun getItemCount(): Int {
        return list.size
    }
}


