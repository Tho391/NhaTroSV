package com.example.android.nhatrosv.views.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.example.android.nhatrosv.R
import com.example.android.nhatrosv.models.MainScreen
import com.example.android.nhatrosv.models.getMainScreenForMenuItem
import com.example.android.nhatrosv.viewModels.MainActivityViewModel
import com.example.android.nhatrosv.views.adapter.MainPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var viewPager: ViewPager
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var mainPagerAdapter: MainPagerAdapter

    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize components/views.
        viewPager = findViewById(R.id.view_pager)
        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        mainPagerAdapter =
            MainPagerAdapter(supportFragmentManager)

        // Set items to be displayed.
        mainPagerAdapter.setItems(
            arrayListOf(
                MainScreen.HOME,
                MainScreen.MAP,
                MainScreen.PROFILE
            )
        )

        // Show the default screen.
        val defaultScreen = MainScreen.HOME
        scrollToScreen(defaultScreen)
        selectBottomNavigationViewMenuItem(defaultScreen.menuItemId)
        supportActionBar?.setTitle(defaultScreen.titleStringId)

        // Set the listener for item selection in the bottom navigation view.
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        // Attach an adapter to the view pager and make it select the bottom navigation
        // menu item and change the title to proper values when selected.
        viewPager.adapter = mainPagerAdapter
        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                val selectedScreen = mainPagerAdapter.getItems()[position]
                selectBottomNavigationViewMenuItem(selectedScreen.menuItemId)
                supportActionBar?.setTitle(selectedScreen.titleStringId)
            }
        })

        //init view model
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
//        mainActivityViewModel.getApartments().observe(this,
//            Observer<ApartmentResponse> { response: ApartmentResponse ->
//                //val apartments: List<Apartment> = response
//                //apartmentList.addAll(apartments)
//            }
//        )
    }

    /**
     * Scrolls ViewPager to show the provided screen.
     */
    private fun scrollToScreen(mainScreen: MainScreen) {
        val screenPosition = mainPagerAdapter.getItems().indexOf(mainScreen)
        if (screenPosition != viewPager.currentItem) {
            viewPager.currentItem = screenPosition
        }
    }

    /**
     * Selects the specified item in the bottom navigation view.
     */
    private fun selectBottomNavigationViewMenuItem(@IdRes menuItemId: Int) {
        bottomNavigationView.setOnNavigationItemSelectedListener(null)
        bottomNavigationView.selectedItemId = menuItemId
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }

    /**
     * Listener implementation for registering bottom navigation clicks.
     */
    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        getMainScreenForMenuItem(menuItem.itemId)?.let {
            scrollToScreen(it)
            supportActionBar?.setTitle(it.titleStringId)
            return true
        }
        return false
    }

}

