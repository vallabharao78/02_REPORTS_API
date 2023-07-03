package com.vallabha.repo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vallabha.entity.EligibilityDetails;

public interface EligibilityDetailsRepo extends JpaRepository<EligibilityDetails,Serializable>
{
	//Custom Query
	@Query("select distinct(planName) from EligibilityDetails")
	public List<String> getUniquePlanNames();
	
	//Custom Query
	@Query("select distinct(planStatus) from EligibilityDetails")
	public List<String> getUniquePlanStatues();
}
