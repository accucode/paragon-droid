package com.kodemore.utility;

public interface KmTaskIF
{
    void handleBackground();

    void publishProgress(Integer i);

    void setMax(Integer i);

    void setMessage(String msg, Object... args);
}
