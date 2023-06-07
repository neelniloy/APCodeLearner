package com.braineer.scheduler.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RoutineViewModel:ViewModel() {

    val goToDayLD: MutableLiveData<String> = MutableLiveData()

    val routineRepository = RoutineRepository()

    fun insertNewRoutine(routine: Routine,callback: (String) -> Unit) = routineRepository.insertNewRoutine(routine,callback)
    fun updateRoutine(routineKey: String, day: String, routineId:String, routine: Routine,callback: (String) -> Unit) = routineRepository.updateRoutine(routineKey,day,routineId,routine,callback)
    fun getRoutineByDay(routineKey:String, day:String) = routineRepository.getRoutineByDay(routineKey,day)
    fun getAllRoutine(routineKey:String) = routineRepository.getAllRoutine(routineKey)
    fun getRoutineKey() = routineRepository.getRoutineKey()
    fun goToDay() = goToDayLD
    fun insertAutoText(count:String,doc: String,value: String,callback: (String) -> Unit) = routineRepository.insertAutoText(count,doc,value,callback)
    fun getAutoText(doc:String,callback: (List<Any>) -> Unit) = routineRepository.getAutoText(doc,callback)
    fun removeRoutineById(day: String,routineId: String,callback: (String) -> Unit) = routineRepository.removeRoutineById(day,routineId,callback)
    fun getRoutineById(routineKey:String, day:String,routineId:String) = routineRepository.getRoutineById(routineKey, day, routineId)
    fun importRoutine(keyFrom: String, keyTo:String,callback: (String) -> Unit) = routineRepository.importRoutine(keyFrom,keyTo,callback)
    fun setImportKey(userId: String,importKey: String,callback: (String) -> Unit) = routineRepository.setImportKey(userId,importKey,callback)
    fun setLastModificationTime(userId: String,callback: (String) -> Unit) = routineRepository.setLastModificationTime(userId,callback)
    fun getLastModificationTime(importKey: String):LiveData<Long> = routineRepository.getLastModificationTime(importKey)


}