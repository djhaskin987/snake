package model;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.tuple.Pair;

public class NaturalLanguageTable<T> {
	private List<Pair<Pattern, T>> table;
	private int caseSensitivity;
	public NaturalLanguageTable(boolean caseSensitivity)
	{
		this.caseSensitivity = 0;
		if (caseSensitivity)
		{
			this.caseSensitivity = Pattern.CASE_INSENSITIVE;
		}
		table = new LinkedList<Pair<Pattern,T>>();
	}
	public void addEntry(String regex, T val)
	{
		Pattern p = Pattern.compile(regex, this.caseSensitivity);
		table.add(Pair.of(p,val));
	}

	public T match(String key)
	{
		for (Pair<Pattern,T> entry : table)
		{
			if (entry.getLeft().matcher(key).matches())
			{
				return entry.getRight();
			}
		}
		return null;
	}
}
