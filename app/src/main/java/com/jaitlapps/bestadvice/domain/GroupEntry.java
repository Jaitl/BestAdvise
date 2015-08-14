package com.jaitlapps.bestadvice.domain;

import java.util.ArrayList;
import java.util.List;

public class GroupEntry extends Entry {
    private List<RecordEntry> children = new ArrayList<>();

    public void setChildrenRecord(RecordEntry record) {
        children.add(record);
    }

    public RecordEntry getChildrenRecord(int location) {
        return children.get(location);
    }

    public int getChildrenSize() {
        return children.size();
    }
}
