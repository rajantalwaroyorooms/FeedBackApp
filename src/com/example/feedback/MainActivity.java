package com.example.feedback;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

public class MainActivity extends ActionBarActivity implements RatingBar.OnRatingBarChangeListener {
	private RatingBar roadDirections, cleanlinessAndHygiene,
	                  inRoomServices, staffAttitude, foodServed,
	                  overAllExperience;
	private Button btnSubmit, btnComment;
	private EditText etComments;
	private String roadDirectionsRating, cleanlinessAndHygieneRating, inRoomServicesRating,
	            staffAttitudeRating, foodServedRatings, overAllExperienceRatings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		roadDirections = (RatingBar)findViewById(R.id.r_bar_road_directions);
		cleanlinessAndHygiene = (RatingBar)findViewById(R.id.r_bar_cleanliness_and_hygiene);
		inRoomServices = (RatingBar)findViewById(R.id.r_bar_in_room_service);
		staffAttitude = (RatingBar)findViewById(R.id.r_bar_staff_attitude);
		foodServed = (RatingBar)findViewById(R.id.r_bar_food_served);
		overAllExperience = (RatingBar)findViewById(R.id.r_bar_overall_experience);
		initRatingListeners();
		init();
	}
	
	private void init() {
		btnSubmit = (Button) findViewById(R.id.btn_submitt);
		btnComment = (Button) findViewById(R.id.btn_comment);
		etComments = (EditText) findViewById(R.id.et_comments);
	}
	private void initRatingListeners(){
		roadDirections.setOnRatingBarChangeListener(this);
		cleanlinessAndHygiene.setOnRatingBarChangeListener(this);
		inRoomServices.setOnRatingBarChangeListener(this);
		staffAttitude.setOnRatingBarChangeListener(this);
		foodServed.setOnRatingBarChangeListener(this);
		overAllExperience.setOnRatingBarChangeListener(this);
	}

   public void commentButtonOnClick(View view){
	   // hide the comment button and show the edit text
	   btnComment.setVisibility(View.GONE);
	   etComments.setVisibility(View.VISIBLE);
   }
   
   public void submitButtonClicked(View view){
	   // checks the network before making the call
	   createJsonForFeedBack();
   }
   
   private void createJsonForFeedBack(){
   	JSONObject jsonObject = new JSONObject();
   	try {
			jsonObject.put("bookingId", "BookingId");
			jsonObject.put("authToken", "AouthToken");
			jsonObject.put("phoneNumber", "phoneNumber");
			
			jsonObject.put("RoadDirectionsRating",roadDirectionsRating);
			jsonObject.put("cleanlinessRating", cleanlinessAndHygieneRating);
			jsonObject.put("inroomserviceRating", inRoomServicesRating);
			jsonObject.put("staffAttitudeRating", staffAttitudeRating);
			jsonObject.put("foodservedRating", foodServedRatings);
			jsonObject.put("overAllExperienceRating", overAllExperienceRatings);
			
			jsonObject.put("comments", etComments.getText().toString());
		} catch (JSONException e) {
			Log.e("navya","navya error:::::"+ e);
			e.printStackTrace();
		}
   	
   	if(showDialogForNoNetwork()){
   	sendJson(jsonObject);
   	}
   	
   }
   
   private void sendJson(JSONObject jsonObject) {
   	new FeedBackAsyncTask(MainActivity.this, jsonObject).execute("Hi");
   }

	@Override
	public void onRatingChanged(RatingBar ratingBar, float rating,
			boolean fromUser) {
		switch (ratingBar.getId()) {
		case R.id.r_bar_road_directions:
			roadDirectionsRating = String.valueOf(rating);
			break;	
		case R.id.r_bar_cleanliness_and_hygiene:
			cleanlinessAndHygieneRating = String.valueOf(rating);
			break;
		case R.id.r_bar_in_room_service:
			inRoomServicesRating = String.valueOf(rating);
			break;
		case R.id.r_bar_staff_attitude:
			staffAttitudeRating = String.valueOf(rating);
			break;
		case R.id.r_bar_food_served:
			foodServedRatings = String.valueOf(rating);
			break;
		case R.id.r_bar_overall_experience:
			overAllExperienceRatings = String.valueOf(rating);
			break;
		default:
			break;
		}	
	}

	/*    returns  true if network is available 
    else  returns false and shows a no network dialog*/
    private boolean showDialogForNoNetwork() {
    	if(haveNetworkConnection()){
    		return true;
    	} else {
    		showNoNetworkDialog();
    	   	return false;
    	}
    }
    
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
    
    private void showNoNetworkDialog(){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle(getResources().getString(R.string.error_dialog_title));
    	builder.setMessage(getResources().getString(R.string.no_network_dialog_message));
    	builder.setCancelable(false);
    	builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
    	AlertDialog dialog = builder.create();
    	dialog.show();
    	
    }
}
