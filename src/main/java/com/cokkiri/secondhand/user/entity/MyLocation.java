package com.cokkiri.secondhand.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cokkiri.secondhand.item.entity.Location;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "my_location")
public class MyLocation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@ManyToOne
	@JoinColumn(name = "location_id")
	private Location location;

	@Column(name = "is_selected")
	private boolean selected;

	@Builder
	public MyLocation(UserEntity user, Location location, boolean selected) {
		this.user = user;
		this.location = location;
		this.selected = selected;
	}

	public void chooseMyLocation() {
		this.selected = true;
	}
}
