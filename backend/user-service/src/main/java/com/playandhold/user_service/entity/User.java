package com.playandhold.user_service.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(
        name = "users_db",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_users_db_username",
                        columnNames = "username"
                ),
                @UniqueConstraint(
                        name = "uk_users_db_email",
                        columnNames = "email"
                )
        }
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "middle_name", length = 50)
    private String middleName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 254)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Gender gender;

    @Column(nullable = false, length = 50)
    private String country;

    @Column(nullable = false, length = 50)
    private String city;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @CreationTimestamp
    @Column(name = "register_at")
    private Instant registeredAt;
}
