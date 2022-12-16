package dgtic.unam.ejerciciodos.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dgtic.unam.ejerciciodos.R
import dgtic.unam.ejerciciodos.databinding.StudentElementBinding
import dgtic.unam.ejerciciodos.model.Student

class StudentAdapter(private val context: Context, val students: ArrayList<Student>) :
    RecyclerView.Adapter<StudentAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView
        var account: TextView
        var age: TextView
        var subjectName1: TextView
        var subjectName2: TextView
        var subjectName3: TextView
        var subjectCredits1: TextView
        var subjectCredits2: TextView
        var subjectCredits3: TextView
        var binding = StudentElementBinding.bind(view)
        init {
            name = binding.tvStudentName
            account = binding.tvStudentAccount
            age = binding.tvStudentAge
            subjectName1 = binding.tvStudentSubjectName1
            subjectName2 = binding.tvStudentSubjectName2
            subjectName3 = binding.tvStudentSubjectName3
            subjectCredits1 = binding.tvStudentSubjectCredits1
            subjectCredits2 = binding.tvStudentSubjectCredits2
            subjectCredits3 = binding.tvStudentSubjectCredits3
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val li = LayoutInflater.from(parent.context)
        val v = li.inflate(R.layout.student_element, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = students[position]
        holder.name.text = info.name
        holder.account.text = info.account
        holder.age.text = info.age.toString()
        if (info.subjects[0].name != null) {
            holder.subjectName1.text = info.subjects[0].name
            holder.subjectCredits1.text = info.subjects[0].credits.toString()
        }
        if (info.subjects.size > 1) {
            holder.subjectName2.text = info.subjects[1].name
            holder.subjectCredits2.text = info.subjects[1].credits.toString()
        }
        if (info.subjects.size > 2) {
            holder.subjectName3.text = info.subjects[2].name
            holder.subjectCredits3.text = info.subjects[2].credits.toString()
        }
    }

    override fun getItemCount(): Int {
        return students.size
    }
}