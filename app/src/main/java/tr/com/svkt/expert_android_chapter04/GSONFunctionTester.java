package tr.com.svkt.expert_android_chapter04;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

    public void storeJSON(){
        MainObject mo = MainObject.createTestMainObject();
        Gson gson = new Gson();
        String jsonString = gson.toJson(mo);
        Log.i(TAG, jsonString);

        SharedPreferences sp = getSharedPreferences();
        SharedPreferences.Editor  spe = sp.edit();
        spe.putString("json", jsonString);
        spe.commit();
    }

    public void retrieveJSON(){
        SharedPreferences sp = getSharedPreferences();
        String jsonString = sp.getString("json", null);
        if(jsonString == null){
            Log.i(TAG, "Not able to read preference.");
            return;
        }
        Gson gson = new Gson();
        MainObject mo = gson.fromJson(jsonString, MainObject.class);
        Log.i(TAG, "Object successfully retrieved.");
        String compareResult = MainObject.checkTestMainObject(mo);
        if(compareResult != null){
            Log.i(TAG, compareResult);
            return;
        }
        //compareResult is null
        Log.i(TAG, "compareResult is null");
        return;
    }

    public void saveJSONToPrivateStorage(){
        //String jsonString = createJSONString();
        String jsonString = new Gson().toJson(MainObject.createTestMainObject());
        saveToInternalFile(jsonString);

        String retrievedString = this.readFromInternalFile();

        //Create the object from retrievedString
        Gson gson = new Gson();
        MainObject mo = gson.fromJson(retrievedString, MainObject.class);
        //make sure it is the same object
        MainObject srcObject = MainObject.createTestMainObject();
        String compareResult = mo.checkTestMainObject(srcObject);
        Log.i(TAG + " saveJSONToPrivateStorage: ", compareResult);
    }

    private void saveToInternalFile(String jsonString) {
        FileOutputStream fos = null;
        Context appContext = MyApplication.sApplicationContext;
        try {
            fos = appContext.openFileOutput("datastore-json.txt", Context.MODE_PRIVATE);
            fos.write(jsonString.getBytes());
        } catch (IOException e) {
            Log.d(TAG, "Cannot create or write to a file");
        }finally {
            closeStreamSilently(fos);
        }
    }

    private String readFromInternalFile() {
        FileInputStream fis = null;
        Context appContext = MyApplication.sApplicationContext;
        try {
            fis = appContext.openFileInput("datastore-json.txt");
            String jsonString = readStreamAsString(fis);
            return jsonString;
        } catch (IOException e){
            Log.d(TAG, "Cannot create or read from a file");
            return null;
        }finally {
            closeStreamSilently(fis);
        }
    }

    private String readStreamAsString(InputStream is) throws
    FileNotFoundException, IOException{
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            copy(is, baos);
            return baos.toString();
        }finally {
            if(baos != null)
                closeStreamSilently(baos);
        }
    }

    private void copy(InputStream reader, OutputStream writer) throws IOException{
        byte[] byteArray = new byte[4092];
        while (true){
            int numOfBytesRead = reader.read(byteArray, 0, 4092);
            if(numOfBytesRead == -1)
                break;
            writer.write(byteArray, 0, numOfBytesRead);
        }
        return;
    }

    private void closeStreamSilently(InputStream is) {
        if(is == null) return;
        try {
            is.close();
        }catch (IOException e){
            throw new RuntimeException("Exception closing a file, " + e);
        }
    }

    private void closeStreamSilently(OutputStream os) {
        if(os == null) return;
        try {
            os.close();
        } catch (IOException e) {
            throw new RuntimeException("Exception closing a file, " + e);
        }
    }

    private String createJSONString() {
        MainObject mo = MainObject.createTestMainObject();
        Gson gson = new Gson();
        return gson.toJson(mo);
    }
}
