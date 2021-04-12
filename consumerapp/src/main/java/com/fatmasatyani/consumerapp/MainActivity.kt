package com.fatmasatyani.consumerapp

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fatmasatyani.consumerapp.adapter.MainAdapter
import com.fatmasatyani.consumerapp.data.FavoriteData
import com.fatmasatyani.consumerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        fetchDataFromContentProvider()
    }

    private fun fetchDataFromContentProvider() {
        val table = "favorite_data"
        val authority = "com.fatmasatyani.githser"

        val uri: Uri = Uri.Builder()
                .scheme("content")
                .authority(authority)
                .appendPath(table)
                .build()

        val contentResolver = this.contentResolver
        val cursor = contentResolver.query(
                uri,
                null,
                null,
                null,
                null
        )
        setAdapter(cursor)
    }

    private fun setAdapter(cursor: Cursor?) {
        val mainAdapter = MainAdapter()
        val favorites = convertCursor(cursor)
        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mainAdapter
        }
        mainAdapter.setItems(favorites)
    }

    private fun convertCursor(cursor: Cursor?): ArrayList<FavoriteData> {
        val favoriteData = ArrayList<FavoriteData>()

        cursor?.apply {
            while (moveToNext()) {
                val username = getString(getColumnIndexOrThrow("login"))
                val avatarUrl = getString(getColumnIndexOrThrow("avatarUrl"))
                favoriteData.add(FavoriteData(username, avatarUrl))
            }
        }
        return favoriteData
    }
}