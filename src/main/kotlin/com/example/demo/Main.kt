package org.example
import java.time.LocalDate

val toDoList = ToDo()

fun main() {
    while (true) {
        println("                                                     \n" +
                "    ████████████         ██████████                  \n" +
                "    █          █████████ █          █ ████████       \n" +
                "    ████    ████        ██    ██    ██        ██     \n" +
                "       █    █ █    ██    █    ██    █    ██    █     \n" +
                "       █    █  █       ███         ███       ███     \n" +
                "       ██████   ████████ ███████████  ████████       \n" +
                "                                                     ")
        println("Что вы хотите сделать?")
        println("1. Посмотреть список всех задач")
        println("2. Найти задачу по описанию")
        println("3. Добавить задачу")
        println("4. Изменить существующую задачу")
        println("5. Удалить задачу")
        println("6. Добавить подзадачу")
        println("7. Выйти\n")
        print("Введите цифру нужного пункта от 1 до 7: ")

        when (val input = readLine()?.toIntOrNull()) {
            1 -> {
                println("\n\n===== Все задачи =====")
                toDoList.listOutPut().forEach { println(it) }
            }
            2 -> {
                println("\n\nВведите описание задачи:")
                val keyword = readLine() ?: ""
                val foundTask = toDoList.getTaskByDescription(keyword)
                if (foundTask != null) {
                    println("===== Результаты поиска =====")
                    println(foundTask)
                } else {
                    println("Задачи не найдены.")
                }
                println("\n\n")
            }
            3 -> {
                println("\n\nВведите описание задачи:")
                val description = readLine() ?: ""
                println("Введите статус задачи (ACTIVE или DONE):")
                val statusInput = readLine() ?: ""
                val status = if (statusInput.equals("ACTIVE", ignoreCase = true)) Status.ACTIVE else Status.DONE
                println("Введите срок выполнения задачи в формате (ГГГГ-ММ-ДД):")
                val dueDateInput = readLine() ?: ""
                val dueDate = LocalDate.parse(dueDateInput)
                println("Введите дополнительную информацию:")
                val additionalInfo = readLine() ?: "\n"

                val task = ToDoItem(description, status, dueDate, additionalInfo)
                toDoList.addTask(task)
                println("\n\n")
            }
            4 -> {
                println("\n\nВведите номер задачи для изменения:")
                val taskId = readLine()?.toIntOrNull() ?: -1
                if (taskId != -1) {
                    val task = toDoList.getTaskById(taskId)
                    if (task != null) {
                        println("Введите новое описание:")
                        val newDescription = readLine() ?: ""
                        println("Введите новый статус (ACTIVE или DONE):")
                        val newStatusInput = readLine() ?: ""
                        val newStatus = if (newStatusInput.equals("ACTIVE", ignoreCase = true)) Status.ACTIVE else Status.DONE
                        println("Введите новый срок выполнения в формате (ГГГГ-MM-ДД):")
                        val newDueDateInput = readLine() ?: ""
                        val newDueDate = LocalDate.parse(newDueDateInput)
                        println("Введите новую дополнительную информацию:")
                        val newAdditionalInfo = readLine() ?: ""

                        toDoList.updateTask(taskId, newDescription, newStatus, newDueDate, newAdditionalInfo)
                        println("Задача успешно изменена.")
                    } else {
                        println("Задача под номером $taskId не найдена.")
                    }
                } else {
                    println("Неверный формат номера задачи.")
                }
                println("\n\n")
            }
            5 -> {
                println("\n\nВведите номер задачи для удаления:")
                val taskId = readLine()?.toIntOrNull() ?: -1
                if (taskId != -1) {
                    if (toDoList.removeTaskById(taskId)) {
                        println("Задача успешно удалена.")
                    } else {
                        println("Задача с номером $taskId не найдена.")
                    }
                } else {
                    println("Неверный формат номера задачи.")
                }
                println("\n\n")
            }
            6 -> {
                println("\n\nВведите номер основной задачи, к которой хотите добавить подзадачу:")
                val mainTaskId = readLine()?.toIntOrNull() ?: -1
                if (mainTaskId != -1) {
                    val mainTask = toDoList.getTaskById(mainTaskId)
                    if (mainTask != null) {
                        println("Введите описание подзадачи:")
                        val subtaskDescription = readLine() ?: ""
                        println("Введите статус подзадачи (ACTIVE или DONE):")
                        val subtaskStatusInput = readLine() ?: ""
                        val subtaskStatus = if (subtaskStatusInput.equals("ACTIVE", ignoreCase = true)) Status.ACTIVE else Status.DONE
                        println("Введите срок выполнения подзадачи в формате (ГГГГ-ММ-ДД):")
                        val subtaskDueDateInput = readLine() ?: ""
                        val subtaskDueDate = LocalDate.parse(subtaskDueDateInput)
                        println("Введите дополнительную информацию для подзадачи:")
                        val subtaskAdditionalInfo = readLine() ?: ""

                        val subtask = ToDoItem(subtaskDescription, subtaskStatus, subtaskDueDate, subtaskAdditionalInfo)
                        mainTask.addSubtask(subtask)
                        println("Подзадача успешно добавлена к основной задаче с номером $mainTaskId.")
                    } else {
                        println("Основная задача с номером $mainTaskId не найдена.")
                    }
                } else {
                    println("Неверный формат номера задачи.")
                }
                println("\n\n")
            }
            7 -> {
                println("Вы вышли из Лабы 4 по вебу")
                return
                println("\n\n")
            }

            else -> {
                println("\n" +
                        "\n" +
                        "Неверный формат. Введите номер пункта от 1 до 7.")
            }
        }
    }
}