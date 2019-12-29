package com.example.android.nhatrosv.models

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.android.nhatrosv.R
import com.example.android.nhatrosv.views.fragment.HomeFragment
import com.example.android.nhatrosv.views.fragment.MapFragment
import com.example.android.nhatrosv.views.fragment.ProfileFragment

/**
 * Screens available for display in the main screen, with their respective titles,
 * icons, and menu item IDs and fragments.
 */
enum class MainScreen constructor (@IdRes val menuItemId: Int,
                      @DrawableRes val menuItemIconId: Int,
                      @StringRes val titleStringId: Int,
                      val fragment: Fragment
) {
    HOME(
        R.id.bottom_navigation_item_home,
        R.drawable.round_home_24,
        R.string.activity_main_bottom_screen_home,
        HomeFragment()
    ),
    MAP(
        R.id.bottom_navigation_item_map,
        R.drawable.round_map_24,
        R.string.activity_main_bottom_screen_map,
        MapFragment()
    ),
    PROFILE(
        R.id.bottom_navigation_item_profile,
        R.drawable.round_person_24,
        R.string.activity_main_bottom_screen_profile,
        ProfileFragment()
    )
}

fun getMainScreenForMenuItem(menuItemId: Int): MainScreen? {
    for (mainScreen in MainScreen.values()) {
        if (mainScreen.menuItemId == menuItemId) {
            return mainScreen
        }
    }
    return null
}