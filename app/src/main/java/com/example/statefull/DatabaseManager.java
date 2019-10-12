package com.example.statefull;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Master";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME1 = "User";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_USERNAME = "name";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";

    private static final String TABLE_NAME2 = "Data";
    private static final String COLUMN_USERID = "Userid";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_VALUE = "mood";

    private static final String TABLE_NAME3 = "Variables";
    private static final String COLUMN_LOGGED = "logged";
    private static final String COLUMN_LOG_TIME = "log_time";
    private static final String COLUMN_LAST_LOG = "last_log";

    private static final String REMINDER_TABLE = "Reminder";
    private static final String COLUMN_REMINDER_TIME = "time";
    private static final String COLUMN_MERIDIAN = "meridian";
    private static final String COLUMN_REMINDER_MILLI = "millis";
    private static final String COLUMN_ISACTIVE = "active";

    private static final String HAPPINESS_TABLE = "Happy";
    private static final String COLUMN_HTIME = "Time";
    private static final String COLUMN_HVALUE = "Value";

    private static final String MOOD_TABLE = "Emotions";
    private static final String COLUMN_REGISTER_TIME = "Time";
    private static final String COLUMN_MOODVALUE = "mood";

    private static final String THOUGHTS_TABLE = "THOUGHTS";
    private static final String COLUMN_THOUGHT = "THOUGHT";
    private static final String COLUMN_DAY_ID = "DAY_ID";
    private static final String COLUMN_THOUGHT_COLOR = "COLOR";

    private static final String DAY_TABLE = "DAYS";
    private static final String COLUMN_DATE = "Date";
    private static final String COLUMN_THOUGHT_TIME = "TIME";

    private static final String ENERGY_TABLE = "Energy";
    private static final String COLUMN__REGISTER_TIME = "Time";
    private static final String COLUMN_ENERGYVALUE = "Energy";

    private static final String CHARACT_TABLE = "Characteristics";
    private static final String COLUMN_SATISFACTION = "satisfaction";
    private static final String COLUMN_CONFIDENCE = "confidence";
    private static final String COLUMN_ENTHUSIASM = "enthusiasm";
    private static final String COLUMN_AMBITION = "ambition";
    private static final String COLUMN_ENERGY = "energy";

    static DatabaseManager databaseManager = null;

    DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        databaseManager = this;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = String.format("CREATE TABLE %s (\n    %s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n    %s varchar(200) NOT NULL,\n    %s varchar(200) NOT NULL,\n    %s varchar(200) NOT NULL\n);",
                TABLE_NAME1, COLUMN_ID, COLUMN_USERNAME, COLUMN_PASSWORD, COLUMN_EMAIL);

        String sql1 = String.format("CREATE TABLE %s(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n %sInteger, %sDATE, %sINTEGER);",
                TABLE_NAME2, COLUMN_USERID, COLUMN_TIME, COLUMN_VALUE);

        String sql2 = String.format("CREATE TABLE %s(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s INTEGER, %s Boolean, %s DATE, %s DATE);",
                TABLE_NAME3, COLUMN_USERID, COLUMN_LOGGED, COLUMN_LOG_TIME, COLUMN_LAST_LOG);

        String sql3 = String.format("CREATE TABLE %s(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s Text,%s FLOAT , %s Text,  %s Integer);",
                REMINDER_TABLE, COLUMN_REMINDER_TIME, COLUMN_REMINDER_MILLI, COLUMN_MERIDIAN, COLUMN_ISACTIVE);

        String sql4 = String.format("CREATE TABLE %s(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s Date, %s Integer);",
                HAPPINESS_TABLE, COLUMN_HTIME, COLUMN_HVALUE);

        String sql7 = String.format("CREATE TABLE %s(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s TEXT);",
                DAY_TABLE, COLUMN_DATE);

        String sql6 = String.format("CREATE TABLE %s(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s TEXT,%s TEXT,%s TEXT ,%s INTEGER, FOREIGN KEY(%s) REFERENCES %s (%s));",
                THOUGHTS_TABLE, COLUMN_THOUGHT, COLUMN_THOUGHT_COLOR, COLUMN_THOUGHT_TIME, COLUMN_DAY_ID, COLUMN_DAY_ID, DAY_TABLE, "_id");

        String sql8 = String.format("CREATE TABLE %s(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s LONG, %s Integer,%s Integer, FOREIGN KEY(%s) REFERENCES %s (%s));",
                ENERGY_TABLE, COLUMN__REGISTER_TIME, COLUMN_ENERGYVALUE, COLUMN_DAY_ID, COLUMN_DAY_ID, DAY_TABLE, "_id");

        String sql5 = String.format("CREATE TABLE %s(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s LONG, %s Integer,%s Integer, FOREIGN KEY(%s) REFERENCES %s (%s));",
                MOOD_TABLE, COLUMN_REGISTER_TIME, COLUMN_MOODVALUE, COLUMN_DAY_ID, COLUMN_DAY_ID, DAY_TABLE, "_id");


        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.execSQL(sql1);
        sqLiteDatabase.execSQL(sql2);
        sqLiteDatabase.execSQL(sql3);
        sqLiteDatabase.execSQL(sql4);
        sqLiteDatabase.execSQL(sql5);
        sqLiteDatabase.execSQL(sql7);
        sqLiteDatabase.execSQL(sql6);
        sqLiteDatabase.execSQL(sql8);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        /*
        String sql = String.format("DROP TABLE IF EXISTS %s;", TABLE_NAME1);
        sqLiteDatabase.execSQL(sql);
        sql = String.format("DROP TABLE IF EXISTS %s;", TABLE_NAME2);
        sqLiteDatabase.execSQL(sql);
        sql = String.format("DROP TABLE IF EXISTS %s;", TABLE_NAME3);
        sqLiteDatabase.execSQL(sql);
        sql = String.format("DROP TABLE IF EXISTS %s;", REMINDER_TABLE);
        sqLiteDatabase.execSQL(sql);
        sql = String.format("DROP TABLE IF EXISTS %s;", HAPPINESS_TABLE);
        sqLiteDatabase.execSQL(sql);
        sql = String.format("DROP TABLE IF EXISTS %s;", MOOD_TABLE);
        sqLiteDatabase.execSQL(sql);
        sql = String.format("DROP TABLE IF EXISTS %s;", THOUGHTS_TABLE);
        sqLiteDatabase.execSQL(sql);
        sql = String.format("DROP TABLE IF EXISTS %s;", DAY_TABLE);
        sqLiteDatabase.execSQL(sql);
        */
        if (i < 3) {
            String sql9 = String.format("CREATE TABLE %s(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,%s LONG, %s Integer,%s Integer,%s Integer,%s Integer,%s Integer,%s Integer);",
                    CHARACT_TABLE, COLUMN_TIME, COLUMN_SATISFACTION, COLUMN_CONFIDENCE, COLUMN_ENTHUSIASM, COLUMN_AMBITION, COLUMN_ENERGY, COLUMN_DAY_ID);
            sqLiteDatabase.execSQL(sql9);
        }

    }

    boolean addUser(String name, String password, String mail) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, name);
        contentValues.put(COLUMN_PASSWORD, password);
        contentValues.put(COLUMN_EMAIL, mail);
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(TABLE_NAME1, null, contentValues) != -1;
    }

    boolean userExist(String name) {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME1, null);
        if (cursor.moveToFirst()) {
            do {
                String x = cursor.getString(1);
                if (name.equals(x)) {
                    cursor.close();
                    return true;
                }
            } while (cursor.moveToNext());
        }
        return false;
    }

    void loginSuccess(int userid) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, 1);
        contentValues.put(COLUMN_USERID, userid);
        contentValues.put(COLUMN_LOGGED, true);
        Cursor cursor = this.getWritableDatabase().rawQuery("Select * from " + TABLE_NAME3, null);
        if (cursor.getCount() == 1) {
            this.getWritableDatabase().update(TABLE_NAME3, contentValues, COLUMN_ID + "=?", new String[]{"1"});
            Log.d("Updated", "loginSuccess");
        } else if (cursor.getCount() == 0) {
            this.getWritableDatabase().insert(TABLE_NAME3, null, contentValues);
            Log.d("Inserted ", "loginSuccess");
        }
        cursor.close();
    }

    void logout() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, 1);
        contentValues.put(COLUMN_LOGGED, false);
        this.getWritableDatabase().update(TABLE_NAME3, contentValues, COLUMN_ID + "=?", new String[]{"1"});
    }

    boolean logged() {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME3, null);
        if (cursor.moveToFirst()) {
            String x = cursor.getString(cursor.getColumnIndex(COLUMN_LOGGED));
            Log.d("Log value: ", x + "00");
            cursor.close();
            if (x.equals("1")) {
                Log.d("returning ", "true");
                return true;
            } else {
                Log.d("returning", "false");
                return false;
            }
        } else {
            Log.d("returning ", "out else");
            return false;
        }
    }

    int isUser(String name, String pass) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME1, null);
        if (cursor.moveToFirst()) {
            do {
                String mail_ = cursor.getString(3);
                String pass_ = cursor.getString(2);
                if (mail_.equals(name) && pass_.equals(pass)) {
                    int id = Integer.parseInt(cursor.getString(0));
                    cursor.close();
                    return id;
                }
            } while (cursor.moveToNext());
        }
        return -1;
    }

    void fabAddReminder(int h, int m, String merd) {
        ContentValues contentValues = new ContentValues();
        String time = h + ":" + m;
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        //date.setHours(h + (merd.equals("AM") ? 0 : 12));
        date.setHours(h);
        date.setMinutes(m);
        date.setSeconds(0);

        contentValues.put(COLUMN_REMINDER_TIME, time);
        contentValues.put(COLUMN_MERIDIAN, merd);
        contentValues.put(COLUMN_REMINDER_MILLI, date.getTime());
        contentValues.put(COLUMN_ISACTIVE, "1");

        getWritableDatabase().insert(REMINDER_TABLE, null, contentValues);
        Cursor g = getReadableDatabase().rawQuery("Select * from " + REMINDER_TABLE, null);
        if (g.moveToFirst()) {
            do {
                Log.d(COLUMN_ISACTIVE, g.getString(g.getColumnIndex(COLUMN_ISACTIVE)));
            } while (g.moveToNext());
        }

    }

    public void editReminder(int id, int hourOfDay, int minute, String meridian) {
        ContentValues contentValues = new ContentValues();
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        //date.setHours(h + (merd.equals("AM") ? 0 : 12));
        date.setHours(hourOfDay);
        date.setMinutes(minute);
        date.setSeconds(0);

        String time = hourOfDay + ":" + minute;
        contentValues.put("_id", id);
        contentValues.put(COLUMN_REGISTER_TIME, time);
        contentValues.put(COLUMN_MERIDIAN, meridian);
        contentValues.put(COLUMN_ISACTIVE, "1");
        contentValues.put(COLUMN_REMINDER_MILLI, date.getTime());
        getWritableDatabase().update(REMINDER_TABLE, contentValues, "_id = ? ", new String[]{Integer.toString(id)});
    }

    public String getLatestThought(int dayId) {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT " + COLUMN_THOUGHT + " FROM " + THOUGHTS_TABLE + " where " + COLUMN_DAY_ID + " = " + dayId + " ORDER BY _id DESC", null);
        if (cursor.moveToFirst()) {
            String j = cursor.getString(0);
            cursor.close();
            return j;
        } else return "";
    }



    void negatereminder(int p) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("Select " + COLUMN_ISACTIVE + " from " + REMINDER_TABLE + " where _id=" + p, null);
        boolean current = true;
        if (c.moveToFirst()) {
            String x = c.getString(c.getColumnIndex(COLUMN_ISACTIVE));
            Log.d("X=", x);
            current = x.equals("1");
        }
        Log.d("Current2", Boolean.toString(current));
        boolean state = !current;
        SQLiteDatabase dp = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ISACTIVE, state);
        dp.update(REMINDER_TABLE, contentValues, "_id = ?", new String[]{Integer.toString(p)});
    }

    void deletereminder(int p) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(REMINDER_TABLE, "_id = ?", new String[]{Integer.toString(p)});
    }

    int getReminderTableSize() {
        SQLiteDatabase db = getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, REMINDER_TABLE);
    }

    int getDayTableSize() {
        SQLiteDatabase db = getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, DAY_TABLE);
    }

    List<Reminder> getReminders() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + REMINDER_TABLE + " ORDER BY " + COLUMN_REMINDER_MILLI + " ASC", null);
        List<Reminder> reminders = new ArrayList<Reminder>();
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String hm = cursor.getString(cursor.getColumnIndex(COLUMN_REMINDER_TIME));
                String merd = cursor.getString(cursor.getColumnIndex(COLUMN_MERIDIAN));

                boolean isactive = cursor.getString(cursor.getColumnIndex(COLUMN_ISACTIVE)).equals("1");
                Log.d("Logd IsActive", "" + isactive);

                String[] t = hm.split(":");
                int h = Integer.parseInt(t[0]);
                if (merd.equals("Pm")) {
                    h = h - 12;
                }
                int m = Integer.parseInt(t[1]);

                Reminder temp = new Reminder(h, m, merd, isactive);
                temp._id = id;
                reminders.add(temp);
            } while (cursor.moveToNext());
        }
        return reminders;
    }

    void moodEntry(long time, int val, int day_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_REGISTER_TIME, time);
        contentValues.put(COLUMN_MOODVALUE, val);
        contentValues.put(COLUMN_DAY_ID, day_id);
        getWritableDatabase().insert(MOOD_TABLE, null, contentValues);
    }

    void charactEntry(long time, PersonalityParams p, int day_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TIME, time);
        contentValues.put(COLUMN_SATISFACTION, p.satisfaction);
        contentValues.put(COLUMN_CONFIDENCE, p.confidence);
        contentValues.put(COLUMN_ENTHUSIASM, p.enthusiasm);
        contentValues.put(COLUMN_AMBITION, p.ambition);
        contentValues.put(COLUMN_ENERGY, p.energy);
        contentValues.put(COLUMN_DAY_ID, day_id);
        getWritableDatabase().insert(CHARACT_TABLE, null, contentValues);
    }

    TreeMap<Long, Integer> getMoodEntries(int day_id) {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + MOOD_TABLE + " WHERE " + COLUMN_DAY_ID + " = " + day_id, null);
        TreeMap<Long, Integer> entries = new TreeMap<>();
        if (cursor.moveToFirst()) {
            do {
                long t = Long.parseLong(cursor.getString(cursor.getColumnIndex(COLUMN_REGISTER_TIME)));
                int v = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_MOODVALUE)));

                entries.put(t, v);
            } while (cursor.moveToNext());
            cursor.close();
        }
        Log.d("Entries Lenght", "" + entries.size());
        return entries;
    }

    int addToday() {
        Calendar calendar = Calendar.getInstance();
        Date day = calendar.getTime();
        int date = day.getDate();
        int month = day.getMonth();
        int year = day.getYear();
        String current_date = date + " " + month + " " + (1900 + year);
        String query = String.format("SELECT * FROM %s WHERE %s = \"%s\"", DAY_TABLE, COLUMN_DATE, current_date);
        Cursor cursor = getWritableDatabase().rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(0);
                cursor.close();
                return Integer.parseInt(id);
            } while (cursor.moveToNext());
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_DATE, current_date);
            getWritableDatabase().insert(DAY_TABLE, null, contentValues);
            return addToday();
        }
    }

    List<DayData> getDays() {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + DAY_TABLE + " ORDER BY _id DESC ", null);
        ArrayList<DayData> days = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String date = cursor.getString(1);
                DayData dayData = new DayData(id, date);
                days.add(dayData);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return days;
    }

    float getDayMiniReport(int id) {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT " + COLUMN_MOODVALUE + " FROM " + MOOD_TABLE + " WHERE " + COLUMN_DAY_ID + " = " + id, null);
        float count = 0;
        float npos = 0;
        if (cursor.moveToFirst()) {
            do {
                int val = 16 - Integer.parseInt(cursor.getString(0));
                if (val > 10) {
                    npos++;
                }
                count++;
            } while (cursor.moveToNext());
            cursor.close();
        }
        Log.d("nC", count + " " + npos);
        return npos / count;
    }

    List<String> getDay(int id) {
        Cursor cursor = getWritableDatabase().rawQuery("SELECT " + COLUMN_DATE + " FROM " + DAY_TABLE + " WHERE _id = " + id, null);
        if (cursor.moveToFirst()) {
            String date = cursor.getString(0);
            String[] components = date.split(" ");
            List<String> retval = new ArrayList<>();
            retval.add(components[0]);
            retval.add(components[1]);
            return retval;
        } else {
            return null;
        }
    }

    int getToDay(int day_id) {

        Calendar calendar = Calendar.getInstance();
        Date day = calendar.getTime();
        int date = day.getDate();
        int month = day.getMonth();
        int year = day.getYear();
        String current_date = date + " " + month + " " + (1900 + year);
        String query = String.format("SELECT * FROM %s WHERE %s = \"%s\"", DAY_TABLE, COLUMN_DATE, current_date);
        Cursor cursor = getWritableDatabase().rawQuery(query, null);
        if (cursor.moveToFirst()) {
            int id = Integer.parseInt(cursor.getString(0));
            cursor.close();
            return id;
        } else {
            cursor.close();
            return -1;
        }

        /*else{
            String query = String.format("SELECT * FROM %s WHERE %s = %s", DAY_TABLE, "_id", ""+day_id);
            Cursor cursor = getWritableDatabase().rawQuery(query, null);
            if (cursor.moveToFirst()) {
                int id = Integer.parseInt(cursor.getString(0));
                cursor.close();
                return id;
            } else {
                cursor.close();
                return -1;
            }
        }*/

    }

    void addThought(int k, String body, int color) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(COLUMN_THOUGHT, body);
        contentValue.put(COLUMN_THOUGHT_COLOR, color);
        contentValue.put(COLUMN_THOUGHT_TIME, Long.toString(Calendar.getInstance().getTimeInMillis()));

        contentValue.put(COLUMN_DAY_ID, k);
        getWritableDatabase().insert(THOUGHTS_TABLE, null, contentValue);
    }

    void justForTest() {
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + THOUGHTS_TABLE, null);
        if (cursor.moveToFirst()) {
            do {
                Log.d("Thought(" + cursor.getString(0) + ")", cursor.getString(1));
            } while (cursor.moveToNext());
            cursor.close();
        }
    }

    TreeMap<String, List<Integer>> getDailyAverage() {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + MOOD_TABLE, null);
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        TreeMap<String, List<Integer>> perDay = new TreeMap<String, List<Integer>>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (cursor.moveToFirst()) {
            do {
                long msecs = Long.parseLong(cursor.getString(cursor.getColumnIndex(COLUMN_REGISTER_TIME)));
                int v = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_MOODVALUE)));
                date.setTime(msecs);
                String datetime = formatter.format(date);
                if (perDay.containsKey(datetime)) {
                    perDay.get(datetime).add(v);
                } else {
                    perDay.put(datetime, new ArrayList<Integer>());
                    perDay.get(datetime).add(v);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        for (String key : perDay.keySet()) {
            List<Integer> values = perDay.get(key);
            int sum = 0;
            for (int i = 0; i < values.size(); i++) {
                sum += values.get(i);
            }
            int avg = sum / values.size();
            values.clear();
            values.add(avg);
        }
        return perDay;
    }

    List<Thought> getThoughts(int day_id) {
        if (day_id != -1) {
            List<Thought> thoughts = new ArrayList<Thought>();
            Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + THOUGHTS_TABLE + " WHERE " + COLUMN_DAY_ID + " = " + day_id + " ORDER BY _id DESC", null);
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(0);
                    String body = cursor.getString(cursor.getColumnIndex(COLUMN_THOUGHT));
                    int color = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_THOUGHT_COLOR)));
                    Calendar calendar = Calendar.getInstance();
                    String time = cursor.getString(cursor.getColumnIndex(COLUMN_THOUGHT_TIME));
                    Date date = calendar.getTime();
                    date.setTime(Long.parseLong(time));
                    int hour = date.getHours();
                    String k = "AM";
                    if (hour > 12) {
                        hour = hour - 12;
                        k = "PM";
                    }
                    String u = hour + ":" + date.getMinutes() + " " + k;
                    Thought thought = new Thought(Integer.parseInt(id), body);
                    thought.thoughtColor = color;
                    thought.setText(body);
                    thought.setTime(u);
                    thoughts.add(thought);
                } while (cursor.moveToNext());
            }
            return thoughts;
        } else {
            return new ArrayList<Thought>();
        }
    }

    void removeThought(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(THOUGHTS_TABLE, "_id = ?", new String[]{Integer.toString(id)});
    }

    int getThoughtsCount(int day_id) {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + THOUGHTS_TABLE + " WHERE " + COLUMN_DAY_ID + " = " + day_id, null);
        return cursor.getCount();
    }

    //Insight Calculator
    double getConfidenceDeviation() {
        String qury = "SELECT " + COLUMN_CONFIDENCE + "," + COLUMN_DAY_ID + "," + COLUMN_TIME + " from " + CHARACT_TABLE;
        Cursor cursor = getReadableDatabase().rawQuery(qury, null);
        TreeMap<Integer, List<Entry>> map = new TreeMap<>();
        if (cursor.moveToFirst()) {
            do {
                int key = Integer.parseInt(cursor.getString(1));
                int val = Integer.parseInt(cursor.getString(0));
                long time = Long.parseLong(cursor.getString(2));
                if (map.containsKey(key)) {
                    List<Entry> list = map.get(key);
                    Entry e = new Entry();
                    e.time = time;
                    e.val = val;
                    list.add(e);
                    map.put(key, list);
                } else {
                    List<Entry> list = new ArrayList<>();
                    Entry e = new Entry();
                    e.time = time;
                    e.val = val;
                    list.add(e);
                    map.put(key, list);
                }
            } while (cursor.moveToNext());
        }
        double total_diff = 0;
        double total_count = 0;
        Log.d("map", "" + map.keySet().size());
        for (int x : map.keySet()) {
            List<Entry> list = map.get(x);
            double diff = 0;
            int count = 0;
            if (list.size() > 1) {
                for (int i = 1; i < list.size(); i++) {
                    long dx = list.get(i).time - list.get(i - 1).time;
                    dx = dx / (1000 * 60);
                    int dy = Math.abs(list.get(i).val - list.get(i - 1).val);
                    diff += dy / (double) dx;
                    count++;
                }
                Log.d("Diff", diff + " " + count);
                diff = diff / count;
                total_diff = total_diff + diff;
                total_count++;
            }
        }
        return total_diff / total_count;
    }

    double getEnergyDeviation() {
        String qury = "SELECT " + COLUMN_ENERGY + "," + COLUMN_DAY_ID + "," + COLUMN_TIME + " from " + CHARACT_TABLE;
        Cursor cursor = getReadableDatabase().rawQuery(qury, null);
        TreeMap<Integer, List<Entry>> map = new TreeMap<>();
        if (cursor.moveToFirst()) {
            do {
                int key = Integer.parseInt(cursor.getString(1));
                int val = Integer.parseInt(cursor.getString(0));
                long time = Long.parseLong(cursor.getString(2));
                if (map.containsKey(key)) {
                    List<Entry> list = map.get(key);
                    Entry e = new Entry();
                    e.time = time;
                    e.val = val;
                    list.add(e);
                    map.put(key, list);
                } else {
                    List<Entry> list = new ArrayList<>();
                    Entry e = new Entry();
                    e.time = time;
                    e.val = val;
                    list.add(e);
                    map.put(key, list);
                }
            } while (cursor.moveToNext());
        }
        double total_diff = 0;
        double total_count = 0;
        Log.d("map", "" + map.keySet().size());
        for (int x : map.keySet()) {
            List<Entry> list = map.get(x);
            double diff = 0;
            int count = 0;
            if (list.size() > 1) {
                for (int i = 1; i < list.size(); i++) {
                    long dx = list.get(i).time - list.get(i - 1).time;
                    dx = dx / (1000 * 60);
                    int dy = Math.abs(list.get(i).val - list.get(i - 1).val);
                    diff += dy / (double) dx;
                    count++;
                }
                Log.d("Diff", diff + " " + count);
                diff = diff / count;
                total_diff = total_diff + diff;
                total_count++;
            }
        }
        return total_diff / total_count;
    }

    double getSatisfactionDeviation() {
        String qury = "SELECT " + COLUMN_SATISFACTION + "," + COLUMN_DAY_ID + "," + COLUMN_TIME + " from " + CHARACT_TABLE;
        Cursor cursor = getReadableDatabase().rawQuery(qury, null);
        TreeMap<Integer, List<Entry>> map = new TreeMap<>();
        if (cursor.moveToFirst()) {
            do {
                int key = Integer.parseInt(cursor.getString(1));
                int val = Integer.parseInt(cursor.getString(0));
                long time = Long.parseLong(cursor.getString(2));
                if (map.containsKey(key)) {
                    List<Entry> list = map.get(key);
                    Entry e = new Entry();
                    e.time = time;
                    e.val = val;
                    list.add(e);
                    map.put(key, list);
                } else {
                    List<Entry> list = new ArrayList<>();
                    Entry e = new Entry();
                    e.time = time;
                    e.val = val;
                    list.add(e);
                    map.put(key, list);
                }
            } while (cursor.moveToNext());
        }
        double total_diff = 0;
        double total_count = 0;
        Log.d("map", "" + map.keySet().size());
        for (int x : map.keySet()) {
            List<Entry> list = map.get(x);
            double diff = 0;
            int count = 0;
            if (list.size() > 1) {
                for (int i = 1; i < list.size(); i++) {
                    long dx = list.get(i).time - list.get(i - 1).time;
                    dx = dx / (1000 * 60);
                    int dy = Math.abs(list.get(i).val - list.get(i - 1).val);
                    diff += dy / (double) dx;
                    count++;
                }
                Log.d("Diff", diff + " " + count);
                diff = diff / count;
                total_diff = total_diff + diff;
                total_count++;
            }
        }
        return total_diff / total_count;
    }

    double getEnthusiasmDeviation() {
        String qury = "SELECT " + COLUMN_ENTHUSIASM + "," + COLUMN_DAY_ID + "," + COLUMN_TIME + " from " + CHARACT_TABLE;
        Cursor cursor = getReadableDatabase().rawQuery(qury, null);
        TreeMap<Integer, List<Entry>> map = new TreeMap<>();
        if (cursor.moveToFirst()) {
            do {
                int key = Integer.parseInt(cursor.getString(1));
                int val = Integer.parseInt(cursor.getString(0));
                long time = Long.parseLong(cursor.getString(2));
                if (map.containsKey(key)) {
                    List<Entry> list = map.get(key);
                    Entry e = new Entry();
                    e.time = time;
                    e.val = val;
                    list.add(e);
                    map.put(key, list);
                } else {
                    List<Entry> list = new ArrayList<>();
                    Entry e = new Entry();
                    e.time = time;
                    e.val = val;
                    list.add(e);
                    map.put(key, list);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        double total_diff = 0;
        double total_count = 0;
        Log.d("map", "" + map.keySet().size());
        for (int x : map.keySet()) {
            List<Entry> list = map.get(x);
            double diff = 0;
            int count = 0;
            if (list.size() > 1) {
                for (int i = 1; i < list.size(); i++) {
                    long dx = list.get(i).time - list.get(i - 1).time;
                    dx = dx / (1000 * 60);
                    int dy = Math.abs(list.get(i).val - list.get(i - 1).val);
                    diff += dy / (double) dx;
                    count++;
                }
                Log.d("Diff", diff + " " + count);
                diff = diff / count;
                total_diff = total_diff + diff;
                total_count++;
            }
        }
        return total_diff / total_count;
    }

    List<Integer> getThoughtTypes() {
        String q = "SELECT " + COLUMN_THOUGHT_COLOR + " FROM " + THOUGHTS_TABLE;
        Cursor cur = getReadableDatabase().rawQuery(q, null);
        int pos = 0, neg = 0, act = 0, ide = 0;
        if (cur.moveToFirst()) {
            do {
                int color = Integer.parseInt(cur.getString(0));
                switch (color) {
                    case 0:
                        act++;
                        break;
                    case 1:
                        neg++;
                        break;
                    case 4:
                        pos++;
                        break;
                    case 5:
                        ide++;
                        break;
                }
            } while (cur.moveToNext());
            cur.close();
        }
        List<Integer> returner = new ArrayList<>();
        returner.add(act);
        returner.add(neg);
        returner.add(pos);
        returner.add(ide);
        return returner;
    }

    List<List<Integer>> getDatForCharacteristics(int id) {
        List<List<Integer>> result = new ArrayList<>();
        String query = String.format("SELECT %s, %s, %s, %s, %s FROM %s WHERE %s = %s".toUpperCase(Locale.getDefault()),
                COLUMN_CONFIDENCE, COLUMN_SATISFACTION, COLUMN_ENTHUSIASM, COLUMN_AMBITION, COLUMN_ENERGY, CHARACT_TABLE, COLUMN_DAY_ID, id);
        Cursor cursor = getReadableDatabase().rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                List<Integer> entry = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    entry.add(Integer.parseInt(cursor.getString(i)));
                }
                result.add(entry);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return result;
    }

    class Entry {
        int val;
        long time;
    }
}