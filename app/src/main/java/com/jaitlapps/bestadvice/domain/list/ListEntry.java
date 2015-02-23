package com.jaitlapps.bestadvice.domain.list;

import com.jaitlapps.bestadvice.domain.Entry;

import java.util.ArrayList;
import java.util.List;

public class ListEntry<T extends Entry> {
    private List<T> list;

    public ListEntry() {
        list = new ArrayList<>();
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public void add(T entry) {
        list.add(entry);
    }
}
