package com.example.quanlynhanvienactivity.recycler_view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.quanlynhanvienactivity.R
import com.example.quanlynhanvienactivity.databinding.LayoutListStaffBinding
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
       holder.itemView.setOnClickListener {
           popupMenus(it)
       }

    }
//    popupMenus
    fun popupMenus(view: View){
        val popupMenu = PopupMenu(c.applicationContext, view)
        popupMenu.menuInflater.inflate((R.menu.show_menu),popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.it_delete -> {
                    Toast.makeText(c.applicationContext, "Bạn đã lựa chọn xóa", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.it_edit -> {
                    Toast.makeText(c.applicationContext, "Bạn đã lựa chọn sửa", Toast.LENGTH_SHORT).show()
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
//getItemCount
    override fun getItemCount(): Int {
        return list.size
    }
}


