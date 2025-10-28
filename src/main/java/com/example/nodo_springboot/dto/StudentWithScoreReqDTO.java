// java
package com.example.nodo_springboot.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentWithScoreReqDTO {
    @JsonProperty("student_dto")
    private StudentRequestDTO student;
    @JsonProperty("list_score_dto")
    private List<ScoreDTO> scores;
}
