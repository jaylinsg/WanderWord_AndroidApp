package com.bignerdranch.android.wanderword

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.core.content.ContextCompat

class MyBottomNavbar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val tabs: MutableList<ImageButton> = mutableListOf()
    private var selectedTabId: Int = -1

    init {
        orientation = HORIZONTAL
        setupTabs()
    }

    fun setNavigationItemSelectedListener(listener: (Int) -> Unit) {
        tabs.forEachIndexed { index, tab ->
            tab.setOnClickListener {
                if (selectedTabId != tab.id) {
                    highlightTab(tab.id)
                    listener(tab.id)
                }
            }
        }
    }

    private fun setupTabs() {
        createTab(R.drawable.ic_home, R.id.action_home)
        createTab(R.drawable.ic_friends, R.id.action_friends)
        createTab(R.drawable.ic_collection, R.id.action_collection)
        createTab(R.drawable.ic_settings, R.id.action_settings)
    }

    private fun createTab(iconResId: Int, tabId: Int) {
        val tab = ImageButton(context)
        tab.layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f)
        tab.setImageResource(iconResId)
        tab.setBackgroundColor(ContextCompat.getColor(context, R.color.orange))
        tab.id = tabId
        tabs.add(tab)
        addView(tab)
    }

    private fun highlightTab(tabId: Int) {
        tabs.forEach { tab ->
            tab.setBackgroundColor(
                if (tab.id == tabId)
                    ContextCompat.getColor(context, R.color.orange)
                else
                    ContextCompat.getColor(context, R.color.purple_700)
            )
        }
        selectedTabId = tabId
    }
}