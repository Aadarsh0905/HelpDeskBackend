package com.spring.ai.helpdesk.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="help_desk_ticket")
public class Ticket {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Lob
	private String summary;
	
	@Column(length=1000)
	private String description;
	
	@Enumerated(EnumType.STRING)
	private Priority priority;
	
	@Column(unique=true)
	private String email;
	
	private LocalDateTime createdOn;
	private LocalDateTime updatedOn;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	private String category;
	
	@PrePersist
	void preSave() {
		if(this.createdOn==null)this.createdOn=LocalDateTime.now();
		this.updatedOn=LocalDateTime.now();
	}
	
	@PreUpdate
	void preUpdate() {
		this.updatedOn=LocalDateTime.now();
	}
	
}
