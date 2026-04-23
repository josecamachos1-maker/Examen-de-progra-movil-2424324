package com.ucb.food.work

import AppDatabase
import AppEventEntity
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ucb.food.portafolio.data.datasource.FirebaseManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EventWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params), KoinComponent {

    private val db: AppDatabase by inject()
    private val firebaseManager: FirebaseManager by inject()

    override suspend fun doWork(): Result {
        val eventType = inputData.getString("EVENT_TYPE") ?: return Result.failure()
        val timestamp = System.currentTimeMillis()

        db.getEventDao().insert(
            AppEventEntity(timestamp = timestamp, eventType = eventType)
        )

        try {
            val path = "user_activity_logs/$timestamp"
            val value = "Event: $eventType at $timestamp"
            firebaseManager.saveData(path, value)
        } catch (e: Exception) {
        }

        return Result.success()
    }
}
