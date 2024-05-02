package com.rebook.common.schema;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ListResponse<T> {

    private List<T> items;

}
