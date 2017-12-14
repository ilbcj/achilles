package com.achilles.dao;

import java.util.List;

import com.achilles.model.Config;

public interface ConfigDAO {
	public List<Config> GetAllConfigItems() throws Exception;
	public Config SaveConfigItem(Config configItem) throws Exception;
}
