package com.zimu.ao.enums;

public enum Menu {
	NONE, CM_SYS, CM_C, CM_M1, CM_M2, CM_M3,
	SYS_S, SYS_L, SYS_O, SYS_E,
	CHAR_C, CHAR_E, CHAR_S, CHAR_I, CHAR_Q, CHAR_M;
	
	public static Menu valueOf(int value) {
		if (value == SYS_S.ordinal())
			return SYS_S;
		else if (value == SYS_L.ordinal())
			return SYS_L;
		else if (value == SYS_O.ordinal())
			return SYS_O;
		else if (value == SYS_E.ordinal())
			return SYS_E;
		else if (value == CHAR_C.ordinal())
			return CHAR_C;
		else if (value == CHAR_E.ordinal())
			return CHAR_E;
		else if (value == CHAR_S.ordinal())
			return CHAR_S;
		else if (value == CHAR_I.ordinal())
			return CHAR_I;
		else if (value == CHAR_Q.ordinal())
			return CHAR_Q;
		else if (value == CHAR_M.ordinal())
			return CHAR_M;
		else 
			return NONE;
	}
}
