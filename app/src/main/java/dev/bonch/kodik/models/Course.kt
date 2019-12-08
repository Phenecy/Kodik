package dev.bonch.kodik.models

import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.toObject

data class Course(
    var courseId: Int,
    var courseTitle: String,
    var courseContext: MutableMap<Int, String>? = hashMapOf(),
    var courseText: MutableList<String>? = arrayListOf()
) {
    class CoursesController {
        var coursesList: MutableList<Course> = mutableListOf()
        private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        private val coursesRef = db.collection("courses").orderBy("courseId")
        private val source = Source.CACHE

        init {
            coursesRef.get(source)
                .addOnSuccessListener { result ->
                    for (document in result) {
                        coursesList.add(
                            Course(
                                document["courseId"].toString().toInt(),
                                document["courseTitle"].toString(),
                                document["courseContext"] as MutableMap<Int, String>,
                                document["courseText"] as MutableList<String>
                            )
                        )
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("Import Error", "Error getting documents: ", exception)
                }
        }
    }


    class MineCoursesController {
        val myCoursesList: MutableList<Course> = mutableListOf()

        init {
            myCoursesList.add(Course(0, "HTML", null, null))
        }
    }
}