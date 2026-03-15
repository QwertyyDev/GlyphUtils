package com.qwertydev.glyphutils.session;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerSession {
    
    private boolean waitingForInput;
    private SessionType sessionType;
    private String inputText;
    private String color1;
    private String color2;
    private boolean bold;
    private boolean italic;
    private boolean underline;
    private boolean strikethrough;
    
    public void reset() {
        this.waitingForInput = false;
        this.sessionType = null;
        this.inputText = null;
        this.color1 = null;
        this.color2 = null;
        this.bold = false;
        this.italic = false;
        this.underline = false;
        this.strikethrough = false;
    }
}