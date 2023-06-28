package com.olamide.startup_progress_tracker.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Table(name = "Phase")
public class Phase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @NotBlank(message = "Name cannot be empty")
    @Column(name = "name", nullable = false)
    private  String name;

    @NonNull
    @NotBlank(message = "Description cannot be empty")
    @Column(name = "description", nullable = false)
    private String description;

    @NonNull
    @Column(name = "dine", nullable = false)
    private Boolean done;

    @Column(name = "stage")
    private Integer stage;

    @JsonIgnore
    @OneToMany(mappedBy = "phase", cascade = CascadeType.ALL)
    private List<Task> tasks;
}
