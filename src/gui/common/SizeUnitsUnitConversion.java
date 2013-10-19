package gui.common;

import model.Unit;

public class SizeUnitsUnitConversion {

	public static Unit sizeUnitsToUnit(SizeUnits supplyUnit) {
		switch (supplyUnit)
		{
			case Pounds:
				return Unit.LBS;
			case Ounces:
				return Unit.OZ;
			case Grams:
				return Unit.G;
			case Kilograms:
				return Unit.KG;
			case Gallons:
				return Unit.GAL;
			case Quarts:
				return Unit.QUART;
			case Pints:
				return Unit.PINT;
			case FluidOunces:
				return Unit.FLOZ;
			case Liters:
				return Unit.LITER;
			case Count:
				return Unit.COUNT;
			default:
				return Unit.COUNT;
		}
	}

	public static SizeUnits unitToSizeUnits(Unit u) {
		switch (u)
		{
			case COUNT:
				return SizeUnits.Count;
			case LBS:
				return SizeUnits.Pounds;
			case OZ:
				return SizeUnits.Ounces;
			case G :
				return SizeUnits.Grams;
			case KG:
				return SizeUnits.Kilograms;
			case GAL:
				return SizeUnits.Gallons;
			case QUART:
				return SizeUnits.Quarts;
			case PINT:
				return SizeUnits.Pints;
			case FLOZ:
				return SizeUnits.FluidOunces;
			case LITER:
				return SizeUnits.Liters;
			default:
				return SizeUnits.Count;
		}
	}
}
