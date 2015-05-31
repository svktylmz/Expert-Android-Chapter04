package tr.com.svkt.expert_android_chapter04;

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
}
