package lab4fcm.gr07.compumovil.udea.edu.co.lab4fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by jufem on 18/10/2016.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh(){

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG, "Token Refrescado: " + refreshedToken);

    }

    private void sendRegistrationToServer(String token) {
      //este metodo para almacenar el token en el servidor
    }
}
