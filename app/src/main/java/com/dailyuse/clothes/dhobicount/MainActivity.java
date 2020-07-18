package com.dailyuse.clothes.dhobicount;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import adapter.DateAndCountAdapter;
import adapter.HashtagImageAdapter;
import adapter.HashtagTextAdapter;
import databaseHandler.DBHandler;
import object.DateAndCount;

import static android.os.Environment.DIRECTORY_PICTURES;
import static android.view.View.GONE;
import static android.view.View.SCALE_X;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    RecyclerView mRecyclerViewHashImage;
    RecyclerView.LayoutManager mGridLayoutManager;
    RecyclerView.Adapter mAdapterHashImage;
    ArrayList<String> alName;
    List<String> mFilePaths = new ArrayList<>();
    Animation slideUp;
    Animation slideDown;

    DBHandler dbHandler = new DBHandler(this);

    LinearLayout layout;
    RelativeLayout add_data_layout;
    EditText add_data_text;
//    FloatingActionButton fab;
    TextView datePresent;
    Button add_data_btn;
    MenuItem fab;
    String checkData;

    Uri imageUri;
    ImageView cameraClick;
    TextView cameraDetails;
    InputMethodManager imm;
    int i=1;

    ArrayList<DateAndCount> dateAndCountList = new ArrayList<>();
    DateAndCount dateAndCountData, dateAndCount = new DateAndCount();
    RecyclerView.Adapter mDateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = (LinearLayout)findViewById(R.id.layout_add_btn);
        datePresent = (TextView) findViewById(R.id.layout_add_btn_date_present);
        add_data_layout = (RelativeLayout) findViewById(R.id.add_specific_layout);
        add_data_text = (EditText) findViewById(R.id.cloth_specific_count);
        add_data_btn = (Button) findViewById(R.id.cloth_specific_btn);
//        cameraClick = (ImageView) findViewById(R.id.camera_click);
//        cameraDetails = (TextView) findViewById(R.id.camera_data);

        int count = dbHandler.getCount();
        Toast.makeText(getApplicationContext(), count + " items", Toast.LENGTH_SHORT).show();

        dateAndCountData = new DateAndCount();
//        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
//                new IntentFilter("custom-message"));

        LocalBroadcastManager.getInstance(this).registerReceiver(mDateReciever,
                new IntentFilter("date-selected"));

        LocalBroadcastManager.getInstance(this).registerReceiver(mDataClothReciever,
                new IntentFilter("message-data"));

        dateAndCountData = new DateAndCount();
        allDateAndCountData(totalDateAndCountDataList());
        presentDate();
        animDeclare();
        hashtagListData();
        onClickListeners();
    }

    private ArrayList<DateAndCount> totalDateAndCountDataList() {
        dateAndCountList.clear();
        return dbHandler.ShowDateAndCount();

    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            Log.v("dateAndCountData", intent.getIntExtra("Shirts", 0) + " " + intent.getIntExtra("Pants", 0) +
                    " " + intent.getIntExtra("Inners", 0) + " " );
            dateAndCountData = new DateAndCount();
            dateAndCountData.setShirts(intent.getIntExtra("Shirts", 0));
            dateAndCountData.setPants(intent.getIntExtra("Pants", 0));
            dateAndCountData.setInners(intent.getIntExtra("Inners",0));


        }
    };

    public BroadcastReceiver mDataClothReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            Log.v("message-data", intent.getStringExtra("command") + " ");
            checkData = intent.getStringExtra("command");
            hashtagImageData(checkData);
            add_data_layout.setAnimation(slideDown);
            add_data_layout.setVisibility(View.VISIBLE);
