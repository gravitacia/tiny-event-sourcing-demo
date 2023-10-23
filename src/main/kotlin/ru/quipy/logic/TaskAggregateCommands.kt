package ru.quipy.logic

import ru.quipy.api.task.*
import ru.quipy.api.task.TaskCreatedEvent
import java.util.*


fun TaskAggregateState.create(id: UUID, title: String, creatorId: String, projectId: UUID): TaskCreatedEvent {
    return TaskCreatedEvent(
            taskId = id,
            title = title,
            projectId = projectId
    )
}

fun TaskAggregateState.changeTitle(id: UUID, title: String): TaskTitleChangedEvent {
    return TaskTitleChangedEvent(
            taskId = id,
            title = title
    )
}

fun TaskAggregateState.assignStatus(id: UUID, statusId: UUID): StatusAssignedToTaskEvent {
    return StatusAssignedToTaskEvent(
            taskId = id,
            statusId = statusId,
    )
}

fun TaskAggregateState.addExecutor(id: UUID): TaskAddedExecutorEvent {
    if (assignees.values.any { it.userId == id }) {
        throw IllegalArgumentException("User already exists: $id")
    }
    return TaskAddedExecutorEvent(taskId = this.getId(), executorId = id)
}

fun TaskAggregateState.removeExecutor(id: UUID): TaskRemovedExecutor {
    if (!assignees.values.any { it.userId == id }) {
        throw IllegalArgumentException("User doesn't exists: $id")
    }

    return TaskRemovedExecutor(taskId = this.getId(), executorId = id)
}