package wseditor.gui.editor;

public class LineColumn
{
	public final int offset;
	public final int line;
	public final int column;

	public LineColumn(int offset, int line, int column)
	{
		this.offset = offset;
		this.line = line;
		this.column = column;
	}

	public static LineColumn of(int offset, int line, int column)
	{
		return new LineColumn(offset, line, column);
	}
}
