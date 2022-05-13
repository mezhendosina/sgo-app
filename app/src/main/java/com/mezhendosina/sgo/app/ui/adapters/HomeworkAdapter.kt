package com.mezhendosina.sgo.app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mezhendosina.sgo.app.databinding.HomeworkItemBinding
import com.mezhendosina.sgo.data.attachments.AttachmentsResponseItem
import com.mezhendosina.sgo.data.diary.diary.Lesson

typealias OnHomeworkClickListener = (Lesson) -> Unit

class HomeworkAdapter(private val onHomeworkClickListener: OnHomeworkClickListener) :
    RecyclerView.Adapter<HomeworkAdapter.HomeworkViewHolder>(), View.OnClickListener {

    var lessons: List<Lesson> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }
    var attachments: List<AttachmentsResponseItem> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class HomeworkViewHolder(val binding: HomeworkItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onClick(v: View) {
        val lesson = v.tag as Lesson
        onHomeworkClickListener(lesson)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeworkViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = HomeworkItemBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)

        return HomeworkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeworkViewHolder, position: Int) {
        val lesson = lessons[position]
        with(holder.binding) {
            holder.itemView.tag = lesson

            lessonName.text = lesson.subjectName
            lessonNumber.text = lesson.number.toString()
            lessonTime.text = "${lesson.startTime}-${lesson.endTime}"

            if (!lesson.assignments.isNullOrEmpty()) {
                var grade = ""

                lesson.assignments.forEach { assign ->
                    grade += if (assign.mark != null) {
                        "${assign.mark.mark}  "
                    } else {
                        ""
                    }
                    if (assign.typeId == 3 && assign.assignmentName.isNotEmpty()) {
                        homework.text = assign.assignmentName
                        homework.visibility = View.VISIBLE
                    } else {
                        homework.text = ""
                        homework.visibility = View.GONE
                    }
                }

                grades.text = grade
            } else {
                grades.visibility = View.GONE
                homework.visibility = View.GONE
                attachmentsIcon.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int = lessons.size
}
