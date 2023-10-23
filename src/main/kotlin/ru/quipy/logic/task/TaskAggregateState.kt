package ru.quipy.logic.task

import ru.quipy.api.task.*
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.util.*

class TaskAggregateState : AggregateState<UUID, TaskAggregate> {
    private lateinit var taskId: UUID
    var createdAt: Long = System.currentTimeMillis()
    var updatedAt: Long = System.currentTimeMillis()
    lateinit var projectId: UUID
    lateinit var statusId: UUID
    lateinit var executorId: UUID
    lateinit var taskTitle: String
    var assignees = mutableMapOf<UUID, UserEntityTask>()

    override fun getId() = taskId

    @StateTransitionFunc
    fun taskCreatedApply(event: TaskCreatedEvent) {
        projectId = event.projectId
        taskTitle = event.title
        updatedAt = event.createdAt
        taskId = event.taskId
    }

    @StateTransitionFunc
    fun taskTitleChangedApply(event: TaskTitleChangedEvent) {
        taskTitle = event.title
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun taskStatusAssignedApply(event: StatusAssignedToTaskEvent) {
        statusId = event.statusId
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun taskExecutorAddedApply(event: TaskAddedExecutorEvent) {
        if (assignees.values.any { it.userId == event.executorId }) {
            throw IllegalArgumentException("User already exists: ${event.executorId}")
        }
        assignees[event.executorId] = UserEntityTask(event.executorId)
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun deleteTaskApply(event: TaskRemoved) {
        if (assignees.containsKey(event.executorId)) {
            assignees.remove(event.executorId)
            taskId = event.taskId
            updatedAt = event.createdAt
        }
    }
}

data class UserEntityTask(
        val userId: UUID
)
