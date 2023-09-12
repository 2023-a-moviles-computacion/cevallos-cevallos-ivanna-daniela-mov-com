    package com.example.myapplication

    import RecyclerViewAdaptadorCategories
    import android.content.Intent
    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.util.Log
    import android.view.MenuItem
    import android.view.View
    import android.widget.Button
    import android.widget.EditText
    import android.widget.PopupMenu
    import android.widget.TextView
    import android.widget.Toast
    import androidx.appcompat.widget.Toolbar // Import the correct Toolbar class
    import androidx.appcompat.app.ActionBarDrawerToggle
    import androidx.core.view.GravityCompat
    import androidx.drawerlayout.widget.DrawerLayout
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView
    import com.firebase.ui.auth.data.model.User
    import com.google.android.material.navigation.NavigationView
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.firestore.FirebaseFirestore

    class SelectCategory : AppCompatActivity(), RecyclerViewAdaptadorCategories.OnItemClickListener {

        private lateinit var recyclerView: RecyclerView
        private lateinit var categoryAdapter: RecyclerViewAdaptadorCategories
       private val targetId: Int = 0
        private lateinit var drawerLayout: DrawerLayout
        private lateinit var drawerToggle: ActionBarDrawerToggle
        private lateinit var navigationView : NavigationView
        private lateinit var auth: FirebaseAuth

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_select_category)

            auth = FirebaseAuth.getInstance()

            drawerLayout = findViewById(R.id.slidemenu)
            navigationView = findViewById(R.id.nav_view)
            drawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
            drawerLayout.addDrawerListener(drawerToggle)
            drawerToggle.syncState()
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            navigationView.setNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.pesonalInfo -> {
                        showToast("My info")
                       true
                    }
                    R.id.MyAppointments -> {
                        irActividad2(UsersAppointments::class.java)
                       true
                    }
                    R.id.Logout -> {
                        logOut()
                       true
                    }
                    else -> false
                }
            }


            recyclerView = findViewById(R.id.rv_category)
            categoryAdapter = RecyclerViewAdaptadorCategories(this, ArrayList(), this)
            recyclerView.adapter = categoryAdapter
           recyclerView.layoutManager = LinearLayoutManager(this)
            fetchName()
            fetchCategories()

        }

        fun irActividad2(
            clase: Class<*>
        ){
            val intent = Intent(this, clase)
            startActivity(intent)
        }

        private fun logOut() {
            auth.signOut()
            showToast("Logged out successfully!")
        }

        override fun onItemClick(category: Category) {
            val categoryName = category.name
            Log.d("CategorySelected", "Category Name: $categoryName")
            irActividad(SelectAppointment::class.java, categoryName)
        }
        private fun fetchName() {
            val txtWelcome = findViewById<TextView>(R.id.txt_welcome)

            val user = FirebaseAuth.getInstance().currentUser
            val displayEmail = user?.email

            if (!displayEmail.isNullOrEmpty()) {
                val db = FirebaseFirestore.getInstance()
                db.collection("people")
                    .whereEqualTo("email", displayEmail)
                    .get()
                    .addOnSuccessListener { documents ->
                        if (!documents.isEmpty) {
                            val document = documents.documents[0]
                            val name = document.getString("name")
                            txtWelcome.text = "Hello $name"
                        } else {
                            txtWelcome.text = "No user found with this email"
                        }
                    }
                    .addOnFailureListener { exception ->
                        txtWelcome.text = "Error: ${exception.message}"
                    }
            } else {
                txtWelcome.text = "No email found"
            }
        }
        private fun fetchCategories() {
            val db = FirebaseFirestore.getInstance()

            db.collection("categories")
                .get()
                .addOnSuccessListener { documents ->
                    val categoryList = ArrayList<Category>()

                    for (document in documents) {
                        val categoryId = document.id
                        val categoryName = document.getString("name")
                        val categoryImageId = document.getString("imageID")
                        val category = Category(categoryId, categoryName, categoryImageId)
                        categoryList.add(category)
                    }
                    categoryAdapter.updateData(categoryList)
                }
                .addOnFailureListener {
                }
        }
        private fun showToast(message: String) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
        fun irActividad(clase: Class<*>, categoryName: String?){
            val intent = Intent(this, clase)
            intent.putExtra("categoryName",categoryName )
            startActivity(intent)
        }
        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                android.R.id.home -> {
                    // Handle the ActionBar's navigation icon click (usually the "hamburger" menu icon)
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        drawerLayout.closeDrawer(GravityCompat.START)
                    } else {
                        drawerLayout.openDrawer(GravityCompat.START)
                    }
                    return true
                }
                else -> return super.onOptionsItemSelected(item)
            }
        }
        override fun onBackPressed() {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                super.onBackPressed()
            }
        }





    }