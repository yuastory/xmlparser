package com.yuastory.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.yuastory.xml.interfc.Element;
import com.yuastory.xml.interfc.XML;

public class BIXML implements XML
{
    private BIElement root;
    private String contents;

    public BIXML()
    {
        contents = "";
    }

    private void makeHierarchy()
    {
        root = new BIElement();
        makeHierarchy0(0, root);
        root.getValue();
    }

    private int makeHierarchy0(int stIdx, BIElement node)
    {
        int angleStIdx = stIdx;
        int angleFnIdx = angleStIdx - 1;
        for (int i = stIdx; i < contents.length(); i++)
        {
            char c = contents.charAt(i);
            switch (c)
            {
                case '<':
                    if (!(node.getValue().trim().equals("")) && i > angleFnIdx + 1) node.setText(contents.substring(angleFnIdx + 1, i).trim());

                    if (i + 3 < contents.length() && contents.charAt(i + 1) == '!' && contents.charAt(i + 2) == '-' && contents.charAt(i + 3) == '-')
                    {
                        for (int j = i + 5; j < contents.length(); j++)
                        {
                            if (contents.charAt(j - 2) == '-' && contents.charAt(j - 1) == '-' && contents.charAt(j) == '>')
                            {
                                node.addComment(contents.substring(i, j + 1));
                                contents = contents.substring(0, i) + contents.substring(j + 1, contents.length());
                                break;
                            }
                        }
                    }
                    angleStIdx = i;
                    break;
                case '>':
                    angleFnIdx = i;
                    
                    if (node.getValue().equals(""))
                    {
                        node.setValue(contents.substring(angleStIdx, i + 1));
                    }

                    else if (contents.charAt(i - 1) == '/')
                    {
                        BIElement sub = new BIElement(contents.substring(angleStIdx, i + 1));
                        sub = parseAttributes(sub);
                        node.addElement(sub);
                    }

                    else if (contents.charAt(angleStIdx + 1) == '/')
                    {
                        node.setValue(node.getValue() + contents.substring(angleStIdx, i + 1)); // value = <ABC></ABC>
                        return i;
                    }

                    else if (!node.getValue().equals(""))
                    {
                        BIElement sub = new BIElement(contents.substring(angleStIdx, i + 1));
                        sub = parseAttributes(sub); // parsing attributes
                        node.addElement(sub);
                        i = makeHierarchy0(i + 1, sub);
                        angleFnIdx = i;
                    }

                    break;
                default:
                    break;
            }
        }

        return contents.length();
    }

    private BIElement parseAttributes(BIElement node)
    {
        String str = node.getValue();
        String key = "";
        String value = "";
        for (int i = 0; i < str.length(); i++)
        {
            // when cursor points at the middle of a key and a value in which an equal mark is located.
            if (str.charAt(i) == '=')
            {
                // stores key
                for (int j = i - 1; j > 0; j--)
                {
                    // when blank is found, in addition, spaces happen to be placed the both side of an equal mark, so allows to save those until an first character appears which is not a space.
                    if (str.charAt(j) == ' ' && str.trim().length() != 0)
                    {
                        StringBuilder sb = new StringBuilder(key); // cuz it goes left the character order have to be reversed.
                        key = sb.reverse().toString();
                        break;
                    }
                    key += str.charAt(j); // otherwise the character is appended on the end of the key string
                }

                int doubleQuatCount = 0;
                for (int j = i + 1; j < str.length(); j++)
                {
                    if (str.charAt(j) == '"') doubleQuatCount++;
                    else value += str.charAt(j);

                    if (doubleQuatCount == 2)
                    {
                        i = j; // move cursor onto the end of this attribute.
                        break;
                    }
                }

                node.addAttribute(key, value);
                key = "";
                value = "";
            }
        }

        return node;
    }

    public void read(String s)
    {
        contents = s;
        makeHierarchy();
    }

    public void read(File f)
    {
        if (!f.exists() || f.isDirectory())
        {
            System.out.println("File doesn't exist.");
            String workingDir = System.getProperty("user.dir");
            System.out.println("Put the file in \"" + workingDir + "\"");
            return;
        }

        try
        {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String s = "";
            while ((s = br.readLine()) != null)
            {
                contents += s;
            }

            br.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        makeHierarchy();
    }

    public Element getRootElement()
    {
        return root;
    }
}


