package com.fatmasatyani.consumerapp

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fatmasatyani.consumerapp.adapter.MainAdapter
import com.fatmasatyani.consumerapp.data.FavoriteData
import com.fatmasatyani.consumerapp.databinding.ActivityMainBinding
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {

//    private val mainAdapter: MainAdapter by lazy {
//        MainAdapter( )
//    }

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)

    }

//    private fun setAdapter(cursor: Cursor?) {
//        rvFavorite.apply {
//            layoutManager = LinearLayoutManager(this@MainActivity)
//            adapter = MainAdapter(convertCursor(cursor))
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

//        getFavUserData()
        setContentView(binding.root)
        fetchDataFromContentProvider()
    }


//    private fun setIllustration(state: Boolen) {
//
//    }

//    @SuppressLint("Recycle")
//    private fun getFavUserData() {
//        val table = "favorite_data"
//        val authority = "com.fatmasatyani.githser.provider"
//
//
//    }

//    private fun setIllustration(state: Boolean) {
//
//
//    }

//
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

//       (cursor != null && cursor.count > 0)
        setAdapter(cursor)

    }

    private fun setAdapter(cursor: Cursor?) {
        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MainAdapter(convertCursor(cursor))
        }

    }

    private fun convertCursor(cursor: Cursor?): ArrayList<FavoriteData> {
        val favoriteData = ArrayList<FavoriteData>()

        Log.d("<RESULT>", "convertCursor: ${cursor == null}")

        cursor?.apply {
            while (moveToNext()) {
                val username = getString(getColumnIndexOrThrow("login"))
                favoriteData.add(FavoriteData(username, avatar = 0))
            }
        }
        Log.d("<RESULT>", "convertCursor: ${Gson().toJson(favoriteData)}")
        return favoriteData
    }
}


        
//        if (cursor !=null && cursor.count >0) {
//            setIllustration(false)
//        }
//
//        if (cursor != null) {
//            initAdapter(cursor)
//        } else {
//            initAdapter(cursor)
//        }
//    }
//
//    private fun initAdapter(cursor: Cursor?) {
//        binding.rvFavorite.apply {
//            layoutManager = LinearLayoutManager(this@MainActivity)
//            adapter = mainAdapter
//        }
//        cursor?.let {
//            mainAdapter.setItems(it.toListUserFavorite())
//        }
//    }

//
//    private lateinit var binding: ActivityMainBinding
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        initBinding()
//        initBottomNav()
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.options,menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.action_reminder -> {
//                val mIntent = Intent(this, NotificationSettings::class.java)
//                startActivity(mIntent)
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }
//
//    private fun initBinding() {
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        val view = binding.root
//        setContentView(view)
//    }

//    private fun initBottomNav() {
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        val navController = navHostFragment.navController
//        NavigationUI.setupWithNavController(binding.bottomNv,navController)
//    }