//            add_data_text.requestFocus();
//            imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.showSoftInput(add_data_text, InputMethodManager.SHOW_IMPLICIT);

            add_data_btn.setOnClickListener(MainActivity.this);
        }
    };

    public BroadcastReceiver mDateReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            Log.v("date-selected", intent.getIntExtra("date1", 0) + " " + intent.getIntExtra("date2", 0) +
                    " " + intent.getIntExtra("date3", 0) + " " );
            int month = intent.getIntExtra("date2", 0) + 1;

            String date = intent.getIntExtra("date3", 0) + "-" + month + "-" + intent.getIntExtra("date1", 0);
            datePresent.setText(date);
        }
    };

    private void allDateAndCountData(ArrayList<object.DateAndCount> dateAndCountList) {
        // Calling the RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.date_and_count_recycler);
        mRecyclerView.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mDateAdapter = new DateAndCountAdapter(MainActivity.this, dateAndCountList);
        mRecyclerView.setAdapter(mDateAdapter);
    }


    public List<String> addDataToImages(final String CheckData) {
        //select files
        final ArrayList<File> mFiles = new ArrayList<File>();
        File mDirectory;
        File[] folderPath = Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES + "/Count").listFiles();
        Log.v("fileData", folderPath + " ");
//        mDirectory = new File(folderPath);

//            ExtensionFilenameFilter filter = new ExtensionFilenameFilter(acceptedFileExtensions);
        List<String> mFilepaths = new ArrayList<>();
        // Get the files in the directory
//            File[] files = mDirectory.listFiles(".jpg");
       /* File[] files = mDirectory.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                name.toLowerCase().endsWith(".jpg") && name.toLowerCase().contains(CheckData)
                    mFiles.add(new File(dir, name));
                    mFilepaths.add(dir + "/" + name);

                    Log.v("fileData", dir + "/" + name);
                    return true;


            }});*/

       for( File f : folderPath){
           if(f.isFile() && f.getPath().endsWith(".jpg") && f.getPath().contains(CheckData)){
               mFilepaths.add(f.getName());
           }
       }
        /*String paths[] =
                mDirectory.list(
                        new FilenameFilter() {
                            public boolean accept(File dir, String name) {
//                name.toLowerCase().endsWith(".jpg") && name.toLowerCase().contains(CheckData)
//                mFiles.add(new File(dir, name));
//                mFilepaths.add(dir + "/" + name);
//
                Log.v("fileData", dir + "/" + name);

                return (name.toLowerCase().endsWith(".jpg") && name.toLowerCase().contains(CheckData));

            }});
        mFilepaths = new ArrayList<>(Arrays.asList(paths));*/

