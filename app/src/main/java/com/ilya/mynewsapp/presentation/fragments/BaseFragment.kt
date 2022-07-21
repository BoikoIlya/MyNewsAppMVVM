package com.ilya.mynewsapp.presentation.fragments

import android.app.Dialog
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ilya.mynewsapp.R


open class BaseFragment: Fragment() {


    fun showProgressBar(progressBar: ProgressBar){
       progressBar.visibility = View.VISIBLE
    }

    fun hideProgressBar(progressBar: ProgressBar){
        progressBar.visibility = View.GONE
    }

}