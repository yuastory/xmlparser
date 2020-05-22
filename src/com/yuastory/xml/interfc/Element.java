package com.yuastory.xml.interfc;

import java.util.List;

public interface Element {
	public Element getElement(String k); // getting specific named k element

    public Attribute getAttribute(String k); // getting specific named k attribute

    public List<Element> getChildElement(); // getting all child element as list

    public List<Attribute> getChildAttribute();// getting all child attribute as list

    public String getName(); // return element name selfish

    public List<String> getText(); // return element's text

    public List<String> getComment();
}

