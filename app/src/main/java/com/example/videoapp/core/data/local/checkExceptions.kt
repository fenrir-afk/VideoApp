package com.example.videoapp.core.data.local

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDiskIOException
import com.example.videoapp.core.domain.util.Result
import com.example.videoapp.core.domain.util.DataBaseError
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext


suspend inline fun <reified T> checkExceptions(
    execute: () -> T
): Result<T, DataBaseError> {
    val result = try {
        execute()
    } catch (e: SQLiteConstraintException) {
        e.printStackTrace()
        return Result.Error(DataBaseError.CONSTRAINT_VIOLATION)
    } catch (e: SQLiteDiskIOException) {
        e.printStackTrace()
        return Result.Error(DataBaseError.DISK_IO_ERROR)
    } catch (e: Exception) {
        e.printStackTrace()
        coroutineContext.ensureActive()
        return Result.Error(DataBaseError.UNKNOWN_DB_ERROR)
    }
    return Result.Success(result)
}
