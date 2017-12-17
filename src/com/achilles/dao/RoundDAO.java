package com.achilles.dao;

import java.util.List;

import com.achilles.model.Round;

public interface RoundDAO {

	public List<Round> GetRounds(int start, int length) throws Exception;

	public long GetRoundsCount() throws Exception;

	public Round AddRound(Round season) throws Exception;

	public void DelRound(Round target) throws Exception;

	public List<Round> GetRoundByStatus(int status) throws Exception;

	public Round GetRoundById(int roundId) throws Exception;

}
