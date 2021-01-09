package com.pws.agu.view.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pws.agu.R
import com.pws.agu.adapter.AdapterListDataUsers
import com.pws.agu.model.ModelUsers
import com.pws.agu.util.Util
import com.pws.agu.viewmodel.ViewModelMain
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var listDataUser: ArrayList<ModelUsers> = ArrayList()
    private lateinit var listAdapter: AdapterListDataUsers
    private lateinit var mainViewModel: ViewModelMain

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listAdapter = AdapterListDataUsers(listDataUser)
        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(ViewModelMain::class.java)
        try {
            if (isOnline(this)) {
                viewConfig()
                runGetDataGit()
                searchUser()
                configMainViewModel(listAdapter)
            }
            else{
                Toast.makeText(this@MainActivity, "Connection Offline", Toast.LENGTH_SHORT).show()
            }
        }
        catch (ex:Exception){
            Toast.makeText(this@MainActivity, ex.localizedMessage, Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.change_language) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)

        }
        return super.onOptionsItemSelected(item)
    }

    private fun viewConfig() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        listAdapter.notifyDataSetChanged()
        recyclerView.adapter = listAdapter
        showLoading(true)
    }

    private fun runGetDataGit() {
        print("Ini Adalah alamat dari ${Util.url}/users")
        mainViewModel.getDataGit(applicationContext)
        showLoading(true)
    }


    private fun configMainViewModel(adapter: AdapterListDataUsers) {
        mainViewModel.getListUsers().observe(this,  { listUsers ->
            if (listUsers != null) {
                adapter.setData(listUsers)
                showLoading(false)
            }
        })
    }


    private fun showLoading(state: Boolean) {
        if (state) {
            loadingProgress.visibility = View.VISIBLE
        } else {
            loadingProgress.visibility = View.INVISIBLE
        }
    }

    private fun searchUser() {
        user_search.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    showLoading(true)
                    mainViewModel.getDataGitSearch(query, applicationContext)
                } else {
                    return true
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })
    }
}