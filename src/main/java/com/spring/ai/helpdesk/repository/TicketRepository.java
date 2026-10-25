package com.spring.ai.helpdesk.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.ai.helpdesk.entity.Priority;
import com.spring.ai.helpdesk.entity.Status;
import com.spring.ai.helpdesk.entity.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

	List<Ticket> findByEmailOrderByCreatedOnDesc(String email);

	Optional<Ticket> findFirstByEmailOrderByCreatedOnDesc(String email);

	List<Ticket> findByStatusOrderByCreatedOnDesc(Status status);

	List<Ticket> findByPriorityOrderByCreatedOnDesc(Priority priority);

	long countByStatus(Status status);
}
