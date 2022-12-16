package dgtic.unam.ejerciciodos.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import dgtic.unam.ejerciciodos.R
import dgtic.unam.ejerciciodos.databinding.ActivityMainBinding
import dgtic.unam.ejerciciodos.model.FormStatusModel

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawer: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()
    }

    private fun initToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        drawer = binding.drawerLayout
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar,
            R.string.open_menu,
            R.string.close_menu
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu)
        initNavigationView()
    }

    private fun initNavigationView() {
        val navigationView: NavigationView = binding.navView
        navigationView.setNavigationItemSelectedListener(this)
        val headerView: View = LayoutInflater.from(this).inflate(
            R.layout.header_main,
            navigationView, false
        )
        navigationView.addHeaderView(headerView)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addStudent -> {
                val i = Intent(this, AddStudentActivity::class.java)
                i.putExtra("formStatus", FormStatusModel.ADD.name)
                startActivity(i)
            }
            R.id.getStudents -> {
                val i = Intent(this, ListStudentsActivity::class.java)
                i.putExtra("formStatus", FormStatusModel.VIEW.name)
                startActivity(i)
            }
            R.id.searchStudent -> {
                val i = Intent(this, SearchStudentActivity::class.java)
                i.putExtra("formStatus", FormStatusModel.SEARCH.name)
                startActivity(i)
            }
            R.id.deleteStudent -> {
                val i = Intent(this, DeleteStudentActivity::class.java)
                i.putExtra("formStatus", FormStatusModel.DELETE.name)
                startActivity(i)
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}