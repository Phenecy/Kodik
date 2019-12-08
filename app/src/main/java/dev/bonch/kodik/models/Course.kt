package dev.bonch.kodik.models

import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.toObject
import dev.bonch.kodik.activities.MainActivity
import dev.bonch.kodik.fragments.HomeFragment
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Course(
    var courseId: Int,
    var courseTitle: String,
    var courseClasses: MutableList<String>? = arrayListOf(),
    var courseClassesCheck: MutableList<Boolean>? = arrayListOf(),
    var courseDescription: String?
):Parcelable  {
    class CoursesController {
        var coursesList: MutableList<Course> = mutableListOf()
        private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        private val coursesRef = db.collection("courses").orderBy("courseId")
        private val source = Source.DEFAULT

        init {
            coursesRef
                .get(source)
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d("Getting Data", "Success")
                        coursesList.add(
                            Course(
                                document["courseId"].toString().toInt(),
                                document["courseTitle"].toString(),
                                document["courseText"] as MutableList<String>?,
                                document["courseClassesCheck"] as MutableList<Boolean>?,
                                document["courseDescription"].toString()
                            )
                        )
                    }
                    HomeFragment.test.dataGot()
                }
                .addOnFailureListener { exception ->
                    Log.d("Import Error", "Error getting documents: ", exception)
                }
        }
    }


    class MineCoursesController {
        val myCoursesList: MutableList<Course> = mutableListOf()

//        init {
//            myCoursesList.add(Course(0, "HTML", null, null, null))
//        }
    }
}