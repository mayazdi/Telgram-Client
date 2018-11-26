package com.Telgram.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class Messages implements Serializable {
    public User from;
    public User to;
    public HashMap value;
    public Date sentDate;
    public Action action;
}
