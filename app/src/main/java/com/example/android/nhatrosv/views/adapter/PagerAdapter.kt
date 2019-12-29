package com.example.android.nhatrosv.views.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.android.nhatrosv.models.MainScreen
import com.example.android.nhatrosv.views.activity.OnDataReceivedListener
import com.example.android.nhatrosv.views.fragment.MapFragment


@Suppress("DEPRECATION")
class MainPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm),OnDataReceivedListener {

    private val screens = arrayListOf<MainScreen>()

    fun setItems(screens: List<MainScreen>) {
        this.screens.apply {
            clear()
            addAll(screens)
            notifyDataSetChanged()
        }
    }

    fun getItems(): List<MainScreen> {
        return screens
    }

    override fun getItem(position: Int): Fragment {
        return screens[position].fragment
    }

    override fun getItemPosition(`object`: Any): Int {
        //return super.getItemPosition(`object`)
        return PagerAdapter.POSITION_NONE
    }

    override fun getCount(): Int {
        return screens.size
    }

    override fun onDataReceived(apartmentId: Int) {
        (getItem(2) as MapFragment).onDataReceived(apartmentId)
    }
}

