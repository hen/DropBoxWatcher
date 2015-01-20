package org.literacybridge.util.dropbox;

import com.dropbox.core.*;
import java.io.*;
import java.util.Locale;

public class DropBoxWatcher {


    // TODO: Pass in app key and app secret as variables
    // TODO: Pass in the auth code as a variabl
    // TODO: If no auth code, kick off the interaction with the UI to go get one
    
    // TODO: General API then becomes one of setting the variables, and saying 'watch <filename(s)>'
    // TODO: Support wildcards via Commons IO
    // TODO: Also set a Listener. Event's passed when a file is changed. 
    public static void main(String[] args) throws IOException, DbxException {
        // Get your app key and secret from the Dropbox developers website.
        final String APP_KEY = "NEEDS SETTING";
        final String APP_SECRET = "NEEDS SETTING";

        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

        DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0",
            Locale.getDefault().toString());

        // If token unknown, use the authFirstTime method
        // String accessToken = authFirstTime(appInfo, config);
        String accessToken = "NEEDS SETTING";
        DbxClient client = new DbxClient(config, accessToken);

        // NOTE: No encoding handling here. 
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            DbxEntry.File downloadedFile = client.getFile("/regions.txt", null,
                outputStream);
            System.out.println("Metadata: " + downloadedFile.toStringMultiline());
            System.out.println("Data: " + outputStream);
        } finally {
            outputStream.close();
        }
        
        // return outputStream.toString();

    }

    public static String authFirstTime(DbxAppInfo appInfo, DbxRequestConfig config) throws IOException, DbxException {
        DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);

        // Have the user sign in and authorize your app.
        String authorizeUrl = webAuth.start();
        System.out.println("1. Go to: " + authorizeUrl);
        System.out.println("2. Click \"Allow\" (you might have to log in first)");
        System.out.println("3. Copy the authorization code.");
        String code = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();

        // This will fail if the user enters an invalid authorization code.
        DbxAuthFinish authFinish = webAuth.finish(code);
        String accessToken = authFinish.accessToken;

        System.out.println("Access Token: '" + accessToken + "'");
        return accessToken;
    }
}
