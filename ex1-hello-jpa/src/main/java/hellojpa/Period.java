package hellojpa;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable //값 타입 선언
public class Period {
    private LocalDateTime startDate;
    private LocalDateTime endDate;


    public Period() {
    }

    public Period(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
