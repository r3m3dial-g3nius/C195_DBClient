package Models;

public class ReportByMonthType {
    private String month;
    private String type;
    private int count;

    public ReportByMonthType(String month, String type, int count) {
        this.month = month;
        this.type = type;
        this.count = count;
    }

    public String getMonth() {
        return month;
    }

    public String getType() {
        return type;
    }

    public int getCount() {
        return count;
    }
}