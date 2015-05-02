package models;

/**
 * Created by antoine on 4/26/15.
 */
public class TriInfo {

    private int phCount;
    private double pbCount;


    public TriInfo() {
        this.pbCount = 0;
        this.phCount = 0;
    }

    public int getPhCount() {
        return phCount;
    }

    public void setPhCount(int phCount) {
        this.phCount = phCount;
    }

    public double getPbCount() {
        return pbCount;
    }

    public void setPbCount(double pbCount) {
        this.pbCount = pbCount;
    }
}
