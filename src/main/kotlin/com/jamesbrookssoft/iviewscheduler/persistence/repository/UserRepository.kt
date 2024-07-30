package com.jamesbrookssoft.iviewscheduler.persistence.repository

import com.jamesbrookssoft.iviewscheduler.persistence.entity.UserEntity
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository: MongoRepository<UserEntity, String>
