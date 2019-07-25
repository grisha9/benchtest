package ru.rzn.gr.myasoedov;

import java.io.Serializable;

/**
 * Created by grisha on 25.07.19.
 */
public class Interval implements Serializable {
    private Integer start;
    private Integer end;

    public Interval(Integer start, Integer end) {
        this.start = start;
        this.end = end;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }
}
