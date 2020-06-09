package com.example.demo.model.role

import org.hibernate.annotations.NaturalId
import javax.persistence.*

@Entity
data class Role(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @Enumerated(EnumType.STRING)
        @NaturalId var name: RoleName? = null
)
