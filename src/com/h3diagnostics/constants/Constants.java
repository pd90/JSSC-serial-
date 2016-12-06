/*******************************************************************************
 * Copyright (c) 5/12/2016
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 *
 *
 * Contributors:
 *     paras dhanta - initial API and implementation
 *******************************************************************************/
package com.h3diagnostics.constants;

public class Constants {
	    public static final int MAX_NUM_BYTES = 65536;
	    public static final int DEVICE_BAUD_RATE = 230400;

	    public static final byte XON = 0x11;    /* Resume transmission */
	    public static final byte XOFF = 0x13;    /* Pause transmission */

	    //handler event
	    public static final int MESSAGE_READ = 1;

	    public static final int UI_READ_BUFFER_SIZE = 412;//14420;//8120;//10240; // Notes: 115K:1440B/100ms, 230k:2880B/100ms

	    public static final int READSIZE_LOAD_VALUE = 624;//14600;//8500;
	    public static final int TEST_ID_PULSE = 7;
	    public static final int TEST_ID_BG = 2;
	    public static final int TEST_ID_BP = 1;


}
