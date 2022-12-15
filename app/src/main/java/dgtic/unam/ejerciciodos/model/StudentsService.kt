package dgtic.unam.ejerciciodos.model

import android.content.Context
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import org.json.JSONArray
import org.json.JSONObject

class StudentsService(private val context: Context) {
    private var volleyAPI: VolleyAPI = VolleyAPI(context)
    private val ipPuerto = "192.168.1.116:8080"

    fun addStudent(student: Student) {
        val urlJSON = "http://$ipPuerto/agregarestudiante"
        var cadena = ""
        val jsonRequest =
            object : JsonArrayRequest(urlJSON, Response.Listener<JSONArray> { response ->
                (0 until response.length()).forEach {
                    val estudiante = response.getJSONObject(it)
                    val materia = estudiante.getJSONArray("materias")
                    cadena += estudiante.get("cuenta").toString() + "<"
                    (0 until materia.length()).forEach {
                        val datos = materia.getJSONObject(it)
                        cadena += datos.get("nombre").toString() + "**" + datos.get("creditos")
                            .toString() + "---"
                    }
                    cadena += "> \n"
                }
                println("RESPONSE ->>> $cadena")
            }, Response.ErrorListener { error ->
                println("ERROR ->>> $error")
            }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["User-Agent"] =
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36"
                    return headers
                }

                override fun getBody(): ByteArray {
                    val estudiante = JSONObject()
                    estudiante.put("cuenta", student.account)
                    estudiante.put("nombre", student.name)
                    estudiante.put("edad", student.age)
                    val materias = JSONArray()
                    student.subjects.forEach {
                        val itemMaterias = JSONObject()
                        itemMaterias.put("id", it.id)
                        itemMaterias.put("nombre", it.name)
                        itemMaterias.put("creditos", it.credits)
                        materias.put(itemMaterias)
                        estudiante.put("materias", materias)
                    }
                    return estudiante.toString().toByteArray(Charsets.UTF_8)
                }

                override fun getMethod(): Int {
                    return Method.POST
                }
            }
        volleyAPI.add(jsonRequest)
    }


}