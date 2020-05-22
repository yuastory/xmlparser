package com.yuastory.xml;

import com.yuastory.xml.interfc.Attribute;

public class BIAttribute implements Attribute
{
    private String key;
    private String value;

    public BIAttribute(String key, String value)
    {
        this.key = key;
        this.value = value;
    }

    public String getKey()
    {
        return key;
    }

    public String getValue()
    {
        return value;
    }

    public String toString()
    {
        return " " + key + "=\"" + value + "\"";
    }
}