package com.spring.ai.helpdesk.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "help_desk_ticket")
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "TEXT")
	private String summary;

	@Column(length = 2000)
	private String description;

	@Enumerated(EnumType.STRING)
	private Priority priority;

	@Column(nullable = false)
	private String email;

	private LocalDateTime createdOn;
	private LocalDateTime updatedOn;

	@Enumerated(EnumType.STRING)
	private Status status;

	private String category;

	@PrePersist
	void preSave() {
		if (this.createdOn == null) {
			this.createdOn = LocalDateTime.now();
		}
		if (this.updatedOn == null) {
			this.updatedOn = LocalDateTime.now();
		}
		if (this.status == null) {
			this.status = Status.OPEN;
		}
		if (this.priority == null) {
			this.priority = Priority.MEDIUM;
		}
		if (this.category == null || this.category.isBlank()) {
			this.category = "General";
		}
	}

	@PreUpdate
	void preUpdate() {
		this.updatedOn = LocalDateTime.now();
	}
}
