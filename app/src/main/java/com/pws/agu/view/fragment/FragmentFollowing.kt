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
import com.pws.agu.adapter.AdapterListDataFollowing
import com.pws.agu.model.ModelFollowing
import com.pws.agu.model.ModelUsers
import com.pws.agu.viewmodel.ViewModelFollowing
import kotlinx.android.synthetic.main.fragment_following.*

class FragmentFollowing : Fragment() {

    companion object {
        val TAG = FragmentFollowing::class.java.simpleName
        const val Following = "EXTRA_DETAIL"
    }

    private var listData: ArrayList<ModelFollowing> = ArrayList()
    private lateinit var adapter: AdapterListDataFollowing
    private lateinit var followingViewModel: ViewModelFollowing

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = AdapterListDataFollowing(listData)
        followingViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(ViewModelFollowing::class.java)

        val dataUser = activity?.intent?.getParcelableExtra(Following) as ModelUsers
        config()

        followingViewModel.getDataGitFollowing(
            activity!!.applicationContext,
            dataUser.userName.toString()
        )
        showLoading(true)

        followingViewModel.getListFollowing().observe(activity!!, { listFollowing ->
            if (listFollowing != null) {
                adapter.setData(listFollowing)
                showLoading(false)
            }
        })
    }

    private fun config() {
        recycler_view_following.layoutManager = LinearLayoutManager(activity)
        recycler_view_following.setHasFixedSize(true)
        recycler_view_following.adapter = adapter
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressbar_following.visibility = View.VISIBLE
        } else {
            progressbar_following.visibility = View.INVISIBLE
        }
    }
}