package dev.bonch.kodik.models

import android.media.ThumbnailUtils
import androidx.cardview.widget.CardView

data class Course(
    val courseId: Long,
    val courseTitle: String
) {
    class CoursesController {
        val coursesList: MutableList<Course> = mutableListOf()

        init {
            coursesList.add(Course(0, "HTML"))
            coursesList.add(Course(1, "CSS"))
            coursesList.add(Course(2, "JAVA"))
            coursesList.add(Course(3, "KOTLIN"))
            coursesList.add(Course(4, "PYTHON"))
            coursesList.add(Course(5, "PASCAL"))
        }
    }
    class MineCoursesController {
        val myCoursesList: MutableList<Course> = mutableListOf()

        init {
            myCoursesList.add(Course(0, "HTML"))
        }
    }
}