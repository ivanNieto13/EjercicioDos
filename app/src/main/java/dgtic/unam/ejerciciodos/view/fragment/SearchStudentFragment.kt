package dgtic.unam.ejerciciodos.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import dgtic.unam.ejerciciodos.databinding.ActivitySearchStudentBinding
import dgtic.unam.ejerciciodos.databinding.FragmentPreviewStudentsBinding
import dgtic.unam.ejerciciodos.databinding.FragmentSearchStudentBinding
import dgtic.unam.ejerciciodos.model.Student
import dgtic.unam.ejerciciodos.model.Subject
import dgtic.unam.ejerciciodos.model.VolleyAPI
import dgtic.unam.ejerciciodos.view.adapters.StudentAdapter
import org.json.JSONArray
import org.json.JSONObject

class SearchStudentFragment: Fragment() {
    private var _binding: FragmentSearchStudentBinding? = null
    private val binding get() = _binding!!
    private lateinit var volleyAPI: VolleyAPI
    private val ipPuerto = "192.168.1.100:8080"
    private var studentList = ArrayList<Student>()
    private lateinit var studentsAdapter: StudentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchStudentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        volleyAPI = VolleyAPI(requireContext())

        binding.btnSearchStudent.setOnClickListener {
            val cuenta = binding.etSearchStudent.text.toString()
            if (cuenta.isNotEmpty()) {
                searchStudent(cuenta)
            } else if (cuenta.isEmpty()) {
                binding.etSearchStudent.error = "Campo vacío"
            }
            if ("(\\s)".toRegex().containsMatchIn(cuenta)) {
                Toast.makeText(requireContext(), "No se permiten espacios", Toast.LENGTH_SHORT).show()
            }


        }

        return root
    }

    fun searchStudent(cuenta: String) {
            val urlJSON = "http://$ipPuerto/id/$cuenta"
            val jsonRequest = object : JsonObjectRequest(
                Method.GET, urlJSON, null,
                Response.Listener<JSONObject> { student ->
                    val subjects = student.getJSONArray("materias")
                    val subjectList = ArrayList<Subject>()
                    val cuenta = student.get("cuenta").toString()
                    val name = student.get("nombre").toString()
                    val age = student.get("edad").toString().toInt()
                    (0 until subjects.length()).forEach {
                        val datos = subjects.getJSONObject(it)
                        val id = datos.get("id").toString()
                        val nombre= datos.get("nombre").toString()
                        val creditos = datos.get("creditos").toString()
                        val subject = Subject(id.toInt(), nombre, creditos.toInt())
                        subjectList.add(subject)
                    }
                    val studentObject = Student(cuenta, name, age, subjectList)
                    binding.tvStudentAccountSearch.text = studentObject.account
                    binding.tvStudentNameSearch.text = studentObject.name
                    binding.tvStudentAgeSearch.text = studentObject.age.toString()
                    if (subjectList[0].name != null) {
                        binding.tvStudentSubjectName1Search.text = subjectList[0].name
                        binding.tvStudentSubjectCredits1Search.text = subjectList[0].credits.toString()
                    }
                    if (subjectList.size > 1) {
                        binding.tvStudentSubjectName2Search.text = subjectList[1].name
                        binding.tvStudentSubjectCredits2Search.text = subjectList[1].credits.toString()
                    }
                    if (subjectList.size > 2) {
                        binding.tvStudentSubjectName3Search.text = subjectList[2].name
                        binding.tvStudentSubjectCredits3Search.text = subjectList[2].credits.toString()
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(requireContext(), "No se encontró el alumno", Toast.LENGTH_SHORT).show()
                    binding.tvStudentAccountSearch.text = ""
                    binding.tvStudentNameSearch.text = ""
                    binding.tvStudentAgeSearch.text = ""
                    binding.tvStudentSubjectName1Search.text = ""
                    binding.tvStudentSubjectName2Search.text = ""
                    binding.tvStudentSubjectName3Search.text = ""
                    binding.tvStudentSubjectCredits1Search.text = ""
                    binding.tvStudentSubjectCredits2Search.text = ""
                    binding.tvStudentSubjectCredits3Search.text = ""
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["User-Agent"] =
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36"
                    return headers
                }
            }
            volleyAPI.add(jsonRequest)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        println("back")
    }

}