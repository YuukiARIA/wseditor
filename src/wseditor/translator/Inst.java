package wseditor.translator;

import java.io.*;
import java.util.*;

public enum Inst
{
	UNDEF(false),
	PUSH(true),
	COPY(true),
	SLIDE(true),
	DUP(false),
	SWAP(false),
	POP(false),
	ADD(false),
	SUB(false),
	MUL(false),
	DIV(false),
	MOD(false),
	STORE(false),
	LOAD(false),
	PUTC(false),
	PUTI(false),
	GETC(false),
	GETI(false),
	LABEL(false),
	CALL(true),
	JMP(true),
	JZ(true),
	JNEG(true),
	RET(false),
	HALT(false);

	private boolean param;

	private Inst(boolean param)
	{
		this.param = param;
	}

	public boolean hasParameter()
	{
		return param;
	}
}
