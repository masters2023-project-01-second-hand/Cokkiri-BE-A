package com.cokkiri.secondhand.user.entity;

import java.util.List;

import com.cokkiri.secondhand.global.exception.list.EmptyMyLocationListException;
import com.cokkiri.secondhand.global.exception.list.NotExistSelectedMyLocationException;
import com.cokkiri.secondhand.item.entity.Location;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MyLocationList {

	private List<MyLocation> myLocations;

	public Location findSelectedLocation() {

		if(myLocations.isEmpty()) throw new EmptyMyLocationListException();

		for (MyLocation myLocation : myLocations) {
			if (myLocation.isSelected()) {
				return myLocation.getLocation();
			}
		}
		throw new NotExistSelectedMyLocationException();
	}

}
