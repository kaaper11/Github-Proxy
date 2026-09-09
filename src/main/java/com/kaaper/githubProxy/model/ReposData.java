package com.kaaper.githubProxy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "repos_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReposData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String description;

    private String cloneUrl;

    private String stars;

    private LocalDateTime createdAt;

    private String owner;

    private String repositoryName;
}
