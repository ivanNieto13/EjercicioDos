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
import dgtic.unam.ejerciciodos.databinding.FragmentPreviewStudentsBinding
import dgtic.unam.ejerciciodos.model.Student
import dgtic.unam.ejerciciodos.model.StudentsService
import dgtic.unam.ejerciciodos.model.Subject
import dgtic.unam.ejerciciodos.model.VolleyAPI
import dgtic.unam.ejerciciodos.view.adapters.StudentAdapter
import org.json.JSONArray


class ListStudentsFragment : Fragment() {
    private var _binding: FragmentPreviewStudentsBinding? = null
    private val binding get() = _binding!!
    private lateinit var volleyAPI: VolleyAPI
    private val ipPuerto = "192.168.1.100:8080"
    private var studentList = ArrayList<Student>()
    private lateinit var studentsAdapter: StudentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPreviewStudentsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        volleyAPI = VolleyAPI(requireContext())

        activity?.let {
            studentsAdapter = StudentAdapter(it, studentList)
            binding.rvPreviewStudents.layoutManager = LinearLayoutManager(it)
            binding.rvPreviewStudents.adapter = studentsAdapter
            getStudents()
        }
        return root
    }

    fun getStudents() {
        val urlJSON = "http://$ipPuerto/estudiantesJSON"
        val jsonRequest =
            object : JsonArrayRequest(urlJSON, Response.Listener<JSONArray> { response ->
                (0 until response.length()).forEach { it ->
                    val student = response.getJSONObject(it)
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
                    studentList.add(studentObject)
                }
                studentsAdapter.notifyDataSetChanged()

            }, Response.ErrorListener { error ->
                println(error.toString())

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