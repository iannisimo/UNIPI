import java.util.Date;

public class Movement {
    private Date date;
    private String causal;
    
    public void setDate(Date date) {
        this.date = date;
    }

    public void setCausal(String causal) {
        this.causal = causal;
    }

    public Date getDate() {
        return this.date;
    }

    public String getCausal() {
        return this.causal;
    }
}
