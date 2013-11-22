package wseditor.translator.util;

import java.util.Map;

public abstract class WSTree<T>
{
	@SuppressWarnings("unchecked")
	protected Mutable<T>[] sub = new Mutable[3];
	protected boolean accept;
	protected T value;

	public boolean isAccept()
	{
		return accept;
	}

	public T getValue()
	{
		return value;
	}

	public WSTree<T> getSubtree(char c)
	{
		return sub[toIndex(c)];
	}

	public static class Mutable<T> extends WSTree<T>
	{
		public void add(String s, T value)
		{
			add(s, 0, value);
		}

		private void add(String s, int i, T value)
		{
			if (i < s.length())
			{
				int k = toIndex(s.charAt(i));
				if (sub[k] == null)
				{
					sub[k] = create();
				}
				sub[k].add(s, i + 1, value);
			}
			else
			{
				this.accept = true;
				this.value = value;
			}
		}
	}

	public static <T> Mutable<T> create()
	{
		return new Mutable<T>();
	}

	public static <T> WSTree<T> create(Map<String, T> map)
	{
		Mutable<T> tree = create();
		for (Map.Entry<String, T> e : map.entrySet())
		{
			tree.add(e.getKey(), e.getValue());
		}
		return tree;
	}

	private static int toIndex(char c)
	{
		switch (c)
		{
		case ' ':  case 'S': return 0;
		case '\t': case 'T': return 1;
		case '\n': case 'L': return 2;
		}
		return -1;
	}
}
