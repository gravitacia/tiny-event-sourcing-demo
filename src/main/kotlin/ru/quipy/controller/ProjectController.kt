package ru.quipy.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.quipy.api.project.ProjectAggregate
import ru.quipy.api.project.ProjectCreatedEvent
import ru.quipy.api.project.StatusCreatedEvent
import ru.quipy.api.project.UserInvitedEvent
import ru.quipy.core.EventSourcingService
import ru.quipy.logic.project.ProjectAggregateState
import ru.quipy.logic.project.create
import ru.quipy.logic.project.createStatus
import ru.quipy.logic.project.inviteUser
import java.util.*

@RestController
@RequestMapping("/projects")
class ProjectController(
        val projectEsService: EventSourcingService<UUID, ProjectAggregate, ProjectAggregateState>
) {

    @PostMapping("/{projectTitle}")
    fun createProject(@PathVariable projectTitle: String, @RequestParam creatorId: String) : ProjectCreatedEvent {
        return projectEsService.create { it.create(UUID.randomUUID(), projectTitle, creatorId) }
    }

    @GetMapping("/{projectId}")
    fun getProject(@PathVariable projectId: UUID) : ProjectAggregateState? {
        return projectEsService.getState(projectId)
    }

    @PostMapping("/status/{projectId}/{statusTitle}")
    fun createStatus(@PathVariable projectId: UUID, @PathVariable statusTitle: String) : StatusCreatedEvent {
        return projectEsService.update(projectId) { it.createStatus(statusTitle) }
    }

    @PostMapping("/invite/{projectId}/{userId}")
    fun inviteUser(@PathVariable projectId: UUID, @PathVariable userId: UUID) : UserInvitedEvent? {
        return projectEsService.update(projectId) {it.inviteUser(userId)}
    }

}