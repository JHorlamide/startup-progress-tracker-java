package com.olamide.startup_progress_tracker.DTO;

import com.olamide.startup_progress_tracker.entities.Task;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Getter
@Setter
public class PhaseDTO {
    private Long id;
    private String name;
    private String description;
    private Boolean done;
    private Integer stage;
    private List<Map<String, Object>> tasks;

}
