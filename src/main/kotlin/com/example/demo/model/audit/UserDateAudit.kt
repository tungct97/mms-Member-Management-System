package com.example.demo.model.audit

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
@JsonIgnoreProperties(value = ["createdBY", "updatedBy"], allowGetters = true)
abstract class UserDateAudit : DateAudit() {
    @CreatedBy
    @JsonIgnore
    @Column(nullable = false, updatable = false)
    var createdBy: Long? = null

    @LastModifiedBy
    @JsonIgnore
    @Column(nullable = false)
    var updatedBy: Long? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}
