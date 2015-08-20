package com.jaitlapps.bestadvice;

import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.jaitlapps.bestadvice.domain.GroupEntry;
import com.jaitlapps.bestadvice.domain.RecordEntry;
import com.jaitlapps.bestadvice.domain.list.GroupListEntry;
import com.jaitlapps.bestadvice.domain.list.ListRecordGroup;
import com.jaitlapps.bestadvice.domain.list.RecordListEntry;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class DataLoader {
    private AssetManager assetManager;
    private Gson gson = new Gson();

    public DataLoader(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public List<GroupEntry> loadCategories() {
        List<GroupEntry> groupsList = loadGroups().getList();
        List<RecordEntry> recordsList = loadRecords().getList();

        for(GroupEntry groupEntry: groupsList) {
            for(RecordEntry recordEntry: recordsList) {
                if(recordEntry.getGroupId().compareTo(groupEntry.getId()) == 0) {
                    groupEntry.setChildrenRecord(recordEntry);
                }
            }
        }

        return groupsList;
    }

    public ListRecordGroup loadListRecords() {
        List<GroupEntry> groupsList = loadGroups().getList();
        List<RecordEntry> recordsList = loadRecords().getList();

        recordsList = new ArrayList<>(recordsList);

        Collections.reverse(recordsList);

        ListRecordGroup listRecordGroup = new ListRecordGroup();
        listRecordGroup.setRecordEntryList(recordsList);

        HashMap<String, GroupEntry> groupMap = new HashMap<>();

        for(GroupEntry group: groupsList) {
            groupMap.put(group.getId(), group);
        }

        listRecordGroup.setGroupEntryMap(groupMap);

        return listRecordGroup;
    }

    private synchronized RecordListEntry loadRecords() {

        String jsonRecords = loadJsonDataFromFile("record");
        return gson.fromJson(jsonRecords, RecordListEntry.class);
    }

    private synchronized GroupListEntry loadGroups() {

        String jsonGroups = loadJsonDataFromFile("group");
        return gson.fromJson(jsonGroups, GroupListEntry.class);
    }

    private String loadJsonDataFromFile(String name) {
        InputStream inputStream = null;
        byte[] jsonBytes = null;
        String jsonRecords = null;

        try {
            inputStream = assetManager.open("bestadvice/data/" + name + ".json");
            jsonBytes = new byte[inputStream.available()];
            inputStream.read(jsonBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            jsonRecords = new String(jsonBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return jsonRecords;
    }
}
