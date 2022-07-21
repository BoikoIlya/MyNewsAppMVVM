package com.ilya.mynewsapp.presentation.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Message
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ilya.mynewsapp.R
import com.ilya.mynewsapp.di.App


open class BaseFragment: Fragment() {


    fun showProgressBar(progressBar: ProgressBar){
       progressBar.visibility = View.VISIBLE
    }

    fun hideProgressBar(progressBar: ProgressBar){
        progressBar.visibility = View.GONE
    }

    fun showAlert(message: String){
        val dialog = AlertDialog.Builder(activity as AppCompatActivity)
        dialog.setNegativeButton("Ok"){ _, id->
        }
            .setMessage(message)
        dialog.create()
    }

}