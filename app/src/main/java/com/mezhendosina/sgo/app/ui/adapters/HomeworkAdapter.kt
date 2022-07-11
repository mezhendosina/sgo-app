package com.mezhendosina.sgo.app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mezhendosina.sgo.app.databinding.ItemHomeworkBinding
import com.mezhendosina.sgo.data.layouts.attachments.AttachmentsResponseItem
import com.mezhendosina.sgo.data.layouts.diary.diary.Lesson
import com.mezhendosina.sgo.data.layouts.diary.diary.Mark

typealias OnHomeworkClickListener = (Lesson) -> Unit

class HomeworkAdapter(private val onHomeworkClickListener: OnHomeworkClickListener) :
    RecyclerView.Adapter<HomeworkAdapter.HomeworkViewHolder>(), View.OnClickListener {

    var lessons: List<Lesson> = emptyList()
        set(newValue) {
            field = newValue.sortedBy { it.number }
            notifyDataSetChanged()
        }
    var attachments: List<AttachmentsResponseItem> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }
    private val viewPool = RecyclerView.RecycledViewPool()

    class HomeworkViewHolder(val binding: ItemHomeworkBinding) :
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
        val binding = ItemHomeworkBinding.inflate(inflater, parent, false)

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
                var withAttachments = false
                var withHomework = false
                for (i in lesson.assignments) {
                    for (b in attachments) {
                        if (i.id == b.assignmentId) {
                            withAttachments = true
                        }
                    }
                    if (i.typeId == 3) {
                        homework.text = i.assignmentName
                        withHomework = true
                    }
                }

                if (withAttachments) {
                    attachmentsIcon.visibility = View.VISIBLE
                } else {
                    attachmentsIcon.visibility = View.INVISIBLE
                }
                if (withHomework) {
                    homework.visibility = View.VISIBLE
                } else {
                    homework.visibility = View.INVISIBLE
                }

                lesson.assignments.forEach { assign ->
                    grade += if (assign.mark != null) {
                        "${assign.mark.mark}  "
                    } else {
                        ""
                    }
                }

                val layoutManager =
                    LinearLayoutManager(
                        holder.itemView.context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                val homeworkAdapter = HomeworkGradeAdapter()
                val gradesList = mutableListOf<Mark>()

                lesson.assignments.forEach {
                    if (it.mark != null) {
                        gradesList.add(it.mark)
                    }
                }
                homeworkAdapter.grades = gradesList

                grades.apply {
                    adapter = homeworkAdapter
                    this.layoutManager = layoutManager
                    setRecycledViewPool(viewPool)
                }
            } else {
                grades.visibility = View.GONE
                homework.visibility = View.GONE
                attachmentsIcon.visibility = View.INVISIBLE
                this.root.isClickable = false
            }
        }
    }

    override fun getItemCount(): Int = lessons.size
}
