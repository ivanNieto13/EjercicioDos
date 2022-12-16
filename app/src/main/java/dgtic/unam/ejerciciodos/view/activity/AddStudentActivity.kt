package dgtic.unam.ejerciciodos.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dgtic.unam.ejerciciodos.databinding.ActivityAddStudentBinding
import dgtic.unam.ejerciciodos.model.FormStatusModel
import dgtic.unam.ejerciciodos.view.fragment.FormFragment

class AddStudentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddStudentBinding
    private lateinit var formFragment: FormFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStudentBinding.inflate(layoutInflater)
        formFragment = FormFragment()
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        intent.getStringExtra("formStatus").let {
            when (it?.let { it1 -> FormStatusModel.valueOf(it1) }) {
                FormStatusModel.ADD -> {
                    supportFragmentManager
                        .beginTransaction()
                        .add(binding.flFormFragmentContainer.id, formFragment)
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