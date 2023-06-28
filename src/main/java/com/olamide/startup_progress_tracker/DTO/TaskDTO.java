package com.olamide.startup_progress_tracker.DTO;

import com.olamide.startup_progress_tracker.entities.Phase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class TaskDTO {
    private Long id;
    private String name;
    private String description;
    private Boolean completed;
    private Phase phase;
}
