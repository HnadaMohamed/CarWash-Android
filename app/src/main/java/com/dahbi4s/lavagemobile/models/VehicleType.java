package com.dahbi4s.lavagemobile.models;

import com.dahbi4s.lavagemobile.R;

public  class  VehicleType {

    public enum Type {
        SEDAN,
        SUV4x4,
        TAPI,
        CANAPE

    };

    private long Id;
    Type Type;
    private Integer flag;

    public VehicleType() {

    }
    private VehicleType(long id, Type type, Integer flag) {
        Id = id;
        Type = type;
        this.flag = flag;
    }

    public long getId() {
        return Id;
    }

    public VehicleType.Type getType() {
        return Type;
    }

    public Integer getFlag() {
        return flag;
    }

    public  VehicleType[] getVehicleTypes(){

        return new VehicleType[]{
                new VehicleType(0, Type.SEDAN, R.drawable.type1),
                new VehicleType(1, Type.SUV4x4, R.drawable.type2),
                new VehicleType(1, Type.TAPI, R.drawable.type4),
                new VehicleType(2, Type.CANAPE, R.drawable.type3)
        };
    }

}
