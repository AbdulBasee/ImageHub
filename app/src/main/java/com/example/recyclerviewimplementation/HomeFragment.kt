package com.example.recyclerviewimplementation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewimplementation.databinding.ActivityMainBinding
import com.example.recyclerviewimplementation.models.Post
import com.example.recyclerviewimplementation.models.User
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query



private const val TAG = "PostsActivity"
class HomeFragment : Fragment() {


    private var signedInUser: User? = null
    private lateinit var binding: ActivityMainBinding
    private lateinit var firestoreDb: FirebaseFirestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var itemList:ArrayList<Int>
    private lateinit var floatingactionbuttoin: FloatingActionButton

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<itemAdapter.ItemViewHolder>? = null
//    private var recyclerView: RecyclerView? = null


//    private lateinit var itemsAdapter: itemAdapter

    private lateinit var posts: MutableList<Post>
//    private lateinit var adapter: itemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_home, container, false)

        posts = mutableListOf()
//        val floatingactionbuttoin = view?.findViewById<FloatingActionButton>(R.id.fabCreate)

//        floatingactionbuttoin = view?.findViewById(R.id.fabCreate)!!
//        recyclerView = view?.findViewById(R.id.rvPosts)!!

        recyclerView = v.findViewById(R.id.rvPosts)
        adapter = itemAdapter(v.context, posts)


        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(context)


        firestoreDb = FirebaseFirestore.getInstance()
        firestoreDb.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid as String)
            .get()
            .addOnSuccessListener { userSnapShot ->
                signedInUser = userSnapShot.toObject(User::class.java)
                Log.i(TAG, "signed in user: $signedInUser")
            }
            .addOnFailureListener{ exception ->
                Log.i(TAG, "Failure fetching signed in user", exception)
            }

        firestoreDb = FirebaseFirestore.getInstance()
        var postsReference = firestoreDb
            .collection("posts")
            .limit(20)
            .orderBy("creation_time_ms", Query.Direction.DESCENDING)

        val username = getActivity()?.getIntent()?.getStringExtra(EXTRA_USERNAME)
//        intent.getStringExtra(EXTRA_USERNAME)
        if(username != null){
            (activity as AppCompatActivity).supportActionBar?.title = username
            postsReference =  postsReference.whereEqualTo("user.username", username)
        }

        postsReference.addSnapshotListener { snapshot, exception ->
            if(exception != null || snapshot == null){
                Log.e(TAG, "Exception when querying posts", exception)
                return@addSnapshotListener
            }


            var postList = snapshot.toObjects(Post::class.java)
            posts.clear()
            posts.addAll(postList)
            (adapter as itemAdapter).notifyDataSetChanged()
            for(post in postList){

                Log.i(TAG, "Post ${post}")
            }
        }


//        floatingactionbuttoin.setOnClickListener{
////            val intent  = Intent(activity, CreateActivity::class.java)
////            startActivity(intent)
//
//            (activity as? MainActivity)?.let{
//                val intent = Intent (it, CreateActivity::class.java)
//                it.startActivity(intent)
//            }
//
//        }


        return v
    }








}