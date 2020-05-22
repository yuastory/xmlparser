package com.yuastory.xml;
import java.util.ArrayList;
import java.util.List;

import com.yuastory.xml.interfc.Attribute;
import com.yuastory.xml.interfc.Element;

public class BIElement implements Element
{
    private List<Element>   elements;
    private List<Attribute> attributes;
    private String           value;
    private String           parsedValue;
    public String            text;
    private List<String>     comments;

    public BIElement()
    {
        this("");
    }

    public BIElement(String value)
    {
        this.value = value;
        parsedValue = subStringValue(value);
        elements = new ArrayList<Element>(3);
        attributes = new ArrayList<Attribute>(3);
        text = "";
        comments = new ArrayList<String>(3);
    }

    public Element getElement(String k)
    {
        BIElement be = new BIElement();
        be.addElement(this);
        return getElement0(k, be.elements);
    }

    private Element getElement0(String k, List<Element> list)
    {
        Element be = new BIElement();
        int size = list.size();
        for (int i = 0; i < size; i++)
        {
            BIElement element = (BIElement) list.get(i);
            String val = subStringValue(element.getValue());
            if (val.equals(k)) return element;

            be = element.getElement0(k, element.elements);
        }
        return be;
    }

    // value ?•µ?‹¬ ê°’ë§Œ ì¶”ì¶œ
    public String subStringValue(String val)
    {
        if (val.equals("")) return val;
        
        val = val.substring(1).trim();

        for (int j = 0; j < val.length(); j++)
        {
            if (val.charAt(j) == ' ' || val.charAt(j) == '>') val = val.substring(0, j);
        }

        return val;
    }

    public Attribute getAttribute(String k)
    {
        BIElement be = new BIElement();
        be.addElement(this);
        return getAttribute0(k, be.elements);
    }

    private Attribute getAttribute0(String k, List<Element> list)
    {
        Attribute attribute = new BIAttribute("", "");
        int size = list.size();
        for (int i = 0; i < size; i++)
        {
            BIElement element = (BIElement) list.get(i);
            for (Attribute attr : element.attributes)
            {
                if (attr.getKey().equals(k)) return attr;
            }

            attribute = getAttribute0(k, element.elements);
        }
        return attribute;
    }

    public List<Element> getChildElement()
    {
        BIElement be = new BIElement();
        be.addElement(this);
        List<Element> list = getChildElement0(be.elements);

        return list;
    }

    @SuppressWarnings("unchecked")
    private List<Element> getChildElement0(List<Element> list)
    {
        int size = list.size();
        for (int i = 0; i < size; i++)
        {
            BIElement element = (BIElement) list.get(i);
            list.addAll(getChildElement0((ArrayList<Element>) ((ArrayList<Element>) (element.elements)).clone()));
        }

        return list;
    }

    public List<Attribute> getChildAttribute()
    {
        BIElement be = new BIElement();
        be.addElement(this);

        return getChildAttribute0(be.elements, new ArrayList<Attribute>());
    }

    @SuppressWarnings("unchecked")
    private List<Attribute> getChildAttribute0(List<Element> list, List<Attribute> listAttr)
    {
        int size = list.size();
        for (int i = 0; i < size; i++)
        {
            BIElement element = (BIElement) list.get(i);
            listAttr.addAll(getChildAttribute0(element.elements, (ArrayList<Attribute>) ((ArrayList<Attribute>) (element.attributes)).clone()));
        }

        return listAttr;
    }

    public String getName()
    {
        return null;
    }

    public List<String> getText()
    {
        BIElement be = new BIElement();
        be.addElement(this);

        return getText0(be.elements, new ArrayList<String>());
    }

    private List<String> getText0(List<Element> list, List<String> listText)
    {
        int size = list.size();
        for (int i = 0; i < size; i++)
        {
            BIElement element = (BIElement) list.get(i);
            listText.add(element.text);
            listText = getText0(element.elements, listText);
        }

        return listText;
    }

    public List<String> getComment()
    {
        BIElement be = new BIElement();
        be.addElement(this);

        return getComment0(be.elements, new ArrayList<String>());
    }

    @SuppressWarnings("unchecked")
    private List<String> getComment0(List<Element> list, List<String> listComment)
    {
        int size = list.size();
        for (int i = 0; i < size; i++)
        {
            BIElement element = (BIElement) list.get(i);
            listComment.addAll(getComment0(element.elements, (ArrayList<String>) ((ArrayList<String>) (element.comments)).clone()));
        }

        return listComment;
    }

    public String toString()
    {
        return parsedValue;
    }

    public void addComment(String comment)
    {
        comments.add(comment);
    }

    public void addAttribute(String key, String value)
    {
        attributes.add(new BIAttribute(key, value));
    }

    public void addElement(BIElement t)
    {
        elements.add(t);
    }

    public void setValue(String s)
    {
        value = s;
        parsedValue = subStringValue(value);
    }
    
    // Pure Setters, Getters
    public void setText(String text)
    {
        this.text = text;
    }

    public String getValue()
    {
        return value;
    }

    public String getParsedValue()
    {
        return parsedValue;
    }

    public void setParsedValue(String parsedValue)
    {
        this.parsedValue = parsedValue;
    }

    public List<Element> getElements()
    {
        return elements;
    }
    
    public List<Attribute> getAttributes()
    {
        return attributes;
    }
    
    public List<String> getComments()
    {
        return comments;
    }
}
