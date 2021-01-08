package com.pws.agu.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pws.agu.R
import com.pws.agu.adapter.AdapterListDataFollowers
import com.pws.agu.model.ModelFollowers
import com.pws.agu.model.ModelUsers
import com.pws.agu.viewmodel.ViewModelFollowers
import kotlinx.android.synthetic.main.fragment_followers.*

class FragmentFollowers : Fragment() {

    companion object {
        val TAG = FragmentFollowers::class.java.simpleName
        const val Followers = "EXTRA_DETAIL"
    }

    private var listData: ArrayList<ModelFollowers> = ArrayList()
    private lateinit var adapter: AdapterListDataFollowers
    private lateinit var followerViewModel: ViewModelFollowers

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = AdapterListDataFollowers(listData)
        followerViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(ViewModelFollowers::class.java)

        val dataUser = activity?.intent?.getParcelableExtra(Followers) as ModelUsers
        config()

        followerViewModel.getDataGitFollowers(activity!!.applicationContext, dataUser.userName.toString())
        showLoading(true)

        followerViewModel.getListFollowers().observe(activity!!, { listFollower ->
            if (listFollower != null) {
                adapter.setData(listFollower)
                showLoading(false)
            }
        })
    }

    private fun config() {
        recycler_view_followers.layoutManager = LinearLayoutManager(activity)
        recycler_view_followers.setHasFixedSize(true)
        recycler_view_followers.adapter = adapter
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressbar_followers.visibility = View.VISIBLE
        } else {
            progressbar_followers.visibility = View.INVISIBLE
        }
    }
}