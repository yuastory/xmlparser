package com.yuastory.xml.interfc;

import java.io.File;

public interface XML
{
    public void read(String s); // read from string

    public void read(File f); // read from file

    public Element getRootElement(); // getting root element
}