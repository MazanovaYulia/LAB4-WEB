package com.example.demo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.example.ToDo
import org.example.ToDoItem
import org.example.Status
import java.time.LocalDate


val toDoList = ToDo()

@RestController
class DemoController {
    @RequestMapping("/home", method = [RequestMethod.GET])
    fun home(): String? {
        return "HOME"
    }

    @RequestMapping("/tasks", method = [RequestMethod.GET])
    fun getTasks(): ResponseEntity<Any> {
        return ResponseEntity.ok(toDoList.listOutPut())
    }

    @RequestMapping("/task", method = [RequestMethod.POST])
    fun addTask(description: String, status: String,  date: String, additionalInfo: String): ResponseEntity<Any> {
        val status = if (status.equals("ACTIVE", ignoreCase = true)) Status.ACTIVE else Status.DONE
        val dueDate = LocalDate.parse(date)

        val task = ToDoItem(description, status, dueDate, additionalInfo)
        toDoList.addTask(task)

        return ResponseEntity.ok(task)
    }

    @RequestMapping("/task", method = [RequestMethod.PUT])
    fun updateTask(id: String, description: String, status: String,  date: String, additionalInfo: String): ResponseEntity<Any> {
        val taskId = id.toIntOrNull() ?: -1
        if (taskId != -1) {
            val task = toDoList.getTaskById(taskId)
            if (task != null) {
                val newStatus = if (status.equals("ACTIVE", ignoreCase = true)) Status.ACTIVE else Status.DONE
                val newDueDate = LocalDate.parse(date)

                toDoList.updateTask(taskId, description, newStatus, newDueDate, additionalInfo)
                return ResponseEntity.ok(task)
            } else {
                return ResponseEntity.notFound().build()
            }
        } else {
            return ResponseEntity.badRequest().body("Неправильный формат id")
        }
    }

    @RequestMapping("/subtask", method = [RequestMethod.POST])
    fun addSubtask(id: String, description: String, status: String,  date: String, additionalInfo: String): ResponseEntity<Any> {
        val mainTaskId = id.toIntOrNull() ?: -1
        if (mainTaskId != -1) {
            val mainTask = toDoList.getTaskById(mainTaskId)
            if (mainTask != null) {
                val subtaskStatus = if (status.equals("ACTIVE", ignoreCase = true)) Status.ACTIVE else Status.DONE
                val subtaskDueDate = LocalDate.parse(date)

                val subtask = ToDoItem(description, subtaskStatus, subtaskDueDate, additionalInfo)
                mainTask.addSubtask(subtask)
                return ResponseEntity.ok(mainTask)
            } else {
                return ResponseEntity.notFound().build()
            }
        } else {
            return ResponseEntity.badRequest().body("Неправильный формат id")
        }
    }




    @RequestMapping("/task", method = [RequestMethod.DELETE])
    fun deleteTask(id: String): ResponseEntity<Any> {
        val taskId = id.toIntOrNull() ?: -1
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

    @RequestMapping("/searchTaskByDescription", method = [RequestMethod.GET])
    fun searchTaskByDescription(description: String): ResponseEntity<Any> {

        val foundTask = toDoList.getTaskByDescription(description)
        if (foundTask != null) {
            return ResponseEntity.ok(foundTask)
        }

        return ResponseEntity.notFound().build()
    }
}