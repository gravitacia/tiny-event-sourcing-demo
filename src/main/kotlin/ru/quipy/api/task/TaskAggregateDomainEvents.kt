package ru.quipy.api.task

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.*

const val TASK_CREATED_EVENT = "TASK_CREATED_EVENT"
const val STATUS_ASSIGNED_TO_TASK_EVENT = "STATUS_ASSIGNED_TO_TASK_EVENT"
const val TASK_NAME_CHANGED_EVENT = "TASK_NAME_CHANGED_EVENT"
const val USER_ASSIGNED_TO_TASK_EVENT = "USER_ASSIGNED_TO_TASK_EVENT"
const val TASK_REMOVED_EVENT = "TASK_REMOVED_EVENT"

// API
@DomainEvent(name = ru.quipy.api.project.TASK_CREATED_EVENT)
class TaskCreatedEvent(
        val taskId: UUID,
        val projectId: UUID,
        val title: String,
        createdAt: Long = System.currentTimeMillis(),
) : Event<TaskAggregate>(
        name = ru.quipy.api.project.TASK_CREATED_EVENT,
        createdAt = createdAt,
)

@DomainEvent(name = STATUS_ASSIGNED_TO_TASK_EVENT)
class StatusAssignedToTaskEvent(
        val taskId: UUID,
        val statusId: UUID,
        createdAt: Long = System.currentTimeMillis(),
) : Event<TaskAggregate>(
        name = STATUS_ASSIGNED_TO_TASK_EVENT,
        createdAt = createdAt
)

@DomainEvent(name = TASK_NAME_CHANGED_EVENT)
class TaskTitleChangedEvent(
        val taskId: UUID,
        val title: String,
        createdAt: Long = System.currentTimeMillis(),
) : Event<TaskAggregate>(
        name = TASK_NAME_CHANGED_EVENT,
        createdAt = createdAt,
)

@DomainEvent(name = USER_ASSIGNED_TO_TASK_EVENT)
class TaskAddedExecutorEvent(
        val taskId: UUID,
        val executorId: UUID,
        createdAt: Long = System.currentTimeMillis(),
) : Event<TaskAggregate>(
        name = USER_ASSIGNED_TO_TASK_EVENT,
        createdAt = createdAt,
)

@DomainEvent(name = TASK_REMOVED_EVENT)
class TaskRemoved(
        val taskId: UUID,
        val projectId: UUID,
        createdAt: Long = System.currentTimeMillis(),
) : Event<TaskAggregate>(
        name = TASK_REMOVED_EVENT,
        createdAt = createdAt,
)
