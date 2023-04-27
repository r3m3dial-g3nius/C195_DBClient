package Models;

public class ReportByDivision {

    private String division;
    private int count;

    public ReportByDivision(String division, int count) {
        this.division = division;
        this.count = count;
    }

    public String getDivision() {
        return division;
    }

    public int getCount() {
        return count;
    }
}
