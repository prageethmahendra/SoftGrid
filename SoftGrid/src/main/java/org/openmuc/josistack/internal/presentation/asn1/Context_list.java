/**
 * This class file was automatically generated by jASN1 (http://www.openmuc.org)
 */

package org.openmuc.josistack.internal.presentation.asn1;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.openmuc.jasn1.ber.BerByteArrayOutputStream;
import org.openmuc.jasn1.ber.BerIdentifier;
import org.openmuc.jasn1.ber.BerLength;
import org.openmuc.jasn1.ber.types.BerInteger;
import org.openmuc.jasn1.ber.types.BerObjectIdentifier;

public final class Context_list {

	public final static class SubSeq {

		public final static class SubSeqOf_transfer_syntax_name_list {

			public final static BerIdentifier identifier = new BerIdentifier(BerIdentifier.UNIVERSAL_CLASS,
					BerIdentifier.CONSTRUCTED, 16);
			protected BerIdentifier id;

			public byte[] code = null;
			public List<BerObjectIdentifier> seqOf = null;

			public SubSeqOf_transfer_syntax_name_list() {
				id = identifier;
			}

			public SubSeqOf_transfer_syntax_name_list(byte[] code) {
				id = identifier;
				this.code = code;
			}

			public SubSeqOf_transfer_syntax_name_list(List<BerObjectIdentifier> seqOf) {
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
				seqOf = new LinkedList<BerObjectIdentifier>();

				if (explicit) {
					codeLength += id.decodeAndCheck(iStream);
				}

				BerLength length = new BerLength();
				codeLength += length.decode(iStream);

				while (subCodeLength < length.val) {
					BerObjectIdentifier element = new BerObjectIdentifier();
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

		public final static BerIdentifier identifier = new BerIdentifier(BerIdentifier.UNIVERSAL_CLASS,
				BerIdentifier.CONSTRUCTED, 16);
		protected BerIdentifier id;

		public byte[] code = null;
		public BerInteger presentation_context_identifier = null;

		public BerObjectIdentifier abstract_syntax_name = null;

		public SubSeqOf_transfer_syntax_name_list transfer_syntax_name_list = null;

		public SubSeq() {
			id = identifier;
		}

		public SubSeq(byte[] code) {
			id = identifier;
			this.code = code;
		}

		public SubSeq(BerInteger presentation_context_identifier, BerObjectIdentifier abstract_syntax_name,
				SubSeqOf_transfer_syntax_name_list transfer_syntax_name_list) {
			id = identifier;
			this.presentation_context_identifier = presentation_context_identifier;
			this.abstract_syntax_name = abstract_syntax_name;
			this.transfer_syntax_name_list = transfer_syntax_name_list;
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
				codeLength += transfer_syntax_name_list.encode(berOStream, true);

				codeLength += abstract_syntax_name.encode(berOStream, true);

				codeLength += presentation_context_identifier.encode(berOStream, true);

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
			BerIdentifier berIdentifier = new BerIdentifier();
			boolean decodedIdentifier = false;

			if (explicit) {
				codeLength += id.decodeAndCheck(iStream);
			}

			BerLength length = new BerLength();
			codeLength += length.decode(iStream);

			if (subCodeLength < length.val) {
				if (decodedIdentifier == false) {
					subCodeLength += berIdentifier.decode(iStream);
					decodedIdentifier = true;
				}
				if (berIdentifier.equals(BerInteger.identifier)) {
					presentation_context_identifier = new BerInteger();
					subCodeLength += presentation_context_identifier.decode(iStream, false);
					decodedIdentifier = false;
				}
				else {
					throw new IOException("Identifier does not macht required sequence element identifer.");
				}
			}
			if (subCodeLength < length.val) {
				if (decodedIdentifier == false) {
					subCodeLength += berIdentifier.decode(iStream);
					decodedIdentifier = true;
				}
				if (berIdentifier.equals(BerObjectIdentifier.identifier)) {
					abstract_syntax_name = new BerObjectIdentifier();
					subCodeLength += abstract_syntax_name.decode(iStream, false);
					decodedIdentifier = false;
				}
				else {
					throw new IOException("Identifier does not macht required sequence element identifer.");
				}
			}
			if (subCodeLength < length.val) {
				if (decodedIdentifier == false) {
					subCodeLength += berIdentifier.decode(iStream);
					decodedIdentifier = true;
				}
				if (berIdentifier.equals(SubSeqOf_transfer_syntax_name_list.identifier)) {
					transfer_syntax_name_list = new SubSeqOf_transfer_syntax_name_list();
					subCodeLength += transfer_syntax_name_list.decode(iStream, false);
					decodedIdentifier = false;
				}
				else {
					throw new IOException("Identifier does not macht required sequence element identifer.");
				}
			}
			if (subCodeLength != length.val) {
				throw new IOException("Decoded sequence has wrong length tag");

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

	public final static BerIdentifier identifier = new BerIdentifier(BerIdentifier.UNIVERSAL_CLASS,
			BerIdentifier.CONSTRUCTED, 16);
	protected BerIdentifier id;

	public byte[] code = null;
	public List<SubSeq> seqOf = null;

	public Context_list() {
		id = identifier;
	}

	public Context_list(byte[] code) {
		id = identifier;
		this.code = code;
	}

	public Context_list(List<SubSeq> seqOf) {
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
		seqOf = new LinkedList<SubSeq>();

		if (explicit) {
			codeLength += id.decodeAndCheck(iStream);
		}

		BerLength length = new BerLength();
		codeLength += length.decode(iStream);

		while (subCodeLength < length.val) {
			SubSeq element = new SubSeq();
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
