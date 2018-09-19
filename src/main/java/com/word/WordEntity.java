package com.word;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WordEntity {

    private String word;
    @Builder.Default
    private Integer count = 1;

    public void increaseCount() {
        this.count++;
    }
}
