package com.pws.agu.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.pws.agu.model.ModelFollowing
import com.pws.agu.view.fragment.FragmentFollowing
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception



class ViewModelFollowing : ViewModel() {

    private val listfollowingNonMutable = ArrayList<ModelFollowing>()
    private val listfollowingMutable = MutableLiveData<ArrayList<ModelFollowing>>()

    fun getListFollowing(): LiveData<ArrayList<ModelFollowing>> {
        return listfollowingMutable
    }

    fun getDataGitFollowing(context: Context, id: String) {
        val httpClient = AsyncHttpClient()
        httpClient.addHeader("Authorization", "token $paToken")
        httpClient.addHeader("User-Agent", "request")
        val urlClient = "https://api.github.com/users/$id/following"

        httpClient.get(urlClient, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = responseBody?.let { String(it) }
                Log.d(FragmentFollowing.TAG, result)
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val usernameLogin = jsonObject.getString("login")
                        getDataGitDetailFollowing(usernameLogin, context)
                    }

                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden ,API rate limit exceeded While Get Data Following"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun getDataGitDetailFollowing(usernameLogin: String, context: Context) {
        val httpClient = AsyncHttpClient()
        httpClient.addHeader("Authorization", "token $paToken")
        httpClient.addHeader("User-Agent", "request")
        val urlClient = "https://api.github.com/users/$usernameLogin"

        httpClient.get(urlClient, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = responseBody?.let { String(it) }
                Log.d(FragmentFollowing.TAG, result)

                try {
                    val jsonObject = JSONObject(result)
                    val usersData = ModelFollowing()
                    usersData.userName = jsonObject.getString("login")
                    usersData.name = jsonObject.getString("name")
                    usersData.avatar = jsonObject.getString("avatar_url")
                    usersData.company = jsonObject.getString("company")
                    usersData.location = jsonObject.getString("location")
                    usersData.repository = jsonObject.getString("public_repos")
                    usersData.following = jsonObject.getString("following")
                    usersData.following = jsonObject.getString("following")
                    listfollowingNonMutable.add(usersData)
                    listfollowingMutable.postValue(listfollowingNonMutable)
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }
}