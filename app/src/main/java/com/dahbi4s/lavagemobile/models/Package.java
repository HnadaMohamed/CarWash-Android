package com.dahbi4s.lavagemobile.models;

import com.dahbi4s.lavagemobile.R;

public class Package {


    public enum PACK {
        SIMPLE,
        SPECIAL,
        COMPLET,
        SEC,
        VAPEUR
    };


    private long Id;
    private PACK Name;
    private Integer Flag;
    private long Price;
    private VehicleType vehicleType=new VehicleType();

    public long getId() {
        return Id;
    }

    public PACK getName() {
        return Name;
    }

    public Integer getFlag() {
        return Flag;
    }

    public long getPrice() {
        return Price;
    }

    public Package(){

    }

    public Package(long Id, PACK Name, Integer Flag, long Price){
        this.Id=Id;
        this.Name=Name;
        this.Flag=Flag;
        this.Price=Price;
    }

    public  Package[] getPackages(VehicleType.Type type ){

        if(type ==      vehicleType.Type.SEDAN){
            return new Package[]{
                    new Package(0,PACK.SIMPLE, R.drawable.number1,45),
                    new Package(0,PACK.SPECIAL, R.drawable.number2,80),
                    new Package(0,PACK.COMPLET, R.drawable.number3,300),
                    new Package(0,PACK.SEC, R.drawable.number4,50),
                    new Package(0,PACK.VAPEUR, R.drawable.number5,170)
            };
        }else if(type == vehicleType.Type.SUV4x4){
            return new Package[]{
                    new Package(0,PACK.SIMPLE, R.drawable.number1,60),
                    new Package(0,PACK.SPECIAL, R.drawable.number2,100),
                    new Package(0,PACK.COMPLET, R.drawable.number3,400),
                    new Package(0,PACK.SEC, R.drawable.number4,70),
                    new Package(0,PACK.VAPEUR, R.drawable.number5,200)
            };
        }else if(type == vehicleType.Type.TAPI){
            return new Package[]{
                    new Package(0,PACK.SIMPLE, R.drawable.number1,10),
                    new Package(0,PACK.VAPEUR, R.drawable.number2,15)
            };
        }else if(type == vehicleType.Type.CANAPE){
            return new Package[]{
                    new Package(0,PACK.VAPEUR, R.drawable.number1,150)
            };
        }


        return null;
    }
}
