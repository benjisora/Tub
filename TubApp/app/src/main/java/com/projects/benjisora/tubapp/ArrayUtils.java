package com.projects.benjisora.tubapp;

import com.projects.benjisora.tubapp.data.model.Path;
import com.projects.benjisora.tubapp.data.model.Stop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yann on 16/06/2017.
 */

public class ArrayUtils {

    public static boolean isStopAlreadyInArray(List<Stop> stopList, Stop stop) {
        for (Stop stopInArray : stopList) {
            if (stopInArray.getLabel().equals(stop.getLabel())) {
                return true;
            }
        }
        return false;
    }

    public static List<Stop> cleanStopArray(List<Stop> stopList) {
        ArrayList<Stop> cleanArray = new ArrayList<>();
        cleanArray.addAll(stopList);
        for (int i = 0; i < cleanArray.size() - 1; i++) {
            for (int j = i + 1; j < cleanArray.size(); j++) {
                if (cleanArray.get(i).getLabel().equals(cleanArray.get(j).getLabel())) {
                    cleanArray.remove(j);
                }
            }
        }
        stopList.clear();
        return cleanArray;
    }

    public static List<Path> cleanLineArray(List<Path> stopList) {
        ArrayList<Path> cleanStopArray = new ArrayList<>();
        cleanStopArray.addAll(stopList);
        for (int i = 0; i < cleanStopArray.size() - 1; i++) {
            for (int j = i + 1; j < cleanStopArray.size(); j++) {
                if (cleanStopArray.get(i).getLabel().equals(cleanStopArray.get(j).getLabel())) {
                    cleanStopArray.remove(j);
                }
            }
        }
        stopList.clear();
        return cleanStopArray;
    }

}
