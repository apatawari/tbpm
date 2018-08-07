package com.textile.tbpm.bootfaces.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.textile.tbpm.bootfaces.model.OrderInwardEntry;

public interface OrderInwardRepository extends JpaRepository<OrderInwardEntry, Long> {
	
	public List<OrderInwardEntry> findAllByOrderByLotNumberAsc();
	
	public List<OrderInwardEntry> findByPartyNameIgnoreCaseContaining(String keyword);

	public List<OrderInwardEntry> findByQualityIgnoreCaseContaining(String keyword);

	public List<OrderInwardEntry> findByMarkaIgnoreCaseContaining(String keyword);

}
