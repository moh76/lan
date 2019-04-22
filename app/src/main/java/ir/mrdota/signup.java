package ir.mrdota;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class signup  extends AppCompatActivity {

    Button signup , login ;
    public static final String URI_SHOW_PARAMS = "http://mrdota.ir/lan/saveName.php";
    RequestQueue requestQueue;
    EditText user , phnum,pass , passag , mail ;
    ProgressDialog pgdialog ;
    String Err= "" ;
    boolean oklist = false ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        user = (EditText)  findViewById(R.id.user);
        phnum = (EditText)  findViewById(R.id.phnum);
        pass = (EditText)  findViewById(R.id.pass);
        passag = (EditText)  findViewById(R.id.passag);
        mail = (EditText)  findViewById(R.id.mail);
        signup = (Button) findViewById(R.id.signup);

        login = (Button) findViewById(R.id.login);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                checklist();

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signup.this,login.class));
                finish();
            }
        });
        pgdialog = new ProgressDialog(this);
        pgdialog.setTitle("loading ...");

    }

    public void addtoserver(){
        // Sus Spw Smail ===> postto server
        //
        requestQueue = Volley.newRequestQueue(this);
        StringRequest req = new StringRequest(Request.Method.POST,
                URI_SHOW_PARAMS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

//                        Toast.makeText(signup.this, ""+s, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject ss = new JSONObject(s);
                            if (ss.getString("bol").contains("true")){
                                startActivity(new Intent(signup.this, login.class));
                                finish();
                            }else if(ss.getString("chun").contains("unex")){
                                Err = "user name exist";

                            }
                            else if(ss.getString("cht").contains("tex")){
                                Err = "phone number name exist";

                            }
                            else if(ss.getString("chm").contains("mex")){
                                Err = "mail name exist";
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        pgdialog.dismiss();
                        Toast.makeText(signup.this, ""+Err, Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                pgdialog.dismiss();

            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ptype", "add");
                params.put("user_name", user.getText().toString().trim());
                params.put("pass",pass.getText().toString().trim());
                params.put("type", "1");
                params.put("region", "null");
                params.put("mail", mail.getText().toString().trim());
                params.put("tell", phnum.getText().toString().trim());
//                params.put("ex_date",new Date().toString().trim());


                return params;
            }

        };
        pgdialog.show();
        requestQueue.add(req);

    }
    public void checklist(){
        chempty();
        if (oklist == false) {
            Toast.makeText(this, "" + Err, Toast.LENGTH_SHORT).show();
            return;
        }
        chchar();
        if (oklist == false) {
            Toast.makeText(this, "" + Err, Toast.LENGTH_SHORT).show();
            return;
        }
        chphone();
        if (oklist == false) {
            Toast.makeText(this, "" + Err, Toast.LENGTH_SHORT).show();
            return;
        }
        chmail();
        if (oklist == false) {
            Toast.makeText(this, "" + Err, Toast.LENGTH_SHORT).show();
            return;
        }
        chpass(passag ,pass);
        if (oklist == false) {
            Toast.makeText(this, "" + Err, Toast.LENGTH_SHORT).show();
            return;
        }
        if (oklist == false) {
            Toast.makeText(this, "" + Err, Toast.LENGTH_SHORT).show();
            return;
        }
        Err = "not err";
        oklist =true ;
        chserver();
//        Toast.makeText(this, "err = "+Err + "oklist = "+oklist, Toast.LENGTH_SHORT).show();

    }
    public void chpass(EditText passag ,EditText pass){
        if (!pass.getText().toString().equals(passag.getText().toString())){
            oklist = false ;
            Err = "pass != pass rep";
            return;
        }


    }
    public void chmail( ){

        if (!mail.getText().toString().trim().contains("@")){
            Err = "Err mailformat char (not found @) ";
            oklist = false ;
            return ;
        }if (!mail.getText().toString().trim().contains(".")){
            Err = "Err mailformat char (not found .) ";
            oklist = false ;
            return ;
        }else oklist = true ;

    }
    public void chempty(){
        if (user.getText().toString().trim().isEmpty()){
            Err = "empty user name";
            oklist = false ;
            return ;
        }if (phnum.getText().toString().trim().isEmpty()){
            Err = "empty phone number";
            oklist = false ;
            return ;
        }if (mail.getText().toString().trim().isEmpty()){
            Err = "empty mail ";
            oklist = false ;
            return ;
        }if (pass.getText().toString().trim().isEmpty()){
            Err = "empty pass ";
            oklist = false ;
            return ;
        }if (passag.getText().toString().trim().isEmpty()){
            Err = "empty pass rep ";
            oklist = false ;
            return ;
        }else oklist = true ;

    }
    public void chchar(){
        if (user.getText().toString().trim().contains("'")){
            Err = "Err char (') ";
            oklist = false ;
            return ;
        }if (mail.getText().toString().trim().contains("'")){
            Err = "Err char (') ";
            oklist = false ;
            return ;
        }if (pass.getText().toString().trim().contains("'")){
            Err = "Err char (') ";
            oklist = false ;
            return ;
        }if (passag.getText().toString().trim().contains("'")){
            Err = "Err char (') ";
            oklist = false ;
            return ;
    }else oklist = true ;
        //nim space
//        if (user.getText().toString().trim().contains("'")){
//            Err = "Err char (') ";
//            oklist = false ;
//            return ;
//        }if (mail.getText().toString().trim().contains("'")){
//            Err = "Err char (') ";
//            oklist = false ;
//            return ;
//        }if (pass.getText().toString().trim().contains("'")){
//            Err = "Err char (') ";
//            oklist = false ;
//            return ;
//        }if (passag.getText().toString().trim().contains("'")){
//            Err = "Err char (') ";
//            oklist = false ;
//            return ;
//        }
    }
    public  void chphone(){
        if (phnum.getText().toString().length() != 11){
            Err = "Err phone form ";
            oklist = false ;
            return ;
        }else oklist = true ;
    }
    public void chserver(){
        if (Err.contains("not err") && oklist==true) {
            addtoserver();

        }
    }
}
