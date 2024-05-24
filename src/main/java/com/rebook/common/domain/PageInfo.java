package com.rebook.common.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PageInfo {
    private int page;
    private int size;
    private boolean hasNext;
}
