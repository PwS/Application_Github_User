package com.pws.agu.view.activity

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pws.agu.R
import com.pws.agu.adapter.AdapterDetailViewPager
import com.pws.agu.model.ModelUsers
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DETAIL = "EXTRA_DETAIL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.title = "Detail User"
        setData()
        viewPagerConfig()

    }

    private fun viewPagerConfig() {
        val viewPagerDetail = AdapterDetailViewPager(this, supportFragmentManager)
        viewpager.adapter = viewPagerDetail
        tabs.setupWithViewPager(viewpager)
        supportActionBar?.elevation = 0f
    }

    private fun setData() {
        val dataUser = intent.getParcelableExtra(EXTRA_DETAIL) as ModelUsers
        Glide.with(this)
            .load(dataUser.avatar)
            .apply(RequestOptions().override(150, 130))
            .into(avatars)
        fullName.text = dataUser.name
        username.text = dataUser.userName
        company.text = dataUser.company
        location.text = dataUser.location
        following.text = dataUser.following
        followers.text = dataUser.followers
        repositories.text = dataUser.repository
    }

}