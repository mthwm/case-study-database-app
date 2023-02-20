package com.moravek.applicantsdatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TechnologyStats {
    private String name;
    private int count;
    private List<Integer> levels = new ArrayList<>();
    private double medianLevel;
    private double averageLevel;

    public TechnologyStats(String name, int level) {
        this.name = name;
        this.count = 1;
        levels.add(level);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getMedianLevel() {
        return medianLevel;
    }

    public void setMedianLevel(double medianLevel) {
        this.medianLevel = medianLevel;
    }

    public double getAverageLevel() {
        return averageLevel;
    }

    public void setAverageLevel(double averageLevel) {
        this.averageLevel = averageLevel;
    }

    public List<Integer> getLevels() {
        return levels;
    }

    public void setLevels(List<Integer> levels) {
        this.levels = levels;
    }

    public void calculateAverageLevel() {
        int sum = 0;
        for (Integer level : levels) {
            sum += level;
        }
        this.averageLevel = sum / (double) (levels.size());
    }

    public void calculateMedian() {
        Collections.sort(levels);
        
        if (levels.size() % 2 == 0) {
            int midIndex1 = levels.size() / 2 - 1;
            int midIndex2 = levels.size() / 2;
            int midValue1 = levels.get(midIndex1);
            int midValue2 = levels.get(midIndex2);
            medianLevel = (double) (midValue1 + midValue2) / 2;
        } else {
            int middleIndex = levels.size() / 2;
            medianLevel = levels.get(middleIndex);
        }
    }

    public void increaseCount() {
        this.count++;
    }
}
