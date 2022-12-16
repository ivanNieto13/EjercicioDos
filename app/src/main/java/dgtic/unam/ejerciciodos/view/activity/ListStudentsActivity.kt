package dgtic.unam.ejerciciodos.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dgtic.unam.ejerciciodos.R
import dgtic.unam.ejerciciodos.databinding.ActivityAddStudentBinding
import dgtic.unam.ejerciciodos.databinding.ActivityListStudentsBinding
import dgtic.unam.ejerciciodos.model.FormStatusModel
import dgtic.unam.ejerciciodos.view.fragment.FormFragment
import dgtic.unam.ejerciciodos.view.fragment.ListStudentsFragment

class ListStudentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListStudentsBinding
    private lateinit var listStudentsFragment: ListStudentsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListStudentsBinding.inflate(layoutInflater)
        listStudentsFragment = ListStudentsFragment()
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        intent.getStringExtra("formStatus").let {
            when (it?.let { it1 -> FormStatusModel.valueOf(it1) }) {
                FormStatusModel.VIEW -> {
                    supportFragmentManager
                        .beginTransaction()
                        .add(binding.flStudentsFragmentContainer.id, listStudentsFragment)
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