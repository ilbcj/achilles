package com.achilles.controller.conveter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.achilles.model.Battle;
import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;

public class BattleInfoConverter extends DefaultTypeConverter {
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object convertValue(Map context, Object value, Class toType) {
		if(value == null) {
			return null;
		}
		
		List<Battle> res = new ArrayList<Battle>();
		Map<String,Class<?>> m  = new HashMap<String,Class<?>>();
		m.put("challengerId", Integer.class);
		m.put("challengerRace", String.class);
		m.put("result", Integer.class);
		m.put("adversaryId", Integer.class);
		m.put("adversaryRace", String.class);
		m.put("mapName", String.class);
		String json = ((String [])value)[0];
		JSONArray jsonArray = JSONArray.fromObject(json);
		for (int i = 0; i < jsonArray.size(); i++) {
			Battle data = (Battle)JSONObject.toBean( jsonArray.getJSONObject(i), Battle.class, m);
			res.add(data);
		}
		return res;
	}

}



