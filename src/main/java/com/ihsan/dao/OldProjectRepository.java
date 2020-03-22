package com.ihsan.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ihsan.entities.OldProject;

public interface OldProjectRepository extends JpaRepository<OldProject, BigInteger> {

	@Query(value = "SELECT * FROM POS_CP_PROJECTS_V  WHERE donor =:donatorId", nativeQuery = true)
	List<OldProject> getDonatorOldProjects(@Param("donatorId") BigInteger donatorId);

	@Query(value = "select * from POS_CP_PROJECTS_V order by PROJECT_ID desc fetch next 10 rows only", nativeQuery = true)
	List<OldProject> getMostRecentProjects();

	List<OldProject> findTop10ByNameIgnoreCaseContainingOrderByNameAsc(String name);

}
