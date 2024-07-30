package com.jamesbrookssoft.iviewscheduler

import com.jamesbrookssoft.iviewscheduler.business.AvailabilityService
import com.jamesbrookssoft.iviewscheduler.business.model.TimeSlot
import com.jamesbrookssoft.iviewscheduler.business.model.UserType
import com.jamesbrookssoft.iviewscheduler.persistence.entity.UserEntity
import com.jamesbrookssoft.iviewscheduler.persistence.repository.AppointmentDayEntityRepository
import com.jamesbrookssoft.iviewscheduler.persistence.repository.UserRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalTime
import java.time.Month
import javax.annotation.PostConstruct

@SpringBootApplication
@Configuration
class IviewSchedulerApplication(val userRepository: UserRepository,
                                val appointmentDayEntityRepository: AppointmentDayEntityRepository,
                                val availabilityService: AvailabilityService) {


    @PostConstruct
    @Transactional
    fun setUpDemo() {
        userRepository.deleteAll()
        appointmentDayEntityRepository.deleteAll()

        val candidate = userRepository.save(UserEntity(id = "cand1",firstName = "Candidate", lastName = "One", type = UserType.CANDIDATE))
        val interviewer1 = userRepository.save(UserEntity(id = "iview1", firstName = "Interviewer", lastName = "One", type = UserType.INTERVIEWER))
        val interviewer2 = userRepository.save(UserEntity(id = "iview2", firstName = "Interviewer", lastName = "Two", type = UserType.INTERVIEWER))

        println("Newly generated users: " + listOf(candidate, interviewer1, interviewer2))

        /* Add the appointments of interviewer one */

        val monday = LocalDate.of(2018, Month.MAY, 20)

        availabilityService.addNewAppointment(interviewer1.id!!, listOf(
                TimeSlot(monday, LocalTime.of(9, 0), LocalTime.of(16, 0)),
                TimeSlot(monday.plusDays(1), LocalTime.of(9, 0), LocalTime.of(16, 0)),
                TimeSlot(monday.plusDays(2), LocalTime.of(9, 0), LocalTime.of(16, 0)),
                TimeSlot(monday.plusDays(3), LocalTime.of(9, 0), LocalTime.of(16, 0)),
                TimeSlot(monday.plusDays(4), LocalTime.of(9, 0), LocalTime.of(16, 0))))

        availabilityService.addNewAppointment(interviewer2.id!!, listOf(
                TimeSlot(monday, LocalTime.of(12, 0), LocalTime.of(18, 0)),
                TimeSlot(monday.plusDays(1), LocalTime.of(9, 0), LocalTime.of(12, 0)),
                TimeSlot(monday.plusDays(2), LocalTime.of(12, 0), LocalTime.of(18, 0)),
                TimeSlot(monday.plusDays(3), LocalTime.of(9, 0), LocalTime.of(12, 0))))

        availabilityService.addNewAppointment(candidate.id!!, listOf(
                TimeSlot(monday, LocalTime.of(9, 0), LocalTime.of(10, 0)),
                TimeSlot(monday.plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0)),
                TimeSlot(monday.plusDays(2), LocalTime.of(9, 0), LocalTime.of(10, 0)),
                TimeSlot(monday.plusDays(2), LocalTime.of(10, 0), LocalTime.of(12, 0)),
                TimeSlot(monday.plusDays(3), LocalTime.of(9, 0), LocalTime.of(10, 0)),
                TimeSlot(monday.plusDays(4), LocalTime.of(9, 0), LocalTime.of(10, 0))))

    }
}

fun main(args: Array<String>) {
    runApplication<IviewSchedulerApplication>(*args)
}
