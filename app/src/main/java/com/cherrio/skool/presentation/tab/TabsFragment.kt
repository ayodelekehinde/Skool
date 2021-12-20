package com.cherrio.skool.presentation.tab

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.cherrio.skool.R
import com.cherrio.skool.base.BaseFragment
import com.cherrio.skool.databinding.FragmentTabsBinding
import com.cherrio.skool.presentation.MainViewModel
import com.cherrio.skool.presentation.filter.FilterDialog

/**
 *Created by Ayodele on 12/7/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

class TabsFragment : BaseFragment<FragmentTabsBinding>(),
    NavController.OnDestinationChangedListener {

    val mainViewModel by activityViewModels<MainViewModel>()

    override fun getBinding(inflater: LayoutInflater): FragmentTabsBinding {
        return FragmentTabsBinding.inflate(inflater)
    }

    override fun useViews() {
        val appBarConfiguration =
            AppBarConfiguration(setOf(R.id.tab_saved, R.id.tab_search, R.id.tab_home))
        val navhost =
            childFragmentManager.findFragmentById(R.id.tab_navigation) as? NavHostFragment
        val controller = navhost?.navController
        binding.bottomNav.setupWithNavController(controller!!)
        controller.addOnDestinationChangedListener(this)
    }

    override fun useState() {
        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.btn_filter) {
                openFilterDialog()
            }
            return@setOnMenuItemClickListener true
        }
        val searchView = binding.toolbar.menu.findItem(R.id.btn_search).actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    hideKeyboardWindow()
                    mainViewModel.setQuery(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        } )
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        val filterItem = binding.toolbar.menu.findItem(R.id.btn_filter)
        val searchMenu = binding.toolbar.menu.findItem(R.id.btn_search)

        var title = ""
        when (destination.id) {
            R.id.tab_home -> {
                title = resources.getString(R.string.txt_home)
                filterItem.isVisible = true
                searchMenu.isVisible = false
            }
            R.id.tab_saved -> {
                title = resources.getString(R.string.txt_favourites)
                filterItem.isVisible = false
                searchMenu.isVisible = false

            }
            R.id.tab_search -> {
                title = resources.getString(R.string.txt_search)
                filterItem.isVisible = false
                searchMenu.isVisible = true

            }

        }
        binding.toolbar.title = title
    }

    private fun openFilterDialog() {
        FilterDialog().show(childFragmentManager, FilterDialog.TAG)
    }
    private fun hideKeyboardWindow() {
        val mInputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        requireActivity().currentFocus?.let {
            mInputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}