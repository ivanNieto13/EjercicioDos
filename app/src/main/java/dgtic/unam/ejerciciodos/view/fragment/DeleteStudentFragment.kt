package dgtic.unam.ejerciciodos.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import dgtic.unam.ejerciciodos.databinding.FragmentDeleteStudentBinding
import dgtic.unam.ejerciciodos.model.VolleyAPI
import org.json.JSONArray

class DeleteStudentFragment: Fragment() {
    private var _binding: FragmentDeleteStudentBinding? = null
    private val binding get() = _binding!!
    private lateinit var volleyAPI: VolleyAPI
    private val ipPuerto = "192.168.1.100:8080"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDeleteStudentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        volleyAPI = VolleyAPI(requireContext())

        binding.btnSearchStudent.setOnClickListener {
            val cuenta = binding.etDeleteStudent.text.toString()
            if (cuenta.isNotEmpty()) {
                deleteStudent(cuenta)
            } else if (cuenta.isEmpty()) {
                binding.etDeleteStudent.error = "Campo vacío"
            }
            if ("(\\s)".toRegex().containsMatchIn(cuenta)) {
                Toast.makeText(requireContext(), "No se permiten espacios", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    fun deleteStudent(cuenta: String) {
        val urlJSON = "http://$ipPuerto/borrarestudiante/$cuenta"
        val jsonRequest =
            object : JsonArrayRequest(urlJSON, Response.Listener<JSONArray> { response ->
                Toast.makeText(requireContext(), "Estudiante eliminado", Toast.LENGTH_SHORT).show()
                activity?.onBackPressed()
            }, Response.ErrorListener { error ->
                Toast.makeText(requireContext(), "No se encontró el alumno", Toast.LENGTH_SHORT).show()
            }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["User-Agent"] =
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36"
                    return headers
                }

                override fun getMethod(): Int {
                    return Method.DELETE
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