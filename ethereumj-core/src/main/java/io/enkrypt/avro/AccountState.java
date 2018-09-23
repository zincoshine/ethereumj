/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package io.enkrypt.avro;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class AccountState extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -9185627008323833488L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"AccountState\",\"namespace\":\"io.enkrypt.avro\",\"fields\":[{\"name\":\"nonce\",\"type\":\"bytes\"},{\"name\":\"balance\",\"type\":\"bytes\"},{\"name\":\"stateRoot\",\"type\":\"bytes\"},{\"name\":\"codeHash\",\"type\":\"bytes\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<AccountState> ENCODER =
      new BinaryMessageEncoder<AccountState>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<AccountState> DECODER =
      new BinaryMessageDecoder<AccountState>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<AccountState> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<AccountState> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<AccountState>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this AccountState to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a AccountState from a ByteBuffer. */
  public static AccountState fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public java.nio.ByteBuffer nonce;
  @Deprecated public java.nio.ByteBuffer balance;
  @Deprecated public java.nio.ByteBuffer stateRoot;
  @Deprecated public java.nio.ByteBuffer codeHash;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public AccountState() {}

  /**
   * All-args constructor.
   * @param nonce The new value for nonce
   * @param balance The new value for balance
   * @param stateRoot The new value for stateRoot
   * @param codeHash The new value for codeHash
   */
  public AccountState(java.nio.ByteBuffer nonce, java.nio.ByteBuffer balance, java.nio.ByteBuffer stateRoot, java.nio.ByteBuffer codeHash) {
    this.nonce = nonce;
    this.balance = balance;
    this.stateRoot = stateRoot;
    this.codeHash = codeHash;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return nonce;
    case 1: return balance;
    case 2: return stateRoot;
    case 3: return codeHash;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: nonce = (java.nio.ByteBuffer)value$; break;
    case 1: balance = (java.nio.ByteBuffer)value$; break;
    case 2: stateRoot = (java.nio.ByteBuffer)value$; break;
    case 3: codeHash = (java.nio.ByteBuffer)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'nonce' field.
   * @return The value of the 'nonce' field.
   */
  public java.nio.ByteBuffer getNonce() {
    return nonce;
  }

  /**
   * Sets the value of the 'nonce' field.
   * @param value the value to set.
   */
  public void setNonce(java.nio.ByteBuffer value) {
    this.nonce = value;
  }

  /**
   * Gets the value of the 'balance' field.
   * @return The value of the 'balance' field.
   */
  public java.nio.ByteBuffer getBalance() {
    return balance;
  }

  /**
   * Sets the value of the 'balance' field.
   * @param value the value to set.
   */
  public void setBalance(java.nio.ByteBuffer value) {
    this.balance = value;
  }

  /**
   * Gets the value of the 'stateRoot' field.
   * @return The value of the 'stateRoot' field.
   */
  public java.nio.ByteBuffer getStateRoot() {
    return stateRoot;
  }

  /**
   * Sets the value of the 'stateRoot' field.
   * @param value the value to set.
   */
  public void setStateRoot(java.nio.ByteBuffer value) {
    this.stateRoot = value;
  }

  /**
   * Gets the value of the 'codeHash' field.
   * @return The value of the 'codeHash' field.
   */
  public java.nio.ByteBuffer getCodeHash() {
    return codeHash;
  }

  /**
   * Sets the value of the 'codeHash' field.
   * @param value the value to set.
   */
  public void setCodeHash(java.nio.ByteBuffer value) {
    this.codeHash = value;
  }

  /**
   * Creates a new AccountState RecordBuilder.
   * @return A new AccountState RecordBuilder
   */
  public static io.enkrypt.avro.AccountState.Builder newBuilder() {
    return new io.enkrypt.avro.AccountState.Builder();
  }

  /**
   * Creates a new AccountState RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new AccountState RecordBuilder
   */
  public static io.enkrypt.avro.AccountState.Builder newBuilder(io.enkrypt.avro.AccountState.Builder other) {
    return new io.enkrypt.avro.AccountState.Builder(other);
  }

  /**
   * Creates a new AccountState RecordBuilder by copying an existing AccountState instance.
   * @param other The existing instance to copy.
   * @return A new AccountState RecordBuilder
   */
  public static io.enkrypt.avro.AccountState.Builder newBuilder(io.enkrypt.avro.AccountState other) {
    return new io.enkrypt.avro.AccountState.Builder(other);
  }

  /**
   * RecordBuilder for AccountState instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<AccountState>
    implements org.apache.avro.data.RecordBuilder<AccountState> {

    private java.nio.ByteBuffer nonce;
    private java.nio.ByteBuffer balance;
    private java.nio.ByteBuffer stateRoot;
    private java.nio.ByteBuffer codeHash;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(io.enkrypt.avro.AccountState.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.nonce)) {
        this.nonce = data().deepCopy(fields()[0].schema(), other.nonce);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.balance)) {
        this.balance = data().deepCopy(fields()[1].schema(), other.balance);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.stateRoot)) {
        this.stateRoot = data().deepCopy(fields()[2].schema(), other.stateRoot);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.codeHash)) {
        this.codeHash = data().deepCopy(fields()[3].schema(), other.codeHash);
        fieldSetFlags()[3] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing AccountState instance
     * @param other The existing instance to copy.
     */
    private Builder(io.enkrypt.avro.AccountState other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.nonce)) {
        this.nonce = data().deepCopy(fields()[0].schema(), other.nonce);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.balance)) {
        this.balance = data().deepCopy(fields()[1].schema(), other.balance);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.stateRoot)) {
        this.stateRoot = data().deepCopy(fields()[2].schema(), other.stateRoot);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.codeHash)) {
        this.codeHash = data().deepCopy(fields()[3].schema(), other.codeHash);
        fieldSetFlags()[3] = true;
      }
    }

    /**
      * Gets the value of the 'nonce' field.
      * @return The value.
      */
    public java.nio.ByteBuffer getNonce() {
      return nonce;
    }

    /**
      * Sets the value of the 'nonce' field.
      * @param value The value of 'nonce'.
      * @return This builder.
      */
    public io.enkrypt.avro.AccountState.Builder setNonce(java.nio.ByteBuffer value) {
      validate(fields()[0], value);
      this.nonce = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'nonce' field has been set.
      * @return True if the 'nonce' field has been set, false otherwise.
      */
    public boolean hasNonce() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'nonce' field.
      * @return This builder.
      */
    public io.enkrypt.avro.AccountState.Builder clearNonce() {
      nonce = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'balance' field.
      * @return The value.
      */
    public java.nio.ByteBuffer getBalance() {
      return balance;
    }

    /**
      * Sets the value of the 'balance' field.
      * @param value The value of 'balance'.
      * @return This builder.
      */
    public io.enkrypt.avro.AccountState.Builder setBalance(java.nio.ByteBuffer value) {
      validate(fields()[1], value);
      this.balance = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'balance' field has been set.
      * @return True if the 'balance' field has been set, false otherwise.
      */
    public boolean hasBalance() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'balance' field.
      * @return This builder.
      */
    public io.enkrypt.avro.AccountState.Builder clearBalance() {
      balance = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'stateRoot' field.
      * @return The value.
      */
    public java.nio.ByteBuffer getStateRoot() {
      return stateRoot;
    }

    /**
      * Sets the value of the 'stateRoot' field.
      * @param value The value of 'stateRoot'.
      * @return This builder.
      */
    public io.enkrypt.avro.AccountState.Builder setStateRoot(java.nio.ByteBuffer value) {
      validate(fields()[2], value);
      this.stateRoot = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'stateRoot' field has been set.
      * @return True if the 'stateRoot' field has been set, false otherwise.
      */
    public boolean hasStateRoot() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'stateRoot' field.
      * @return This builder.
      */
    public io.enkrypt.avro.AccountState.Builder clearStateRoot() {
      stateRoot = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'codeHash' field.
      * @return The value.
      */
    public java.nio.ByteBuffer getCodeHash() {
      return codeHash;
    }

    /**
      * Sets the value of the 'codeHash' field.
      * @param value The value of 'codeHash'.
      * @return This builder.
      */
    public io.enkrypt.avro.AccountState.Builder setCodeHash(java.nio.ByteBuffer value) {
      validate(fields()[3], value);
      this.codeHash = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'codeHash' field has been set.
      * @return True if the 'codeHash' field has been set, false otherwise.
      */
    public boolean hasCodeHash() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'codeHash' field.
      * @return This builder.
      */
    public io.enkrypt.avro.AccountState.Builder clearCodeHash() {
      codeHash = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public AccountState build() {
      try {
        AccountState record = new AccountState();
        record.nonce = fieldSetFlags()[0] ? this.nonce : (java.nio.ByteBuffer) defaultValue(fields()[0]);
        record.balance = fieldSetFlags()[1] ? this.balance : (java.nio.ByteBuffer) defaultValue(fields()[1]);
        record.stateRoot = fieldSetFlags()[2] ? this.stateRoot : (java.nio.ByteBuffer) defaultValue(fields()[2]);
        record.codeHash = fieldSetFlags()[3] ? this.codeHash : (java.nio.ByteBuffer) defaultValue(fields()[3]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<AccountState>
    WRITER$ = (org.apache.avro.io.DatumWriter<AccountState>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<AccountState>
    READER$ = (org.apache.avro.io.DatumReader<AccountState>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
