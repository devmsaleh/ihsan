package com.ihsan.dao.charityBoxes;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ihsan.entities.charityBoxes.RouteDetail;

public interface RouteDetailRepository extends JpaRepository<RouteDetail, BigInteger> {

	@Query(value = "select * from TM_DELEGATE_ROUTE_DTL dtl where dtl.ROUTE_ID in (select ROUTE_ID from TM_DELEGATE_ROUTE where STATUS=1 and DELEGATE_ID=:delegateId)", nativeQuery = true)
	List<RouteDetail> findDelegateRouteDetails(@Param("delegateId") BigInteger delegateId);

}
