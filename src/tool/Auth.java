package tool;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.FileDataStoreFactory;

public class Auth {
	
	public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    /**
     * Define a global instance of the JSON factory.
     */
    public static final JsonFactory JSON_FACTORY = new JacksonFactory();
    

	public static Credential authorize(List<String> scopes, String credentialDatastore) throws IOException {

        // Load client secrets.
		for (String string : getResources(Pattern.compile(".*json"))) {
			System.out.println(string);
		}
        Reader clientSecretReader = new InputStreamReader(Auth.class.getClassLoader().getResourceAsStream("resources/client_secret.json"));
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, clientSecretReader);

        // Checks that the defaults have been replaced (Default = "Enter X here").
        if (clientSecrets.getDetails().getClientId().startsWith("Enter")
                || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
            System.out.println(
                    "Enter Client ID and Secret from https://console.developers.google.com/project/_/apiui/credential "
                            + "into src/main/resources/client_secrets.json");
            System.exit(1);
        }

        // This creates the credentials datastore at ~/.oauth-credentials/${credentialDatastore}
        FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(new File(System.getProperty("user.home") + "/" + ".oauth-credentials"));
        DataStore<StoredCredential> datastore = fileDataStoreFactory.getDataStore(credentialDatastore);

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, scopes).setCredentialDataStore(datastore)
                .build();

        // Build the local server and bind it to port 8080
        LocalServerReceiver localReceiver = new LocalServerReceiver.Builder().setPort(8080).build();

        // Authorize.
        return new AuthorizationCodeInstalledApp(flow, localReceiver).authorize("user");
    }
	
	public static Collection<String> getResources(
	        final Pattern pattern){
	        final ArrayList<String> retval = new ArrayList<String>();
	        final String classPath = System.getProperty("java.class.path", ".");
	        final String[] classPathElements = classPath.split(System.getProperty("path.separator"));
	        for(final String element : classPathElements){
	            retval.addAll(getResources(element, pattern));
	        }
	        return retval;
	    }
	private static Collection<String> getResources(
	        final String element,
	        final Pattern pattern){
	        final ArrayList<String> retval = new ArrayList<String>();
	        final File file = new File(element);
	        if(file.isDirectory()){
	            retval.addAll(getResourcesFromDirectory(file, pattern));
	        } else{
	            retval.addAll(getResourcesFromJarFile(file, pattern));
	        }
	        return retval;
	    }

	 private static Collection<String> getResourcesFromJarFile(
		        final File file,
		        final Pattern pattern){
		        final ArrayList<String> retval = new ArrayList<String>();
		        ZipFile zf;
		        try{
		            zf = new ZipFile(file);
		        } catch(final ZipException e){
		            throw new Error(e);
		        } catch(final IOException e){
		            throw new Error(e);
		        }
		        final Enumeration e = zf.entries();
		        while(e.hasMoreElements()){
		            final ZipEntry ze = (ZipEntry) e.nextElement();
		            final String fileName = ze.getName();
		            final boolean accept = pattern.matcher(fileName).matches();
		            if(accept){
		                retval.add(fileName);
		            }
		        }
		        try{
		            zf.close();
		        } catch(final IOException e1){
		            throw new Error(e1);
		        }
		        return retval;
		    }

		    private static Collection<String> getResourcesFromDirectory(
		        final File directory,
		        final Pattern pattern){
		        final ArrayList<String> retval = new ArrayList<String>();
		        final File[] fileList = directory.listFiles();
		        for(final File file : fileList){
		            if(file.isDirectory()){
		                retval.addAll(getResourcesFromDirectory(file, pattern));
		            } else{
		                try{
		                    final String fileName = file.getCanonicalPath();
		                    final boolean accept = pattern.matcher(fileName).matches();
		                    if(accept){
		                        retval.add(fileName);
		                    }
		                } catch(final IOException e){
		                    throw new Error(e);
		                }
		            }
		        }
		        return retval;
		    }
	
}
