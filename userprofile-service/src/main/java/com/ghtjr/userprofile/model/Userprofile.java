package com.ghtjr.userprofile.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "userprofiles")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Userprofile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String uuid;

    @Column(unique = true, nullable = false)
    private String nickname;

    private String blogName;
    private String bio;
}
