package dgtic.unam.ejerciciodos.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dgtic.unam.ejerciciodos.R
import dgtic.unam.ejerciciodos.databinding.ActivityListStudentsBinding
import dgtic.unam.ejerciciodos.databinding.ActivitySearchStudentBinding
import dgtic.unam.ejerciciodos.model.FormStatusModel
import dgtic.unam.ejerciciodos.view.fragment.ListStudentsFragment
import dgtic.unam.ejerciciodos.view.fragment.SearchStudentFragment

class SearchStudentActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchStudentBinding
    private lateinit var searchStudentFragment: SearchStudentFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchStudentBinding.inflate(layoutInflater)
        searchStudentFragment = SearchStudentFragment()
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        intent.getStringExtra("formStatus").let {
            when (it?.let { it1 -> FormStatusModel.valueOf(it1) }) {
                FormStatusModel.SEARCH -> {
                    supportFragmentManager
                        .beginTransaction()
                        .add(binding.flSearchStudentFragmentContainer.id, searchStudentFragment)
                        .commit()
                }
                else -> {
                    // TODO: 10/10/2021
                    println("nothing")
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}