package org.example

import java.time.LocalDate

class ToDoItem(
    var description: String,
    var status: Status,
    var date: LocalDate,
    var additionalInfo: String
) {
    companion object {
        private var taskIdCounter = 1
    }

    val id: Int = taskIdCounter++
    val subtasks: MutableList<ToDoItem> = mutableListOf()

    fun addSubtask(subtask: ToDoItem) {
        subtasks.add(subtask)
    }

    override fun toString(): String {
        val subtasksString = if (subtasks.isNotEmpty()) {
            val subtasksDetails = subtasks.joinToString("\n") { subtask ->
            """
            Номер задачи: ${subtask.id}
            Описание: ${subtask.description}
            Статус: ${subtask.status}
            Срок выполнения: ${subtask.date}
            Дополнительная информация: ${subtask.additionalInfo}
            """
            }
            "  \n  Вложенные задачи:\n$subtasksDetails"
        } else {
            "  \n  Нет вложенных задач"
        }
        return """
        |
        |Номер задачи: $id
        |Описание: $description
        |Статус: $status
        |Срок выполнения: $date
        |Дополнительная информация: $additionalInfo
        $subtasksString
        """.trimMargin()
    }
}

enum class Status{
    DONE, ACTIVE
}