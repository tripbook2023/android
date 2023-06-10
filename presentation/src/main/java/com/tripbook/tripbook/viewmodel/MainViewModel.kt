package com.tripbook.tripbook.viewmodel

import com.tripbook.libs.network.NetworkManager


class MainViewModel (private val netWorkManger: NetworkManager) {
    private fun chkNetWork() : Boolean {
        return netWorkManger.chkNetWork()
    }
}