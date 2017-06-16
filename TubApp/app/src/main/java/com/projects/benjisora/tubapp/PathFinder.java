package com.projects.benjisora.tubapp;

import com.projects.benjisora.tubapp.data.database.Utils;
import com.projects.benjisora.tubapp.data.model.Path;
import com.projects.benjisora.tubapp.data.model.Stop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yann on 16/06/2017.
 */

public class PathFinder {

    public static List<Path> getLineListForStop(Stop stop){
        return Utils.getinstance().getLineforStop(stop.getId());
    }

    public static List<Stop> getStopListForLine(final Path line){
        return ArrayUtils.cleanStopArray(Utils.getinstance().getStopForPath(line.getId()));
    }

    public static boolean areBothStopsOnSameLine(Stop fStop, Stop sStop) {
        for (Path line : getLineListForStop(fStop)) {
            if (getLineListForStop(sStop).contains(line)) {
                return true;
            }
        }
        return false;
    }

    private static List<Path> getLinesForStops(Stop fStop, Stop sStop) {
        ArrayList<Path> lines = new ArrayList<>();
        for (Path line : getLineListForStop(fStop)) {
            if (getLineListForStop(sStop).contains(line)) {
                lines.add(line);
            }
        }
        return lines;
    }

    public static List<Stop> getDirectionsOnSameLineBetween(Stop start, Stop end) {
        int minStopsIndex = 0;
        int minStopsCount;
        //init minStopsCount :
        ArrayList<Path> possibleLines = (ArrayList<Path>) getLinesForStops(start, end);
        ArrayList<Stop> initstopList = (ArrayList<Stop>) getStopListForLine(possibleLines.get(0));
        if (initstopList.indexOf(start) < initstopList.indexOf(end)) {
            minStopsCount = initstopList.subList(initstopList.indexOf(start), initstopList.indexOf(end)).size();
        } else {
            minStopsCount = initstopList.subList(initstopList.indexOf(end), initstopList.indexOf(start)).size();
        }
        for (int i = 0; i < possibleLines.size(); i++) {
            int currentStopCount;
            ArrayList<Stop> stopListForCurrentLine = (ArrayList<Stop>) getStopListForLine(possibleLines.get(i));
            ArrayList<Stop> trimmedStopList;

            if (stopListForCurrentLine.indexOf(start) < stopListForCurrentLine.indexOf(end)) {
                trimmedStopList = new ArrayList<>(stopListForCurrentLine.subList(stopListForCurrentLine.indexOf(start), stopListForCurrentLine.indexOf(end)));
            } else {
                trimmedStopList = new ArrayList<>(stopListForCurrentLine.subList(stopListForCurrentLine.indexOf(end), stopListForCurrentLine.indexOf(start)));
            }

            currentStopCount = trimmedStopList.size();

            if (currentStopCount < minStopsCount) {
                minStopsCount = currentStopCount;
                minStopsIndex = i;
            }
        }

        ArrayList<Stop> listOfAllStopsFromTheShortestLine = (ArrayList<Stop>) getStopListForLine(possibleLines.get(minStopsIndex));
        ArrayList<Stop> directions;
        //TODO : Include last stop
        if (listOfAllStopsFromTheShortestLine.indexOf(start) < listOfAllStopsFromTheShortestLine.indexOf(end)) {
            directions = new ArrayList<>(listOfAllStopsFromTheShortestLine.subList(initstopList.indexOf(start), initstopList.indexOf(end)));
        } else {
            directions = new ArrayList<>(listOfAllStopsFromTheShortestLine.subList(initstopList.indexOf(end), initstopList.indexOf(start)));
            Collections.reverse(directions);
        }

        return directions;
    }

    public static HashMap<Path, ArrayList<Stop>> getDirectionsOnDifferentLinesBetween(Stop start, Stop end) {
        ArrayList<Path> linesForStart = (ArrayList<Path>) getLineListForStop(start);
        ArrayList<Path> linesForEnd = (ArrayList<Path>) getLineListForStop(end);

        linesForStart = (ArrayList<Path>) ArrayUtils.cleanLineArray(linesForStart);
        linesForEnd = (ArrayList<Path>) ArrayUtils.cleanLineArray(linesForEnd);
        ArrayList<HashMap<Path, ArrayList<Stop>>> possiblePaths = new ArrayList<>();

        for (Path line : linesForStart) {
            HashMap<Path, ArrayList<Stop>> stops = new HashMap<>();
            ArrayList<Stop> listFromStart = (ArrayList<Stop>) getStopListForLine(line);
            listFromStart = new ArrayList<>(listFromStart.subList(listFromStart.indexOf(start), listFromStart.size()));
            boolean shouldGoOn = true;
            for (Stop stop : listFromStart) {
                for (Path lineE : getLineListForStop(stop)) {
                    if (getStopListForLine(lineE).contains(end)) {
                        stops.putAll(getStopsFromStopToStop(stop, end, lineE));
                        possiblePaths.add(stops);
                        shouldGoOn = false;
                    } else {
                        if (stops.containsKey(line)) {
                            if (!stops.get(line).contains(stop)) {
                                stops.get(line).add(stop);
                            }
                        } else {
                            stops.put(line, new ArrayList<Stop>(Arrays.asList(stop)));
                        }
                    }
                    if (!shouldGoOn) {
                        break;
                    }
                }
                if (!shouldGoOn) {
                    break;
                }
            }
            possiblePaths.add(stops);
        }
        HashMap<Path, ArrayList<Stop>> bestPath = possiblePaths.get(0);
        for(HashMap<Path, ArrayList<Stop>> stops : possiblePaths) {
            if(stops.size() < bestPath.size()) {
                bestPath = stops;
            }
        }
        return bestPath;
    }

    private static Map<Path, ArrayList<Stop>> getStopsFromStopToStop(Stop start, Stop end, Path line) {
        HashMap<Path, ArrayList<Stop>> hashmap = new HashMap<>();
        ArrayList<Stop> stopsForLine = (ArrayList<Stop>) getDirectionsOnSameLineBetween(start, end);
        hashmap.put(line, stopsForLine);
        return hashmap;
    }
}
