package dto;

import java.time.LocalDate;

public class GroupByDate implements java.util.Comparator<LocalDate> {
    @Override
    public int compare(LocalDate o1, LocalDate o2) {
        return o1.compareTo(o2);
    }
}
