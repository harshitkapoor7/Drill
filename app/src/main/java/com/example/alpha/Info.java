package com.example.alpha;

import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.TreeMap;

public class Info {


    public static String[] squads(String json) throws JSONException {
        String[] squad_team1 = new String[40];
        TreeMap<Integer, String> playerid = new TreeMap<>();
        JSONObject obj = new JSONObject(json);
        JSONArray players = obj.getJSONArray("players");
        int l = players.length();
        for (int i = 0; i < l; i++) {
            JSONObject p = players.getJSONObject(i);
            String name = p.getString("f_name");
            Integer id = Integer.parseInt(p.getString("id"));
            playerid.put(id, name);
        }
        int j = 0;
        JSONObject t1 = obj.getJSONObject("team1");
        squad_team1[j++] = t1.getString("name");
        JSONArray sq1 = t1.getJSONArray("squad");
        int l1 = sq1.length();
        for (int i = 0; i < l1; i++)
            squad_team1[j++] = playerid.get(sq1.getInt(i));
        squad_team1[j++] = "SPLIT";
        JSONObject t2 = obj.getJSONObject("team2");
        squad_team1[j++] = t2.getString("name");
        JSONArray sq2 = t2.getJSONArray("squad");
        int l2 = sq2.length();
        for (int i = 0; i < l2; i++)
            squad_team1[j++] = playerid.get(sq2.getInt(i));
        return squad_team1;
    }
}
