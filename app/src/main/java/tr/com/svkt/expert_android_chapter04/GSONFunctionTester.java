package tr.com.svkt.expert_android_chapter04;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import tr.com.svkt.expert_android_chapter04.models.MainObject;

public class GSONFunctionTester {
    private static final String TAG = GSONFunctionTester.class.getSimpleName();

    public void testJSON(){
        MainObject mo = MainObject.createTestMainObject();
        Gson gson = new Gson();

        //Convert to string
        String jsonString = gson.toJson(mo);
        Log.d(TAG, "Object json string: " + jsonString);

        //Convert it back to object
        MainObject mo1 = gson.fromJson(jsonString, MainObject.class);
        String compareResult = MainObject.checkTestMainObject(mo1);
        Log.i(TAG, compareResult);
    }

    //Use a XML file called myprefs.xml for this example as a SharedPreferences
    private SharedPreferences getSharedPreferences(){
        SharedPreferences sp = MyApplication.sApplicationContext.
                getSharedPreferences("myprefs", Context.MODE_PRIVATE);

        return sp;
    }

    public void testEscapeCharactersInPreferences(){
        String testString = "<node1>blabhh</node1>";
        SharedPreferences sp = getSharedPreferences();

        SharedPreferences.Editor spe = sp.edit(); //prepare SharedPreferences for save
        spe.putString("test", testString); //add key value pair
        spe.commit(); //commit the changes for persistence

        //Retrieve what is stored
        String savedString = sp.getString("test", null);

        if(savedString == null){
            Log.d(TAG, "No saved string!");
            return;
        }
        Log.d(TAG, savedString);
        if(testString.equals(savedString)){
            Log.d(TAG, "Saved the string properly and They Match.");
            return;
        }
        Log.d(TAG, "TestString and SavedSting don't match.");
        return;
    }

}
