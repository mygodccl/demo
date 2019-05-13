package com.example.demo.orika.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonSource {
    private Integer id;
    private String name;
    private boolean active1;
    // private Boolean flag;
    // private Boolean reverse;
}
