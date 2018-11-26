package com.Telgram.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class Message implements Serializable {
    public HashMap <String ,String> value;
    public Action action;
}
