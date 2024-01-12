//package com.campusrecruitmentsystem.database;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//
//import com.example.fayyaz_ahmad.publicfacilitationapplicationkpk.seter_geter.SeterGeter;
//
//
//public class Querries {
//
//    public static long insertIntoLocalDBVoilation(Context context) {
//        long _id_pk = 0;
//        try {
//            SQLiteDatabase db = DataBaseSQlite.connectToDb(context);
//            ContentValues values = new ContentValues();
//            values.put("completeAppData", SeterGeter.getCompleteAppDataJsonString());
//            values.put("image", SeterGeter.getImageName());
//            values.put("image_three", SeterGeter.getImageName1());
//            values.put("status", "0");
//            values.put("imei", SeterGeter.getImei());
//            values.put("date_time", SeterGeter.getDateTime());
//            values.put("_id_pk", SeterGeter.getRowID());
//            values.put("version", SeterGeter.getVersion());
//            values.put("main_id", SeterGeter.getId_live_db());
//            values.put("owner_name", SeterGeter.getOwner_name());
//            values.put("godown_address", SeterGeter.getGodown_address());
//            values.put("godown_name", SeterGeter.getGodown_name());
//            values.put("district_name", SeterGeter.getDistrict());
//            String str = SeterGeter.getId_live_db();
//            String[] token = str.split("-",0);
//            if (token.length == 2){
//                values.put("survey_status", "yellow");
//            }else {
//                values.put("survey_status", "green");
//            }
//
//
//            try {
//                _id_pk = db.insertWithOnConflict("data", null, values, SQLiteDatabase.CONFLICT_REPLACE);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//        return _id_pk;
//    }
//
//}