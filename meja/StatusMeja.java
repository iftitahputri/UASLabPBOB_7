package meja;

public class StatusMeja {
    private String status;

    public StatusMeja() {
        this.status = "Kosong";
    }

    public String getStatus() {
        return status;
    }

    public void setDipesan() {
        status = "Dipesan";
    }

    public void setKosong() {
        status = "Kosong";
    }
}
