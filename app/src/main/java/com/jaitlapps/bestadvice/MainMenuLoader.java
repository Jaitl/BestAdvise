package com.jaitlapps.bestadvice;

import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.jaitlapps.bestadvice.domain.GroupEntry;
import com.jaitlapps.bestadvice.domain.RecordEntry;
import com.jaitlapps.bestadvice.domain.list.GroupListEntry;
import com.jaitlapps.bestadvice.domain.list.RecordListEntry;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class MainMenuLoader {
    private AssetManager assetManager;
    private Gson gson = new Gson();

    public MainMenuLoader(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public List<GroupEntry> loadMainMenu() {
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

    private RecordListEntry loadRecords() {
        String jsonRecords = loadJsonDataFromFile("record");

        gson = new Gson();
        RecordListEntry recordListEntry = gson.fromJson(jsonRecords, RecordListEntry.class);

        return recordListEntry;
    }

    private GroupListEntry loadGroups() {
        String jsonGroups = loadJsonDataFromFile("group");

        GroupListEntry groupListEntry = gson.fromJson(jsonGroups, GroupListEntry.class);

        return groupListEntry;
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
