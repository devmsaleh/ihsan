package com.ihsan.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ihsan.entities.OldProject;

public interface OldProjectRepository extends JpaRepository<OldProject, BigInteger> {

	@Query(value = "SELECT * FROM SC_PROJECT  PRJ WHERE COST < (SELECT NVL(SUM(PAY.MISC_RECEIPT_AMOUNT),0) FROM AR_MISC_RECEIPT_LINES PAY WHERE PAY.project_id=PRJ.PROJECT_ID) AND donor =:donatorId", nativeQuery = true)
	// @Cacheable(cacheNames = Constants.CACHE_NAME_OLD_PROJECTS_FOR_DONATOR)
	List<OldProject> getDonatorOldProjects(@Param("donatorId") BigInteger donatorId);

	@Query(value = "select * from SC_PROJECT order by PROJECT_ID desc fetch next 10 rows only", nativeQuery = true)
	List<OldProject> getMostRecentProjects();

	List<OldProject> findTop10ByNameIgnoreCaseContainingOrderByNameAsc(String name);

}
