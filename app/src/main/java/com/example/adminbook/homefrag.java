package com.example.adminbook;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.SYSTEM_HEALTH_SERVICE;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminbook.Model.Notificationdata;
import com.example.adminbook.Model.PushNotification;
import com.example.adminbook.Model.pdfclass;
import com.example.adminbook.constqnt.NotificationApi;
import com.example.adminbook.constqnt.constant;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class homefrag extends Fragment {
View mview ;
TextView pdfbook ;
FirebaseAuth mauth ;
DatabaseReference datadb ,usref;
StorageReference imgref ;
Button trelecharger ;
ImageView imagebook ;
Uri ImageFIle ;
Uri y;
Uri url ;
public
EditText namebook , autheur , descriptiion , category ;
ProgressDialog   progressDialog ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.fragment_homefrag, container, false);
        init();
        progressDialog = new ProgressDialog(getActivity());

// VARIABLE DE L OUVERTURE DE Galerie

        ActivityResultLauncher<Intent> openGalleryResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // Here, no request code
                            Intent data = result.getData();
                            ImageFIle = data.getData();
                            imagebook.setImageURI(ImageFIle);
                        }
                    }
                });

        // LA FIN DE CE VARIABLE

// L ' OUVERTURE DE GALERIE
        imagebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGalleryResult.launch(opengallery());
            }
        });


// LA FIN DE L ' OUVERTURE DE GALERIE

        trelecharger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectFiles();
            }
        });


        return mview ;
    }


    public void init(){
        trelecharger = mview.findViewById(R.id.telecharger);

        imgref = FirebaseStorage.getInstance().getReference().child("bookpdf");
        namebook = mview.findViewById(R.id.namebook);
        autheur = mview.findViewById(R.id.auteurdebook);
        descriptiion = mview.findViewById(R.id.descripdebook);
        imagebook = mview.findViewById(R.id.imagebook);
        category = mview.findViewById(R.id.category);
         mauth = FirebaseAuth.getInstance();
        FirebaseMessaging.getInstance().subscribeToTopic(constant.TOPIC);
        usref = FirebaseDatabase.getInstance(getString(R.string.Db_url)).getReference().child("users");


    }


    public Intent opengallery(){
        Intent i  = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        return i;
    }




    private void selectFiles() {
        Intent  intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select PDF Files... "),1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data!= null && data.getData()!=null){
            UplaodFiles(data.getData());
        }
    }

    private void UplaodFiles(Uri data) {

       progressDialog.setTitle("Book PDF UPLOADING...");
       progressDialog.show();
       imgref.child("Uplaods/"+System.currentTimeMillis()+".pdf")
               .putFile(data)
               .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                   @Override
                   public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                       Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                       while (!uriTask.isComplete());
                       url = uriTask.getResult();
// hadi tkhes img


                       imgref.child("image/"+System.currentTimeMillis()+"pdf")
                                       .putFile(ImageFIle)
                                               .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                   @Override
                                                   public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                   Task<Uri> uriTask1 = taskSnapshot.getStorage().getDownloadUrl();
                                                   while (!uriTask1.isComplete());
                                                   y = uriTask1.getResult();
                                                       datadb = FirebaseDatabase.getInstance(getString(R.string.Db_url)).getReference().child("bookpdf");
                                                       datadb = datadb.push();
                                                   String bookid = datadb.getKey();
                                                       pdfclass pdfclass = new pdfclass(namebook.getText().toString(),autheur.getText().toString()
                                                               ,descriptiion.getText().toString(),url.toString(),y.toString(),category.getText().toString(),bookid);
                                                       datadb.setValue(pdfclass);
                                                       progressDialog.dismiss();
                                                       Toast.makeText(getActivity(), "File Uplaod", Toast.LENGTH_SHORT).show();

                                                       LinearLayout linearLayout = new LinearLayout(getActivity());
                                                       linearLayout.setOrientation(LinearLayout.VERTICAL);
                                                       EditText notification_title = new EditText(getContext());
                                                       notification_title.setHint("Notifiaction title");
                                                       EditText notification_message = new EditText(getContext());
                                                       notification_message.setHint("Notification message");
                                                       linearLayout.addView(notification_title);
                                                       linearLayout.addView(notification_message);
                                                      sendnotification();


                                                   }
                                               });
                       // hena tkhlas
                   }
               }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                   @Override
                   public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                       double progress = (100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                       progressDialog.setMessage("Uploaded"+" "+(int)progress+"%");


                   }
               });


    }



public void sendnotification(){
        usref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                  Retrofit retrofit = new Retrofit.Builder()
                          .baseUrl(constant.BASE_URL)
                          .addConverterFactory(GsonConverterFactory.create())
                          .build();
                  NotificationApi notificationApi = retrofit.create(NotificationApi.class);
                  Notificationdata content = new Notificationdata(namebook.getText().toString(),"New Book");
                  PushNotification data = new PushNotification(content, dataSnapshot.child("token").getValue().toString());
                  Call<ResponseBody> response = notificationApi.postNotification(data);
                  response.enqueue(new Callback<ResponseBody>() {
                      @Override
                      public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                          if(response.isSuccessful()){
                              Toast.makeText(getContext(), "Notification sent successfully", Toast.LENGTH_SHORT).show();
                          }else{
                              Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                          }
                      }

                      @Override
                      public void onFailure(Call<ResponseBody> call, Throwable t) {
                          Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                      }
                  });
              }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



}



}