package tr.com.svkt.expert_android_chapter04.models;

import java.util.ArrayList;

public class MainObject {
    public int intValue = 5;
    public String stringValue = "st<ri>\"ng\"Value<node1>test</node2>";
    public String[] stringArray;
    public ArrayList<ChildObject> childList = new ArrayList<ChildObject>();

    public void addChild(ChildObject childObject){
        childList.add(childObject);
    }

    public void populateStringArray(){
        stringArray = new String[2];
        stringArray[0] = "first";
        stringArray[1] = "second";
    }

    //This method use to create sample MainObject
    public static MainObject createTestMainObject(){
        MainObject mo = new MainObject();
        mo.populateStringArray();
        mo.addChild(new ChildObject("Eve", 30));
        mo.addChild(new ChildObject("Adam" , 28));
        return mo;
    }

    //this method is used to verify two MainObject instances are the same.
    public static String checkTestMainObject(MainObject mo) {
        MainObject moCopy = createTestMainObject();
        if (!(mo.stringValue.equals(moCopy.stringValue)))
        {
            return "String values don't match:" + mo.stringValue;
        }
        if (mo.childList.size() != moCopy.childList.size())
        {
            return "array list size doesn't match";
        }
        //get first child
        ChildObject firstChild = mo.childList.get(0);
        ChildObject firstChildCopy = moCopy.childList.get(0);
        if (!firstChild.name.equals(firstChildCopy.name))
        {
            return "first child name doesnt match";
        }
        return "everything matches";
    }
}
