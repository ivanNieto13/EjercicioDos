package dgtic.unam.ejerciciodos.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dgtic.unam.ejerciciodos.databinding.ActivityDeleteStudentBinding
import dgtic.unam.ejerciciodos.databinding.ActivitySearchStudentBinding
import dgtic.unam.ejerciciodos.model.FormStatusModel
import dgtic.unam.ejerciciodos.view.fragment.DeleteStudentFragment
import dgtic.unam.ejerciciodos.view.fragment.SearchStudentFragment

class DeleteStudentActivity: AppCompatActivity() {
    private lateinit var binding: ActivityDeleteStudentBinding
    private lateinit var deleteStudentFragment: DeleteStudentFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteStudentBinding.inflate(layoutInflater)
        deleteStudentFragment = DeleteStudentFragment()
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        intent.getStringExtra("formStatus").let {
            when (it?.let { it1 -> FormStatusModel.valueOf(it1) }) {
                FormStatusModel.DELETE -> {
                    supportFragmentManager
                        .beginTransaction()
                        .add(binding.flDeleteStudentFragmentContainer.id, deleteStudentFragment)
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