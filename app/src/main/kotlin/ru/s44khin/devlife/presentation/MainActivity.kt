package ru.s44khin.devlife.presentation

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import ru.s44khin.devlife.R
import ru.s44khin.devlife.databinding.ActivityMainBinding
import ru.s44khin.devlife.presentation.card.FragmentLatest
import ru.s44khin.devlife.presentation.card.FragmentTop

class MainActivity : AppCompatActivity() {

    companion object {
        private const val LAST_USED_FRAGMENT = "LAST_USED_FRAGMENT"
        private const val SETTINGS = "SETTINGS"
    }

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val fragmentLatest = FragmentLatest.newInstance()
    private val fragmentTop = FragmentTop.newInstance()

    private val sharedPreferences by lazy {
        getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(binding.root)
        initNavigation()
    }

    private fun initNavigation() {
        binding.navigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_latest -> {
                    sharedPreferences.edit().putString(LAST_USED_FRAGMENT, FragmentLatest.TAG)
                        .apply()
                    supportFragmentManager.beginTransaction()
                        .replace(binding.fragmentContainerView.id, fragmentLatest)
                        .commit()
                    true
                }
                R.id.menu_top -> {
                    sharedPreferences.edit().putString(LAST_USED_FRAGMENT, FragmentTop.TAG).apply()
                    supportFragmentManager.beginTransaction()
                        .replace(binding.fragmentContainerView.id, fragmentTop)
                        .commit()
                    true
                }
                else -> {
                    error("gg")
                }
            }
        }

        val last = sharedPreferences.getString(LAST_USED_FRAGMENT, FragmentLatest.TAG)

        binding.navigationView.selectedItemId = if (last == FragmentLatest.TAG)
            R.id.menu_latest else R.id.menu_top
    }
}