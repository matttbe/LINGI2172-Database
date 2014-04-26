package com.charlyparkingapps.map;

import android.location.Location;


public class MarkersParams
{
	private Location location;
	private float radius;

	/**
	 * @param location the location of the center point
	 * @param radius the radius that we have to cover
	 */
	public MarkersParams (Location location, float radius)
	{
		this.location = location;
		this.radius = radius;
	}

	/**
	 * @return the location
	 */
	public Location getLocation ()
	{
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation (Location location)
	{
		this.location = location;
	}

	/**
	 * @return the radius
	 */
	public float getRadius ()
	{
		return radius;
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius (float radius)
	{
		this.radius = radius;
	}
}