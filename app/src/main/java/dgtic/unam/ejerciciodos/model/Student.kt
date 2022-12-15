package dgtic.unam.ejerciciodos.model

data class Student(var account: String, var name: String, var age: Int, var subjects: List<Subject>)

data class Subject(var id: Int, var name: String, var credits: Int)
