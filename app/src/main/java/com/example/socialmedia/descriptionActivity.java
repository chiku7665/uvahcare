package com.example.socialmedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.widget.Toast.LENGTH_SHORT;
import static androidx.core.content.ContextCompat.getSystemService;

public class descriptionActivity extends AppCompatActivity {

    RatingBar ratingBardescription,ratingpop;
    Button btnratedesc,btnsubmitrate;
    CardView popupcard;
    float avrating;
    int ch=0,ch1=0;
    private String TAG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        ratingBardescription=findViewById(R.id.ratingdescription);
        btnratedesc=findViewById(R.id.ratedesc);
        btnsubmitrate=findViewById(R.id.submitrating);
        popupcard=findViewById(R.id.popupcard);
        ratingpop=findViewById(R.id.ratingpop);



        TextView txt= (TextView) findViewById(R.id.title12);
        Intent in = getIntent();
        txt.setText(in.getStringExtra("title"));
        ImageView imj=(ImageView) findViewById(R.id.img12);
        String url=in.getStringExtra("im");
        TextView txt11= (TextView) findViewById(R.id.desc12);
        txt11.setText(in.getStringExtra("Ddesc"));
        String postkey=in.getStringExtra("postkey");
        FirebaseUser cuser= FirebaseAuth.getInstance().getCurrentUser();
        String cid=cuser.getUid();
       // CardView cardforpopup=findViewById(R.id.cardforpopup);
        Glide.with(imj.getContext()).load(url).into(imj);
      //  float fratindesc=ratingBardescription.getRating();
        DatabaseReference refrate= FirebaseDatabase.getInstance().getReference("rating");

        try{
        refrate.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(ch==0){
                if(snapshot.hasChild(postkey)) {

                        int n = (int) snapshot.child(postkey).getChildrenCount() - 1;
                        // if(snapshot.child(postkey).child("sum").get)
                        //try{
                        // Double sum = (Double) snapshot.child(postkey).child("sum").getValue();
                     //   double sum = Double.parseDouble(snapshot.child(postkey).child("sum").getValue());
                  //  a.getClass().getName()

                    if (snapshot.child(postkey).child("sum").getValue().getClass().getName().equals("java.lang.Double")) {
                        double sum = (double) snapshot.child(postkey).child("sum").getValue();
                        Double avrate = sum / n;
                        float avr = avrate.floatValue();
                        ratingBardescription.setRating(avr);

                    }
                    else{
                        Long sum = (Long) snapshot.child(postkey).child("sum").getValue();
                         double d = sum.doubleValue();
                        Double avrate = d/ n;

                        float avr = avrate.floatValue();
                        ratingBardescription.setRating(avr);


                    }





                  //}
                  /* catch (Exception e){
                       Long sum = (Long)snapshot.child(postkey).child("sum").getValue();
                       Double avrate=((double) sum)/n;

                       float avr=avrate.floatValue();
                       ratingBardescription.setRating(avr);
                   }*/

                }
                else{
                    // holder.displayrate.setText(0);

                }
            }
            ch=1;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        }catch (Exception e){
            Log.e(TAG,e.getMessage());

        }
        ratingBardescription.setEnabled(false);
        //  rateuser.child(postkey).child(cid).setValue(fratindesc);
        refrate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(postkey).hasChild(cid)){
                    btnratedesc.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnratedesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // btnsubmitrate.setVisibility(View.VISIBLE);
                popupcard.setVisibility(View.VISIBLE);

            }
        });


        btnsubmitrate.setOnClickListener(new View.OnClickListener() {

          //   try {
            @Override
            public void onClick(View v) {
                ch1=0;
                ch=0;
                float frate=(float)ratingpop.getRating();



              refrate.addValueEventListener(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot snapshot) {
                      if(ch1==0){

                     if (snapshot.hasChild(postkey)) {

                         int n = (int) snapshot.child(postkey).getChildrenCount();
                         if (snapshot.child(postkey).child("sum").getValue().getClass().getName().equals("java.lang.Double")){

                             double sum = (double) snapshot.child(postkey).child("sum").getValue();
                         // float sum = (float) snapshot.child(postkey).child("sum").getValue();
                         float fsum = (float)sum;

                         fsum = fsum + frate;
                         float avrate =((float) fsum )/ n;
                         ratingBardescription.setRating(avrate);
                         refrate.child(postkey).child(cid).setValue(frate);
                         refrate.child(postkey).child("sum").setValue(fsum);
                         }
                         else {
                             Long sum1 = (Long) snapshot.child(postkey).child("sum").getValue();
                             // float sum = (float) snapshot.child(postkey).child("sum").getValue();
                             double sum=sum1.doubleValue();
                             float fsum = (float)sum;

                             fsum = fsum + frate;
                             float avrate =((float) fsum )/ n;
                             ratingBardescription.setRating(avrate);
                             refrate.child(postkey).child(cid).setValue(frate);
                             refrate.child(postkey).child("sum").setValue(fsum);
                         }

                     }
                     else {
                       //  float sum = frate;
                         //float avrate = frate;
                         try {
                             ratingBardescription.setRating(frate);
                             refrate.child(postkey).child(cid).setValue(frate);
                             refrate.child(postkey).child("sum").setValue(frate);
                         }catch (Exception e){}


                     }

                  }
                  ch1=1;
                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError error) {

                  }
              });

              popupcard.setVisibility(View.GONE);
            }
        }

             );


    }



}