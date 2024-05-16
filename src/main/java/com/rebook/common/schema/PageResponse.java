package com.rebook.common.schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PageResponse<T> {

    private List<T> items;

    private Page<T> pageInfo;
}
