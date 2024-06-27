package com.rebook.common.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PageInfo {
    private int currentPage;
    private int pageSize;
    private boolean hasNext;
}
