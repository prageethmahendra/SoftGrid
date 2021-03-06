/**
 * This class file was automatically generated by jASN1 (http://www.openmuc.org)
 */

package org.openmuc.openiec61850.internal.mms.asn1;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.openmuc.jasn1.ber.BerByteArrayOutputStream;
import org.openmuc.jasn1.ber.BerIdentifier;
import org.openmuc.jasn1.ber.BerLength;

public final class VariableAccessSpecification {

	public byte[] code = null;

	public final static class SubSeqOf_listOfVariable {

		public final static BerIdentifier identifier = new BerIdentifier(BerIdentifier.UNIVERSAL_CLASS,
				BerIdentifier.CONSTRUCTED, 16);
		protected BerIdentifier id;

		public byte[] code = null;
		public List<VariableDef> seqOf = null;

		public SubSeqOf_listOfVariable() {
			id = identifier;
		}

		public SubSeqOf_listOfVariable(byte[] code) {
			id = identifier;
			this.code = code;
		}

		public SubSeqOf_listOfVariable(List<VariableDef> seqOf) {
			id = identifier;
			this.seqOf = seqOf;
		}

		public int encode(BerByteArrayOutputStream berOStream, boolean explicit) throws IOException {
			int codeLength;

			if (code != null) {
				codeLength = code.length;
				for (int i = code.length - 1; i >= 0; i--) {
					berOStream.write(code[i]);
				}
			}
			else {
				codeLength = 0;
				for (int i = (seqOf.size() - 1); i >= 0; i--) {
					codeLength += seqOf.get(i).encode(berOStream, true);
				}

				codeLength += BerLength.encodeLength(berOStream, codeLength);

			}

			if (explicit) {
				codeLength += id.encode(berOStream);
			}

			return codeLength;
		}

		public int decode(InputStream iStream, boolean explicit) throws IOException {
			int codeLength = 0;
			int subCodeLength = 0;
			seqOf = new LinkedList<VariableDef>();

			if (explicit) {
				codeLength += id.decodeAndCheck(iStream);
			}

			BerLength length = new BerLength();
			codeLength += length.decode(iStream);

			while (subCodeLength < length.val) {
				VariableDef element = new VariableDef();
				subCodeLength += element.decode(iStream, true);
				seqOf.add(element);
			}
			if (subCodeLength != length.val) {
				throw new IOException("Decoded SequenceOf or SetOf has wrong length tag");

			}
			codeLength += subCodeLength;

			return codeLength;
		}

		public void encodeAndSave(int encodingSizeGuess) throws IOException {
			BerByteArrayOutputStream berOStream = new BerByteArrayOutputStream(encodingSizeGuess);
			encode(berOStream, false);
			code = berOStream.getArray();
		}
	}

	public SubSeqOf_listOfVariable listOfVariable = null;

	public ObjectName variableListName = null;

	public VariableAccessSpecification() {
	}

	public VariableAccessSpecification(byte[] code) {
		this.code = code;
	}

	public VariableAccessSpecification(SubSeqOf_listOfVariable listOfVariable, ObjectName variableListName) {
		this.listOfVariable = listOfVariable;
		this.variableListName = variableListName;
	}

	public int encode(BerByteArrayOutputStream berOStream, boolean explicit) throws IOException {
		if (code != null) {
			for (int i = code.length - 1; i >= 0; i--) {
				berOStream.write(code[i]);
			}
			return code.length;

		}
		int codeLength = 0;
		int sublength;

		if (variableListName != null) {
			sublength = variableListName.encode(berOStream, true);
			codeLength += sublength;
			codeLength += BerLength.encodeLength(berOStream, sublength);
			codeLength += (new BerIdentifier(BerIdentifier.CONTEXT_CLASS, BerIdentifier.CONSTRUCTED, 1))
					.encode(berOStream);
			return codeLength;

		}

		if (listOfVariable != null) {
			codeLength += listOfVariable.encode(berOStream, false);
			codeLength += (new BerIdentifier(BerIdentifier.CONTEXT_CLASS, BerIdentifier.CONSTRUCTED, 0))
					.encode(berOStream);
			return codeLength;

		}

		throw new IOException("Error encoding BerChoice: No item in choice was selected.");
	}

	public int decode(InputStream iStream, BerIdentifier berIdentifier) throws IOException {
		int codeLength = 0;
		BerIdentifier passedIdentifier = berIdentifier;
		if (berIdentifier == null) {
			berIdentifier = new BerIdentifier();
			codeLength += berIdentifier.decode(iStream);
		}
		if (berIdentifier.equals(BerIdentifier.CONTEXT_CLASS, BerIdentifier.CONSTRUCTED, 0)) {
			listOfVariable = new SubSeqOf_listOfVariable();
			codeLength += listOfVariable.decode(iStream, false);
			return codeLength;
		}

		if (berIdentifier.equals(BerIdentifier.CONTEXT_CLASS, BerIdentifier.CONSTRUCTED, 1)) {
			codeLength += new BerLength().decode(iStream);
			variableListName = new ObjectName();
			codeLength += variableListName.decode(iStream, null);
			return codeLength;
		}

		if (passedIdentifier != null) {
			return 0;
		}
		throw new IOException("Error decoding BerChoice: Identifier matched to no item.");
	}

	public void encodeAndSave(int encodingSizeGuess) throws IOException {
		BerByteArrayOutputStream berOStream = new BerByteArrayOutputStream(encodingSizeGuess);
		encode(berOStream, false);
		code = berOStream.getArray();
	}
}
