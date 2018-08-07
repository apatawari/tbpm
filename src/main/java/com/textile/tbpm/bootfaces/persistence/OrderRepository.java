package com.textile.tbpm.bootfaces.persistence;

import com.textile.tbpm.bootfaces.model.OrderEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntry, Long> {

}
