package com.example.quanlynhanvienactivity.list_view.adapter.data

class StaffData {

    var imageAvatar: Int = 0
    var userId: String = ""
    var userName: String = ""
    var age:Int = 0
    var address: String = ""
    var department: String = ""
    var status: String = ""
    var isChecked: Boolean = false
//    constructor(image_avatar: Int, user_name : String, department:String, status: String)
    constructor(imageAvatar1: Int, useId: String, userName1 : String, age:Int, address: String, department: String, status: String, isChecked: Boolean){
        this.imageAvatar = imageAvatar1
        this.userId = useId
        this.userName = userName1
        this.age = age
        this.address = address
        this.department = department
        this.status = status
        this.isChecked = isChecked
    }

    override fun toString(): String {
        return "StaffData(userName='$userName')"
    }


}