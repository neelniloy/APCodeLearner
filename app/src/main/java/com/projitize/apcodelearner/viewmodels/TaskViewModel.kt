package com.braineer.scheduler.viewmodels

import androidx.lifecycle.ViewModel

class TaskViewModel:ViewModel() {

    val taskRepository = TaskRepository()

    fun insertNewTask(task: Task, callback: (String) -> Unit) = taskRepository.insertNewTask(task,callback)
    fun getTaskByDate(userId:String, date:Long) = taskRepository.getTaskByDate(userId,date)
    fun removeTaskById(taskId: String,callback: (String) -> Unit) = taskRepository.removeTaskById(taskId,callback)
    fun updateTaskStatus(taskId:String,status:String, callback: (String) -> Unit) = taskRepository.updateTaskStatus(taskId,status,callback)
    fun getAllTaskDate(userId:String) = taskRepository.getAllTaskDate(userId)

}