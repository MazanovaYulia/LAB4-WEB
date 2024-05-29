package org.example

import java.time.LocalDate

class ToDo {
    private val toDoList = mutableListOf<ToDoItem>()

    fun addTask(task: ToDoItem) {
        toDoList.add(task)
    }

    fun listOutPut(): List<ToDoItem> = toDoList
    fun getTaskByDescription(description: String): ToDoItem? {
        return toDoList.find { it.description == description || it.subtasks.any { subtask -> subtask.description == description } }
    }

    fun updateTask(id: Int, newDescription: String, newStatus: Status, newDate: LocalDate, newAdditionalInfo: String): Boolean {
        val task = toDoList.find { it.id == id }
        if (task != null) {
            task.description = newDescription
            task.status = newStatus
            task.date = newDate
            task.additionalInfo = newAdditionalInfo
            return true
        } else {
            // Search in subtasks
            for (item in toDoList) {
                val subtask = item.subtasks.find { it.id == id }
                if (subtask != null) {
                    subtask.description = newDescription
                    subtask.status = newStatus
                    subtask.date = newDate
                    subtask.additionalInfo = newAdditionalInfo
                    return true
                }
            }
        }
        return false
    }

    fun removeTaskById(id: Int): Boolean {
        val task = toDoList.find { it.id == id }
        if (task != null) {
            toDoList.remove(task)
            return true
        } else {
            // Search in subtasks
            for (item in toDoList) {
                val subtask = item.subtasks.find { it.id == id }
                if (subtask != null) {
                    item.subtasks.remove(subtask)
                    return true
                }
            }
        }
        return false
    }

    fun getTaskById(id: Int): ToDoItem? {
        val task = toDoList.find { it.id == id }
        if (task != null) {
            return task
        } else {
            // Search in subtasks
            for (item in toDoList) {
                val subtask = item.subtasks.find { it.id == id }
                if (subtask != null) {
                    return subtask
                }
            }
        }
        return null
    }
}