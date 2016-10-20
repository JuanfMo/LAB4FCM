package lab4fcm.gr07.compumovil.udea.edu.co.lab4fcm;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by jufem on 17/10/2016.
 */
public class ChatRoom extends AppCompatActivity {

    private Button btnEnviar;
    private EditText textMsj;
    private TextView chatConversacion;

    private String nombreUsuario, nombreSala;
    private DatabaseReference root;
    private String key_temp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        btnEnviar =(Button) findViewById(R.id.btn_enviar);
        textMsj = (EditText) findViewById(R.id.txt_enviar);
        chatConversacion = (TextView) findViewById(R.id.textView);

        nombreUsuario = getIntent().getExtras().get("Usuario").toString();
        nombreSala = getIntent().getExtras().get("Nombre Sala").toString();
        setTitle(" Sala = " + nombreSala);

        root = FirebaseDatabase.getInstance().getReference().child(nombreSala);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Map<String,Object> map = new HashMap<String, Object>();
                key_temp = root.push().getKey();
                root.updateChildren(map);

                DatabaseReference message_root = root.child(key_temp);
                Map<String,Object> map2 = new HashMap<String, Object>();
                map2.put("Nombre", nombreUsuario);
                map2.put("Mensaje", textMsj.getText().toString());

                message_root.updateChildren(map2);
                textMsj.setText("");

            }
        });

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                conversacionChat(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                conversacionChat(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private String chat_msg, chat_user;
    private void conversacionChat(DataSnapshot dataSnapshot){

        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()){

            chat_msg = (String) ((DataSnapshot)i.next()).getValue();
            chat_user = (String) ((DataSnapshot)i.next()).getValue();

            chatConversacion.append(chat_user +" : "+ chat_msg + " \n");
        }
    }

}
