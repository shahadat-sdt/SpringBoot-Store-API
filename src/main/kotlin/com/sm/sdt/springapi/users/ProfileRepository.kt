package com.sm.sdt.springapi.users

import org.springframework.data.repository.CrudRepository

interface ProfileRepository : CrudRepository<Profile, Long> {


}