package wseditor.translator;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import wseditor.translator.util.WSTree;

public class Translator
{
	private static WSTree<Inst> root;

	static
	{
		WSTree.Mutable<Inst> tree = WSTree.create();
		tree.add("SS"  , Inst.PUSH);
		tree.add("STS" , Inst.COPY);
		tree.add("STL" , Inst.SLIDE);
		tree.add("SLS" , Inst.DUP);
		tree.add("SLT" , Inst.SWAP);
		tree.add("SLL" , Inst.POP);
		tree.add("TSSS", Inst.ADD);
		tree.add("TSST", Inst.SUB);
		tree.add("TSSL", Inst.MUL);
		tree.add("TSTS", Inst.DIV);
		tree.add("TSTT", Inst.MOD);
		tree.add("TTS" , Inst.STORE);
		tree.add("TTT" , Inst.LOAD);
		tree.add("TLSS", Inst.PUTC);
		tree.add("TLST", Inst.PUTI);
		tree.add("TLTS", Inst.GETC);
		tree.add("TLTT", Inst.GETI);
		tree.add("LSS" , Inst.LABEL);
		tree.add("LST" , Inst.CALL);
		tree.add("LSL" , Inst.JMP);
		tree.add("LTS" , Inst.JZ);
		tree.add("LTT" , Inst.JNEG);
		tree.add("LTL" , Inst.RET);
		tree.add("LLL" , Inst.HALT);
		root = tree;
	}

	private char[] cs;
	private int p;

	public Translator(String text)
	{
		cs = text.toCharArray();
	}

	public List<String> translate()
	{
		List<String> code = new ArrayList<String>();

		WSTree<Inst> cur = root;
		while (p < cs.length)
		{
			char c = cs[p++];
			if (!isWS(c)) continue;

			cur = cur.getSubtree(c);
			if (cur == null)
			{
				System.out.println("error");
				break;
			}
			else if (cur.isAccept())
			{
				gencode(code, cur.getValue());
				cur = root;
			}
		}
		return code;
	}

	private void gencode(List<String> code, Inst inst)
	{
		String s = "";
		if (inst != Inst.LABEL)
		{
			s += inst;
		}
		switch (inst)
		{
			case PUSH: case COPY: case SLIDE:
			{
				BigInteger n = readNumber();
				s += " " + n;
				break;
			}
			case CALL: case JMP: case JZ: case JNEG:
			{
				String label = readToLF();
				s += " L" + label;
				break;
			}
			case LABEL:
			{
				String label = readToLF();
				s += "L" + label + ":";
				break;
			}
			default:
				break;
		}
		code.add(s);
	}

	private String readToLF()
	{
		String s = "";
		char c;
		while ((c = cs[p++]) != '\n')
		{
			s += (c == ' ' ? '0' : '1');
		}
		return s;
	}

	private BigInteger readNumber()
	{
		BigInteger value = BigInteger.ZERO;
		char c;
		boolean positive = cs[p++] == ' ';
		while ((c = cs[p++]) != '\n')
		{
			value = value.shiftLeft(1).or(c == ' ' ? BigInteger.ZERO : BigInteger.ONE);
		}
		if (!positive)
		{
			value = value.negate();
		}
		return value;
	}

	private static boolean isWS(char c)
	{
		return c == ' ' || c == '\t' || c == '\n';
	}
}
