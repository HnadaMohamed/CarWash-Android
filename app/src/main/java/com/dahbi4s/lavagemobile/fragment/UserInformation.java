package com.dahbi4s.lavagemobile.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.dahbi4s.lavagemobile.R;

import com.dahbi4s.lavagemobile.activities.BookingStep;
import com.dahbi4s.lavagemobile.models.Coordinate;
import com.dahbi4s.lavagemobile.models.Tools;
import com.google.gson.Gson;
import com.mapbox.api.geocoding.v5.GeocodingCriteria;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.plugins.places.picker.PlacePicker;
import com.mapbox.mapboxsdk.plugins.places.picker.model.PlacePickerOptions;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class UserInformation extends Fragment {
    static final int REQUEST_CODE = 5678;
    EditText selectedLocationTextView ;

    MaterialRippleLayout btn;
    Context context;
    View root;
    EditText name;
    EditText phone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         context = container.getContext();

        Mapbox.getInstance(context,"pk.eyJ1IjoiaG5hZGEiLCJhIjoiY2s1anV5a3J5MDZ2dzNscWlmMXYzaGw3ZSJ9.3ZUT88xIW9LBHBY7rF8mQA");

        root = inflater.inflate(R.layout.fragment_user_information, container, false);
        btn = root.findViewById(R.id.lyt_next);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPickerActivity();
            }
        });
        selectedLocationTextView =(EditText) root.findViewById(R.id.address);
        //selectedLocationTextView.setVisibility(View.INVISIBLE);

        ((EditText) root.findViewById(R.id.name)).setText(BookingStep.order.getName());
        ((EditText) root.findViewById(R.id.phone)).setText(BookingStep.order.getPhone());
        if(BookingStep.order.getAddress()!=null)
            ((EditText) root.findViewById(R.id.address)).setText(BookingStep.order.getAddress().getType());

        name = root.findViewById(R.id.name);
        phone = root.findViewById(R.id.phone);

        name.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                BookingStep.order.setName(name.getText().toString());
            }
        });

    phone.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                BookingStep.order.setPhone(phone.getText().toString());
            }
        });
        return root;
    }

    private void goToPickerActivity() {
        startActivityForResult(
                new PlacePicker.IntentBuilder()
                        .accessToken("pk.eyJ1IjoiaG5hZGEiLCJhIjoiY2s1anV5a3J5MDZ2dzNscWlmMXYzaGw3ZSJ9.3ZUT88xIW9LBHBY7rF8mQA")
                        .placeOptions(PlacePickerOptions.builder()
                                .statingCameraPosition(new CameraPosition.Builder()

                                        .target(new LatLng(35.5784500, -5.3683700)).zoom(12).build())
                                .build()
                        )
                        .build(getActivity()), REQUEST_CODE);
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {

        } else if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // Retrieve the information from the selected location's CarmenFeature
            CarmenFeature carmenFeature = PlacePicker.getPlace(data);

            // Set the TextView text to the entire CarmenFeature. The CarmenFeature
            // also be parsed through to grab and display certain information such as
            // its placeName, text, or coordinates.
            if (carmenFeature != null && selectedLocationTextView!=null) {
               // selectedLocationTextView.setVisibility(View.VISIBLE);
                Gson gson = new Gson();
                Coordinate object = gson.fromJson(carmenFeature.geometry().toJson(), Coordinate.class);
                object.setType(carmenFeature.placeName());
                BookingStep.order.setAddress(object);
                selectedLocationTextView.setText(carmenFeature.placeName());

            }
        }
    }

}
