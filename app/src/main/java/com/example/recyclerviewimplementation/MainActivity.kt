package com.example.recyclerviewimplementation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewimplementation.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.recyclerviewimplementation.models.Post
import com.example.recyclerviewimplementation.models.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.api.Distribution
import com.google.firebase.auth.FirebaseAuth

//class MainActivity : AppCompatActivity() {
//
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var itemList:ArrayList<Int>
//    private lateinit var itemsAdapter: itemAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//
//        init()
//
//    }
//    private fun init(){
//        itemList = ArrayList()
//        recyclerView = findViewById(R.id.recyclerview)
//        recyclerView.setHasFixedSize(true)
//        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
//
//        addToList()
//        Log.i("Before itemsAdapter", "before items adapter")
//        itemsAdapter = itemAdapter(itemList)
//        Log.i("tag", "after call itemsAdatper")
//        recyclerView.adapter = itemsAdapter
//
//
////        recyclerView.setHasFixedSize(true)
//
//    }
//
//    private fun addToList(){
////        itemList.add(R.drawable.Cat03)
////        itemList.add(R.drawable.Moon)
//        itemList.add(R.drawable.aksh1)
//    }
//}




//private const val TAG = "PostsActivity"
//val EXTRA_USERNAME = "EXTRA_USERNAME"
//
//open class MainActivity : AppCompatActivity() {
//
//
//    private var signedInUser: User? = null
//    private lateinit var binding: ActivityMainBinding
//    private lateinit var firestoreDb: FirebaseFirestore
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var itemList:ArrayList<Int>
//    private lateinit var floatingactionbuttoin:FloatingActionButton
////    private lateinit var itemsAdapter: itemAdapter
//
//
//    private lateinit var posts: MutableList<Post>
//    private lateinit var adapter: itemAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        binding = ActivityMainBinding.inflate(layoutInflater)
////        val view = binding.root
//        setContentView(R.layout.activity_main)
//        // Create the layout file
//        // create data source
//        posts = mutableListOf()
//        floatingactionbuttoin = findViewById(R.id.fabCreate)
//        recyclerView = findViewById(R.id.rvPosts)
////        recyclerView.setHasFixedSize(true)
//
////        addToList()
//
//        adapter = itemAdapter(this, posts)
//
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        firestoreDb = FirebaseFirestore.getInstance()
//        firestoreDb.collection("users")
//            .document(FirebaseAuth.getInstance().currentUser?.uid as String)
//            .get()
//            .addOnSuccessListener { userSnapShot ->
//                signedInUser = userSnapShot.toObject(User::class.java)
//                Log.i(TAG, "signed in user: $signedInUser")
//            }
//            .addOnFailureListener{ exception ->
//                Log.i(TAG, "Failure fetching signed in user", exception)
//            }
//
//        firestoreDb = FirebaseFirestore.getInstance()
//        var postsReference = firestoreDb
//            .collection("posts")
//            .limit(20)
//            .orderBy("creation_time_ms", Query.Direction.DESCENDING)
//
//        val username = intent.getStringExtra(EXTRA_USERNAME)
//
//        if(username != null){
//            supportActionBar?.title = username
//            postsReference =  postsReference.whereEqualTo("user.username", username)
//        }
//
//        postsReference.addSnapshotListener { snapshot, exception ->
//            if(exception != null || snapshot == null){
//
//                Log.e(TAG, "Exception when querying posts", exception)
//                return@addSnapshotListener
//            }
//
//
//            var postList = snapshot.toObjects(Post::class.java)
//            posts.clear()
//            posts.addAll(postList)
//            adapter.notifyDataSetChanged()
//            for(post in postList){
//
//                Log.i(TAG, "Post ${post}")
//            }
//        }
//
//
//        floatingactionbuttoin.setOnClickListener{
//            val intent  = Intent(this, CreateActivity::class.java)
//            startActivity(intent)
//        }
//
//    }
//
//
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_posts , menu)
//
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if(item.itemId == R.id.menu_profile){
//            val intent = Intent(this, ProfileActivity::class.java)
//            intent.putExtra(EXTRA_USERNAME,signedInUser?.username)
//            startActivity(intent)
//        }
//        return super.onOptionsItemSelected(item)
//    }
//}



private const val TAG = "PostsActivity"
val EXTRA_USERNAME = "EXTRA_USERNAME"

open class MainActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val postFragment = PostFragment()
    private val kuttttaFragment = KuttttaFragment()

    private var signedInUser: User? = null

    private lateinit var bottom_navigation: BottomNavigationView



    private lateinit var posts: MutableList<Post>
    private lateinit var adapter: itemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        replaceFragment(homeFragment)

        bottom_navigation = findViewById(R.id.bottom_navigation)

        bottom_navigation.setOnNavigationItemSelectedListener  {
            when(it.itemId){
                R.id.ic_home -> replaceFragment(homeFragment)
                R.id.ic_post -> replaceFragment(postFragment)
            }
            true
        }


    }

    private fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val home = supportFragmentManager.beginTransaction()
            home.replace(R.id.fragment_container, fragment)
            home.commit()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_posts , menu)

        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_profile){
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra(EXTRA_USERNAME,signedInUser?.username)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}






