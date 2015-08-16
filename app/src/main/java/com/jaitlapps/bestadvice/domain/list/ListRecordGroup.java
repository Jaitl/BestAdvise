package com.jaitlapps.bestadvice.domain.list;

import com.jaitlapps.bestadvice.domain.GroupEntry;
import com.jaitlapps.bestadvice.domain.RecordEntry;

import java.util.List;
import java.util.Map;

/**
 * Created by jaitl on 16.08.15.
 */
public class ListRecordGroup {
    public ListRecordGroup() {
    }

    private List<RecordEntry> recordEntryList;
    private Map<String, GroupEntry> groupEntryMap;

    public List<RecordEntry> getRecordEntryList() {
        return recordEntryList;
    }

    public void setRecordEntryList(List<RecordEntry> recordEntryList) {
        this.recordEntryList = recordEntryList;
    }

    public Map<String, GroupEntry> getGroupEntryMap() {
        return groupEntryMap;
    }

    public void setGroupEntryMap(Map<String, GroupEntry> groupEntryMap) {
        this.groupEntryMap = groupEntryMap;
    }
}
