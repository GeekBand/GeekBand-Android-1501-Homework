package com.snail.travellingTrail.travelNotes.model;

import java.util.ArrayList;
import java.util.List;

import com.snail.travellingTrail.trailMap.model.Footprint;
import com.snail.travellingTrail.trailMap.model.FootprintContent;

public class TravelNotesProcessor
{

	/**
	 * 将List<Footprint>中的所有ArrayList<FootprintContent>取出来，
	 * 按顺序存放如新的ArrayList<FootprintContent>
	 * @param footprints
	 * @return 排好序的ArrayList<FootprintContent>集合
	 */
	public static ArrayList<FootprintContent> listTheTravelNotesContents(List<Footprint> footprints)
	{
		ArrayList<FootprintContent> footprintContents = new ArrayList<FootprintContent>();
		
		for (Footprint footprint : footprints)
		{
			for (FootprintContent footprintContent : footprint.getFootprint_Content())
			{
				footprintContents.add(footprintContent);
			}
		}
		
		return footprintContents;
	}
}
