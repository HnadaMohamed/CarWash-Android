package com.dahbi4s.lavagemobile.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import com.dahbi4s.lavagemobile.Adapter.CarTypeAdapter;
import com.dahbi4s.lavagemobile.Adapter.PackageAdapter;
import com.dahbi4s.lavagemobile.R;
import com.dahbi4s.lavagemobile.activities.BookingStep;
import com.dahbi4s.lavagemobile.models.Booking;
import com.dahbi4s.lavagemobile.models.Package;
import com.dahbi4s.lavagemobile.models.VehicleType;

public class WashInformation extends Fragment {
    Context context;
    VehicleType vehicleType = new VehicleType();
    Package packages=new Package();
    Package[] packageliste ;
    VehicleType[] vehicleTypeliste ;
    Spinner packageSpinner;
    Spinner vihecleSpinner;
    PackageAdapter customAdapterPackage;

    public WashInformation() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_wash_information, container, false);
        context = container.getContext();

         vihecleSpinner = (Spinner) root.findViewById(R.id.vihecleSpinner);
         packageSpinner = (Spinner) root.findViewById(R.id.packageSpinner);

        packageliste=packages.getPackages(vehicleType.getVehicleTypes()[0].getType());
        vehicleTypeliste= vehicleType.getVehicleTypes();

        CarTypeAdapter customAdapterVehicle=new CarTypeAdapter(context,vehicleTypeliste);
        customAdapterPackage=new PackageAdapter(context, packageliste);


        vihecleSpinner.setAdapter(customAdapterVehicle);
        vihecleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BookingStep.order.setCarType(vehicleTypeliste[position].getType().toString());

                packageliste=packages.getPackages(vehicleType.getVehicleTypes()[position].getType());

                customAdapterPackage=new PackageAdapter(context, packageliste);
                packageSpinner.setAdapter(customAdapterPackage);


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        packageSpinner.setAdapter(customAdapterPackage);
        packageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BookingStep.order.setLavageType(packageliste[position].getName());
                BookingStep.order.setPrice(packageliste[position].getPrice()+"");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
        return root;
    }

}
