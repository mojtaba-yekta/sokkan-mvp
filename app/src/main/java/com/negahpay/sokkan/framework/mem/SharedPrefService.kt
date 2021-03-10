package com.negahpay.sokkan.framework.mem

import com.orhanobut.hawk.Hawk

class SharedPrefService {
    fun token() = Hawk.get<String>("token","")
    fun token(token:String) = Hawk.put("token",token)
}