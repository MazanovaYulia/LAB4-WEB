package com.example.demo

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.example.ToDo
import org.example.ToDoItem
import org.example.Status
import java.time.LocalDate

val toDoList = ToDo()

@RestController
class DemoController {

    @GetMapping("/home")
    fun home(): String? {
        return "HOME"
    }

    @GetMapping("/tasks")
    fun getTasks(): ResponseEntity<Any> {
        return ResponseEntity.ok(toDoList.listOutPut())
    }

    @PostMapping("/addTask")
    fun addTask(@RequestBody taskRequest: TaskRequest): ResponseEntity<Any> {
        val status = if (taskRequest.status.equals("ACTIVE", ignoreCase = true)) Status.ACTIVE else Status.DONE
        val dueDate = LocalDate.parse(taskRequest.date)

        val task = ToDoItem(taskRequest.description, status, dueDate, taskRequest.additionalInfo)
        toDoList.addTask(task)

        return ResponseEntity.ok(task)
    }

    @PutMapping("/updateTask")
    fun updateTask(@RequestBody taskRequest: UpdateTaskRequest): ResponseEntity<Any> {
        val taskId = taskRequest.id.toIntOrNull() ?: -1
        if (taskId != -1) {
            val task = toDoList.getTaskById(taskId)
            if (task != null) {
                val newStatus = if (taskRequest.status.equals("ACTIVE", ignoreCase = true)) Status.ACTIVE else Status.DONE
                val newDueDate = LocalDate.parse(taskRequest.date)

                toDoList.updateTask(taskId, taskRequest.description, newStatus, newDueDate, taskRequest.additionalInfo)
                return ResponseEntity.ok(task)
            } else {
                return ResponseEntity.notFound().build()
            }
        } else {
            return ResponseEntity.badRequest().body("Неправильный формат id")
        }
    }

    @PostMapping("/subtask")
    fun addSubtask(@RequestBody subtaskRequest: SubtaskRequest): ResponseEntity<Any> {
        val mainTaskId = subtaskRequest.id.toIntOrNull() ?: -1
        if (mainTaskId != -1) {
            val mainTask = toDoList.getTaskById(mainTaskId)
            if (mainTask != null) {
                val subtaskStatus = if (subtaskRequest.status.equals("ACTIVE", ignoreCase = true)) Status.ACTIVE else Status.DONE
                val subtaskDueDate = LocalDate.parse(subtaskRequest.date)

                val subtask = ToDoItem(subtaskRequest.description, subtaskStatus, subtaskDueDate, subtaskRequest.additionalInfo)
                mainTask.addSubtask(subtask)
                return ResponseEntity.ok(mainTask)
            } else {
                return ResponseEntity.notFound().build()
            }
        } else {
            return ResponseEntity.badRequest().body("Неправильный формат id")
        }
    }

    @DeleteMapping("/deleteTask")
    fun deleteTask(@RequestBody idRequest: IdRequest): ResponseEntity<Any> {
        val taskId = idRequest.id.toIntOrNull() ?: -1
        if (taskId != -1) {
            if (toDoList.removeTaskById(taskId)) {
                return ResponseEntity.ok().build()
            } else {
                return ResponseEntity.notFound().build()
            }
        } else {
            return ResponseEntity.badRequest().body("Неправильный формат id")
        }
    }

    @GetMapping("/searchTaskByDescription")
    fun searchTaskByDescription(@RequestBody descriptionRequest: DescriptionRequest): ResponseEntity<Any> {
        val foundTask = toDoList.getTaskByDescription(descriptionRequest.description)
        if (foundTask != null) {
            return ResponseEntity.ok(foundTask)
        }

        return ResponseEntity.notFound().build()
    }
}

data class TaskRequest(
    val description: String,
    val status: String,
    val date: String,
    val additionalInfo: String
)

data class UpdateTaskRequest(
    val id: String,
    val description: String,
    val status: String,
    val date: String,
    val additionalInfo: String
)

data class SubtaskRequest(
    val id: String,
    val description: String,
    val status: String,
    val date: String,
    val additionalInfo: String
)

data class IdRequest(
    val id: String
)

data class DescriptionRequest(
    val description: String
)