//        Toast.makeText(getApplicationContext(), "No. of Filrs = "+files.length ,Toast.LENGTH_SHORT).show();
        return mFilepaths;
    }


    public void hashtagListData(){
        alName = new ArrayList<>(Arrays.asList("Shirts", "Pants", "Inners"));
        
        // Calling the RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.hashtag_text_recycler);
        mRecyclerView.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new HashtagTextAdapter(MainActivity.this, alName);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void hashtagImageData(String checkData){
        mFilePaths = addDataToImages(checkData);

        // Calling the RecyclerView
        mRecyclerViewHashImage = (RecyclerView) findViewById(R.id.cloth_specific_recycler);
        mRecyclerViewHashImage.setHasFixedSize(true);

        // The number of Columns
//        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mGridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false);
        mRecyclerViewHashImage.setLayoutManager(mGridLayoutManager);

        mAdapterHashImage = new HashtagImageAdapter(MainActivity.this, mFilePaths);
        mRecyclerViewHashImage.setAdapter(mAdapterHashImage);
    }

    private void presentDate() {
        String pattern = "dd-MM-yyyy";
        String dateInString =new SimpleDateFormat(pattern).format(new Date());
        datePresent.setText(dateInString);
        datePresent.setOnClickListener(this);
    }

    private void animDeclare() {
        slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
    }

    private void onClickListeners() {
//        fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(this);
        Button save = (Button) findViewById(R.id.layout_add_btn_save);
        save.setOnClickListener(this);
        Button hide = (Button) findViewById(R.id.hide_id);
        hide.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mDateAdapter.notifyDataSetChanged();
//        dateAndCountData = new DateAndCount();
        switch (view.getId()){
//            case R.id.fab:
//
//                break;
            case R.id.layout_add_btn_save:
                if(layout.getVisibility()==View.VISIBLE){
                    layout.startAnimation(slideDown);
                    layout.setVisibility(View.INVISIBLE);
//                    fab.setVisibility(View.VISIBLE);
                    fab.setVisible(true);

                    dateAndCount.setDate(datePresent.getText().toString());
                    Log.v("FinalData", dateAndCount.getDate() +" "+
                            dateAndCount.getShirts() + " " + dateAndCount.getPants()
                            + dateAndCount.getInners());
                     dbHandler.AddDateAndCount(dateAndCount);
                    allDateAndCountData(totalDateAndCountDataList());
                }
                break;
            case R.id.cloth_specific_btn:
                
                if(checkData == "Shirts"){
//                    Log.v("getMet", Integer.parseInt(viewHolder.add_data_text.getText().toString()) + " ");
                    dateAndCount.setShirts(Integer.parseInt(add_data_text.getText().toString()));
                }
                if(checkData == "Pants"){
                    dateAndCount.setPants(Integer.parseInt(add_data_text.getText().toString()));
                }
                if(checkData == "Inners"){
                    dateAndCount.setInners(Integer.parseInt(add_data_text.getText().toString()));
                }
                if(dateAndCount.getShirts()!=0 &&dateAndCount.getPants()!=0 && dateAndCount.getInners()!=0){
//                    Intent intent = new Intent("custom-message");
//                    intent.putExtra("Shirts",dateAndCount.getShirts());
//                    intent.putExtra("Pants",dateAndCount.getPants());
//                    intent.putExtra("Inners",dateAndCount.getInners());
//                    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                }
                add_data_layout.setAnimation(slideUp);
                add_data_layout.setVisibility(GONE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                add_data_text.setText("");
                break;
            case R.id.hide_id:
                if(layout.getVisibility()==View.VISIBLE){
                    layout.startAnimation(slideDown);
                    layout.setVisibility(View.INVISIBLE);
                    fab.setVisible(true);
                }
                break;
            case R.id.layout_add_btn_date_present:
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
        }
    }

    public interface ItemClickListener {

        void onClick(View view, int position, boolean isLongClick);
        void onClickData(View view, int position, boolean isLongClick);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        fab = menu.findItem(R.id.fab);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fab:
                // TODO settings - that can show the choose between number and images for all the three categories and set INNERS default as text
                // TODO imagestorage - change to 'data' file inside appdata
                if(layout.getVisibility()==View.INVISIBLE){
                    layout.startAnimation(slideUp);
                    layout.setVisibility(View.VISIBLE);
                    fab.setVisible(false);
                }
                return true;

            case R.id.camera:
                alertDialog();

                break;


        }
        return super.onOptionsItemSelected(item);
    }

    private void startCamera(String s) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File f = createExternalStorageDirectory("Count");
        File photo = creatingAMediaFile(f, s);
//                File photo = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES) + "/Count",mediaFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        MainActivity.this.startActivityForResult(intent, 100);
//                startActivity(new Intent(getApplicationContext(), Feedback.class));
    }

    @Nullable
    private File creatingAMediaFile(File mediaStorageDir, String dataStamp) {
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ dataStamp + "_"+ timeStamp +".jpg");
        return mediaFile;

    }

    private File createExternalStorageDirectory(String folder_main) {
        File f = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES), folder_main);
        if (!f.exists()) {
            f.mkdirs();
        }
        return f;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageUri;
                    MainActivity.this.getContentResolver().notifyChange(selectedImage, null);
                    ContentResolver cr = MainActivity.this.getContentResolver();
                    Bitmap bitmap;
//                    cameraDetails.setText(selectedImage.getPath());

                    alertDialog();
                    /*try {
                        bitmap = android.provider.MediaStore.Images.Media
                                .getBitmap(cr, selectedImage);
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);

//                        cameraClick.setImageBitmap(bitmap);
//                        cameraClick.setScaleType(ImageView.ScaleType.CENTER);
//                        cameraDetails.setText(selectedImage.getEncodedPath());
//                        Toast.makeText(MainActivity.this, selectedImage.toString(),
//                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Camera", e.toString());
                    }*/
                }
        }
    }

    public void alertDialog(){
        final String[] catStamp = {"Shirts"};
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getSupportActionBar().getThemedContext());
        alertDialog.setTitle("Select the photo Category")
                .setItems(R.array.category_selection, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        String[] catSelection = getResources().getStringArray(R.array.category_selection);
                        catStamp[0] = catSelection[which];
                        startCamera(catStamp[0]);
                    }
                })
                .show();
    }

}
