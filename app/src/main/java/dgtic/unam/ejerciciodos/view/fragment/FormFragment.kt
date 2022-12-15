package dgtic.unam.ejerciciodos.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import dgtic.unam.ejerciciodos.databinding.FragmentFormBinding
import dgtic.unam.ejerciciodos.model.Student
import dgtic.unam.ejerciciodos.model.StudentsService
import dgtic.unam.ejerciciodos.model.Subject


class FormFragment : Fragment() {
    private var _binding: FragmentFormBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFormBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnAddStudent.setOnClickListener {
            val studentFormIsValid = validateStudentForm()
            val subjectsFormIsValid = validateSubjectsForm()

            if (studentFormIsValid && subjectsFormIsValid) {
                val student = Student(
                    binding.etStudentAccount.text.toString(),
                    binding.etStudentName.text.toString(),
                    binding.etStudentAge.text.toString().toInt(),
                    listOf(
                        Subject(
                            generateId() + 1,
                            binding.etStudentAssignatureName1.text.toString(),
                            binding.etStudentAssignatureCredits1.text.toString().toInt(),
                        ),
                        Subject(
                            generateId() + 2,
                            binding.etStudentAssignatureName2.text.toString(),
                            binding.etStudentAssignatureCredits2.text.toString().toInt(),
                        ),
                        Subject(
                            generateId() + 3,
                            binding.etStudentAssignatureName3.text.toString(),
                            binding.etStudentAssignatureCredits3.text.toString().toInt(),
                        )
                    )
                )
                activity?.let { it1 ->
                    StudentsService(it1).addStudent(student)
                    Toast.makeText(it1, "Alumno agregado", Toast.LENGTH_SHORT).show()
                    activity?.onBackPressed()
                }
            }

        }

        return root

    }

    private fun generateId(): Int {
        return (0..100).random()
    }

    private fun validateStudentForm(): Boolean {
        with(binding) {
            val studentAccount = etStudentAccount.text.toString()
            if (studentAccount.isEmpty()) {
                etStudentAccount.error = "El campo no puede estar vacío"
                return false
            }
            val studentName = etStudentName.text.toString()
            if (studentName.isEmpty()) {
                etStudentName.error = "El campo no puede estar vacío"
                return false
            }
            val studentAge = etStudentAge.text.toString()
            if (studentAge.isEmpty()) {
                etStudentAge.error = "El campo no puede estar vacío"
                return false
            }
            return true
        }
    }

    private fun validateSubjectsForm(): Boolean {
        with(binding) {
            val etSubjectName1 = etStudentAssignatureName1.text.toString()
            if (etSubjectName1.isEmpty()) {
                etStudentAssignatureName1.error = "Campo requerido"
                return false
            }
            val etSubjectCredits1 = etStudentAssignatureCredits1.text.toString()
            if (etSubjectCredits1.isEmpty()) {
                etStudentAssignatureCredits1.error = "Campo requerido"
                return false
            }
            val etSubjectName2 = etStudentAssignatureName2.text.toString()
            if (etSubjectName2.isEmpty()) {
                etStudentAssignatureName2.error = "Campo requerido"
                return false
            }
            val etSubjectCredits2 = etStudentAssignatureCredits2.text.toString()
            if (etSubjectCredits2.isEmpty()) {
                etStudentAssignatureCredits2.error = "Campo requerido"
                return false
            }
            val etSubjectName3 = etStudentAssignatureName3.text.toString()
            if (etSubjectName3.isEmpty()) {
                etStudentAssignatureName3.error = "Campo requerido"
                return false
            }
            val etSubjectCredits3 = etStudentAssignatureCredits3.text.toString()
            if (etSubjectCredits3.isEmpty()) {
                etStudentAssignatureCredits3.error = "Campo requerido"
                return false
            }
            return true
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        println("back")
    }

}