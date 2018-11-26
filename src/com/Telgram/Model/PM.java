package com.Telgram.Model;

import java.io.Serializable;

public class PM implements Serializable {
    public PM(String content, String date, boolean incoming) {
        this.Content = content;
        this.Date = date;
        this.Incoming = incoming;

    }

    public void SetSeen() {
        this.Seen = true;
    }
    public String getContent() { return Content; }
    public String getDate() { return Date; }
    public boolean getIncoming() {return this.Incoming;}
    public boolean getSeen() {return this.Seen;}

    private String Content;
    private String Date;
    private boolean Seen = false;
    private boolean Incoming = true;

}
