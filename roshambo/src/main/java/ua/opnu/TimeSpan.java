package ua.opnu;

public class TimeSpan {

    private int totalMinutes;

    public TimeSpan() { this.totalMinutes = 0; }

    public TimeSpan(int minutes) {
        this.totalMinutes = (minutes < 0) ? 0 : minutes;
    }

    public TimeSpan(int hours, int minutes) {
        if (hours < 0 || minutes < 0 || minutes > 59) {
            this.totalMinutes = 0;
        } else {
            this.totalMinutes = hours * 60 + minutes;
        }
    }

    public TimeSpan(TimeSpan other) {
        this.totalMinutes = (other == null) ? 0 : other.totalMinutes;
    }

    public int getHours() { return totalMinutes / 60; }
    public int getMinutes() { return totalMinutes % 60; }
    public int getTotalMinutes() { return totalMinutes; }
    public double getTotalHours() { return totalMinutes / 60.0; }

    public void add(int hours, int minutes) {
        if (hours < 0 || minutes < 0 || minutes > 59) return;
        this.totalMinutes += hours * 60 + minutes;
    }

    public void add(int minutes) {
        if (minutes < 0) return;
        this.totalMinutes += minutes;
    }

    public void add(TimeSpan span) {
        if (span == null) return;
        this.totalMinutes += span.totalMinutes;
    }

    public void subtract(int hours, int minutes) {
        if (hours < 0 || minutes < 0 || minutes > 59) return;
        int delta = hours * 60 + minutes;
        if (delta > this.totalMinutes) return;
        this.totalMinutes -= delta;
    }

    public void subtract(int minutes) {
        if (minutes < 0 || minutes > this.totalMinutes) return;
        this.totalMinutes -= minutes;
    }

    public void subtract(TimeSpan span) {
        if (span == null) return;
        int other = span.totalMinutes;
        if (other > this.totalMinutes) return;
        this.totalMinutes -= other;
    }

    public void scale(int factor) {
        if (factor <= 0) return;
        this.totalMinutes *= factor;
    }

    @Override
    public String toString() {
        return String.format("%d:%02d", getHours(), getMinutes());
    }
}
