package com.sm.sdt.springapi.repository

import com.sm.sdt.springapi.entities.Address
import org.springframework.data.repository.CrudRepository


interface AddressRepository : CrudRepository<Address, Long> {
}