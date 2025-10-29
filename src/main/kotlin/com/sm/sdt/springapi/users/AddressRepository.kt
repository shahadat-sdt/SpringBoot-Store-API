package com.sm.sdt.springapi.users

import org.springframework.data.repository.CrudRepository


interface AddressRepository : CrudRepository<Address, Long> {
}