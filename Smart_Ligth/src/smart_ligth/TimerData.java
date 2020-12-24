/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smart_ligth;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Timer;
import static smart_ligth.Shouse.getInstant;

/**
 *
 * @author User
 */
public class TimerData {

    private static final AtomicInteger autoid = new AtomicInteger(0);
    int id;
    long days;
    long hours;
    long minute;
    String mode;
    LocalDateTime localdateTime;
    public static List<TimerData> schedulList;

    public TimerData(long days, long hours, long minute, String mode) {
        id = autoid.incrementAndGet();
        localdateTime = LocalDateTime.now();
        this.days = days;
        this.hours = hours;
        this.minute = minute;
        this.mode = mode;
    }

    public long getScdulabyMiilseconds() {
        return TimeUnit.MILLISECONDS.convert(this.getDays(), TimeUnit.DAYS)
                + TimeUnit.MILLISECONDS.convert(this.getHours(), TimeUnit.HOURS)
                + TimeUnit.MILLISECONDS.convert(this.getMinute(), TimeUnit.MINUTES);

    }

    public int getId() {
        return id;
    }

    public long getDays() {
        return days;
    }

    public long getHours() {
        return hours;
    }

    public long getMinute() {
        return minute;
    }

    public String getMode() {
        return mode;
    }

    public LocalDateTime getLocaldateTime() {
        return localdateTime;
    }

    @Override
    public String toString() {
        return "TimerData{" + "id=" + this.getId() + ", days=" + this.getDays() + ", hours=" + this.getHours() + ", minute=" + this.getMinute() + ", mode=" + this.getMode() + ", localdateTime=" + this.localdateTime + '}';
    }

}
