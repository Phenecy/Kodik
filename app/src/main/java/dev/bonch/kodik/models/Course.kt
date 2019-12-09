package dev.bonch.kodik.models

import android.os.Parcelable
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import dev.bonch.kodik.fragments.HomeFragment
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Course(
    var courseId: Int,
    var courseTitle: String,
    var courseClasses: MutableList<String>? = arrayListOf(),
    var courseClassesCheck: MutableList<Boolean>? = arrayListOf(),
    var courseDescription: String?,
    var courseLesson: MutableList<Lesson>? = arrayListOf(),
    var courseFbName: String?
):Parcelable  {
    class CoursesController {
        var coursesList: MutableList<Course> = mutableListOf()
        var lessonsList: MutableList<Lesson> = mutableListOf()
        private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        private val coursesRef = db.collection("courses").orderBy("courseId")
        private val source = Source.DEFAULT

        init {
            coursesRef
                .get(source)
                .addOnSuccessListener { result ->
                    for (document in result) {
                        var lessonRef = db.collection("courses").document(document["courseName"].toString()).collection("courseLessons")
                        lessonRef
                            .get(source)
                            .addOnSuccessListener { it ->
                                it.forEach {
                                    lessonsList.add(
                                        Lesson(
                                            it["type"].toString(),
                                            it["title"].toString(),
                                            it["text"].toString(),
                                            it["answerChoice"] as MutableList<String>?,
                                            it["rightAnswer"]?.toString()?.toInt()
                                        )
                                    )
                                }
                            }
                        coursesList.add(
                            Course(
                                document["courseId"].toString().toInt(),
                                document["courseTitle"].toString(),
                                document["courseText"] as MutableList<String>?,
                                document["courseClassesCheck"] as MutableList<Boolean>?,
                                document["courseDescription"].toString(),
                                lessonsList,
                                document["courseName"].toString()
                            )
                        )
                        lessonsList.clear()
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