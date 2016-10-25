/*
 * Copyright 2014 Fraunhofer ISE
 *
 * This file is part of j60870.
 * For more information visit http://www.openmuc.org
 *
 * j60870 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * j60870 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with j60870.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.openmuc.j60870;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Represents a qualifier of interrogation (QOI) information element.
 * 
 * @author Stefan Feuerhahn
 * 
 */
public class IeQualifierOfInterrogation extends InformationElement {

	private final int value;

	public IeQualifierOfInterrogation(int value) {
		this.value = value;
	}

	IeQualifierOfInterrogation(DataInputStream is) throws IOException {
		value = is.read();
	}

	@Override
	int encode(byte[] buffer, int i) {
		buffer[i] = (byte) value;
		return 1;
	}

	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Qualifier of interrogation: " + value;
	}
}
