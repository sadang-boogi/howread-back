package com.rebook.common.schema;

import com.rebook.common.domain.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PageResponse<T> {
    List<T> items;
    PageInfo pageInfo;
}
