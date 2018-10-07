package com.dev.thongpitak.bottomappbars

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private var currentFabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(bottom_app_bar)

        val addVisibilityChanged: FloatingActionButton.OnVisibilityChangedListener = object : FloatingActionButton.OnVisibilityChangedListener() {
            override fun onShown(fab: FloatingActionButton?) {
                super.onShown(fab)
            }

            override fun onHidden(fab: FloatingActionButton?) {
                super.onHidden(fab)
                bottom_app_bar.toggleFabAlignment()
                bottom_app_bar.replaceMenu(
                        if (currentFabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER) R.menu.bottomappbar_menu_secondary
                        else R.menu.bottomappbar_menu
                )
                bottom_app_bar_fab?.setImageDrawable(
                        if (currentFabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER) getDrawable(R.drawable.ic_arrow_back)
                        else getDrawable(R.drawable.ic_add)
                )
                bottom_app_bar_fab?.show()

            }

        }

        toggle_fab_alignment_button.setOnClickListener {
            bottom_app_bar_fab.hide(addVisibilityChanged)
            invalidateOptionsMenu()
            bottom_app_bar.navigationIcon = if (bottom_app_bar.navigationIcon != null) null
            else getDrawable(R.drawable.ic_menu)
            when(screen_label.text) {
                getString(R.string.primary_screen_text) -> screen_label.text = getString(R.string.secondary_sceen_text)
                getString(R.string.secondary_sceen_text) -> screen_label.text = getString(R.string.primary_screen_text)
            }
        }

        bottom_app_bar_fab.setOnClickListener {
            toast("Fab")
        }

    }

    private fun BottomAppBar.toggleFabAlignment() {
        currentFabAlignmentMode = fabAlignmentMode
        fabAlignmentMode = currentFabAlignmentMode.xor(1)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.bottomappbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                val bottomNavDrawerFragment = BottomNavigationDrawerFragment()
                bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
            }
            R.id.app_bar_search -> toast("Search")
        }
        return true
    }
    fun Context.toast(message: CharSequence) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.BOTTOM, 0, 325)
        toast.show()
    }
}
