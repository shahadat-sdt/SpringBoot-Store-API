package com.sm.sdt.springapi.repository

import com.sm.sdt.springapi.entities.Profile
import org.springframework.data.repository.CrudRepository

interface ProfileRepository : CrudRepository<Profile, Long> {


}