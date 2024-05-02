package com.rebook.common.schema;

import lombok.Getter;

import java.util.List;

@Getter
public class ListResponse<T> {
    List<T> items;

    public ListResponse() {}

    public ListResponse(List<T> items) {
        this.items = items;
    }

}
