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
public class TransactionReceipt extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 3765591577823214566L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TransactionReceipt\",\"namespace\":\"io.enkrypt.avro\",\"fields\":[{\"name\":\"postTxState\",\"type\":\"bytes\"},{\"name\":\"cumulativeGas\",\"type\":\"bytes\"},{\"name\":\"bloomFilter\",\"type\":\"bytes\"},{\"name\":\"gasUsed\",\"type\":\"bytes\"},{\"name\":\"executionResult\",\"type\":\"bytes\"},{\"name\":\"logs\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"LogInfo\",\"fields\":[{\"name\":\"address\",\"type\":{\"type\":\"fixed\",\"name\":\"Bytes20\",\"size\":20}},{\"name\":\"topics\",\"type\":{\"type\":\"array\",\"items\":\"bytes\"}},{\"name\":\"data\",\"type\":\"bytes\"}]}}},{\"name\":\"error\",\"type\":[\"null\",\"string\"]},{\"name\":\"txHash\",\"type\":[\"null\",\"bytes\"]}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<TransactionReceipt> ENCODER =
      new BinaryMessageEncoder<TransactionReceipt>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<TransactionReceipt> DECODER =
      new BinaryMessageDecoder<TransactionReceipt>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<TransactionReceipt> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<TransactionReceipt> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<TransactionReceipt>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this TransactionReceipt to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a TransactionReceipt from a ByteBuffer. */
  public static TransactionReceipt fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public java.nio.ByteBuffer postTxState;
  @Deprecated public java.nio.ByteBuffer cumulativeGas;
  @Deprecated public java.nio.ByteBuffer bloomFilter;
  @Deprecated public java.nio.ByteBuffer gasUsed;
  @Deprecated public java.nio.ByteBuffer executionResult;
  @Deprecated public java.util.List<io.enkrypt.avro.LogInfo> logs;
  @Deprecated public java.lang.CharSequence error;
  @Deprecated public java.nio.ByteBuffer txHash;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public TransactionReceipt() {}

  /**
   * All-args constructor.
   * @param postTxState The new value for postTxState
   * @param cumulativeGas The new value for cumulativeGas
   * @param bloomFilter The new value for bloomFilter
   * @param gasUsed The new value for gasUsed
   * @param executionResult The new value for executionResult
   * @param logs The new value for logs
   * @param error The new value for error
   * @param txHash The new value for txHash
   */
  public TransactionReceipt(java.nio.ByteBuffer postTxState, java.nio.ByteBuffer cumulativeGas, java.nio.ByteBuffer bloomFilter, java.nio.ByteBuffer gasUsed, java.nio.ByteBuffer executionResult, java.util.List<io.enkrypt.avro.LogInfo> logs, java.lang.CharSequence error, java.nio.ByteBuffer txHash) {
    this.postTxState = postTxState;
    this.cumulativeGas = cumulativeGas;
    this.bloomFilter = bloomFilter;
    this.gasUsed = gasUsed;
    this.executionResult = executionResult;
    this.logs = logs;
    this.error = error;
    this.txHash = txHash;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return postTxState;
    case 1: return cumulativeGas;
    case 2: return bloomFilter;
    case 3: return gasUsed;
    case 4: return executionResult;
    case 5: return logs;
    case 6: return error;
    case 7: return txHash;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: postTxState = (java.nio.ByteBuffer)value$; break;
    case 1: cumulativeGas = (java.nio.ByteBuffer)value$; break;
    case 2: bloomFilter = (java.nio.ByteBuffer)value$; break;
    case 3: gasUsed = (java.nio.ByteBuffer)value$; break;
    case 4: executionResult = (java.nio.ByteBuffer)value$; break;
    case 5: logs = (java.util.List<io.enkrypt.avro.LogInfo>)value$; break;
    case 6: error = (java.lang.CharSequence)value$; break;
    case 7: txHash = (java.nio.ByteBuffer)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'postTxState' field.
   * @return The value of the 'postTxState' field.
   */
  public java.nio.ByteBuffer getPostTxState() {
    return postTxState;
  }

  /**
   * Sets the value of the 'postTxState' field.
   * @param value the value to set.
   */
  public void setPostTxState(java.nio.ByteBuffer value) {
    this.postTxState = value;
  }

  /**
   * Gets the value of the 'cumulativeGas' field.
   * @return The value of the 'cumulativeGas' field.
   */
  public java.nio.ByteBuffer getCumulativeGas() {
    return cumulativeGas;
  }

  /**
   * Sets the value of the 'cumulativeGas' field.
   * @param value the value to set.
   */
  public void setCumulativeGas(java.nio.ByteBuffer value) {
    this.cumulativeGas = value;
  }

  /**
   * Gets the value of the 'bloomFilter' field.
   * @return The value of the 'bloomFilter' field.
   */
  public java.nio.ByteBuffer getBloomFilter() {
    return bloomFilter;
  }

  /**
   * Sets the value of the 'bloomFilter' field.
   * @param value the value to set.
   */
  public void setBloomFilter(java.nio.ByteBuffer value) {
    this.bloomFilter = value;
  }

  /**
   * Gets the value of the 'gasUsed' field.
   * @return The value of the 'gasUsed' field.
   */
  public java.nio.ByteBuffer getGasUsed() {
    return gasUsed;
  }

  /**
   * Sets the value of the 'gasUsed' field.
   * @param value the value to set.
   */
  public void setGasUsed(java.nio.ByteBuffer value) {
    this.gasUsed = value;
  }

  /**
   * Gets the value of the 'executionResult' field.
   * @return The value of the 'executionResult' field.
   */
  public java.nio.ByteBuffer getExecutionResult() {
    return executionResult;
  }

  /**
   * Sets the value of the 'executionResult' field.
   * @param value the value to set.
   */
  public void setExecutionResult(java.nio.ByteBuffer value) {
    this.executionResult = value;
  }

  /**
   * Gets the value of the 'logs' field.
   * @return The value of the 'logs' field.
   */
  public java.util.List<io.enkrypt.avro.LogInfo> getLogs() {
    return logs;
  }

  /**
   * Sets the value of the 'logs' field.
   * @param value the value to set.
   */
  public void setLogs(java.util.List<io.enkrypt.avro.LogInfo> value) {
    this.logs = value;
  }

  /**
   * Gets the value of the 'error' field.
   * @return The value of the 'error' field.
   */
  public java.lang.CharSequence getError() {
    return error;
  }

  /**
   * Sets the value of the 'error' field.
   * @param value the value to set.
   */
  public void setError(java.lang.CharSequence value) {
    this.error = value;
  }

  /**
   * Gets the value of the 'txHash' field.
   * @return The value of the 'txHash' field.
   */
  public java.nio.ByteBuffer getTxHash() {
    return txHash;
  }

  /**
   * Sets the value of the 'txHash' field.
   * @param value the value to set.
   */
  public void setTxHash(java.nio.ByteBuffer value) {
    this.txHash = value;
  }

  /**
   * Creates a new TransactionReceipt RecordBuilder.
   * @return A new TransactionReceipt RecordBuilder
   */
  public static io.enkrypt.avro.TransactionReceipt.Builder newBuilder() {
    return new io.enkrypt.avro.TransactionReceipt.Builder();
  }

  /**
   * Creates a new TransactionReceipt RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new TransactionReceipt RecordBuilder
   */
  public static io.enkrypt.avro.TransactionReceipt.Builder newBuilder(io.enkrypt.avro.TransactionReceipt.Builder other) {
    return new io.enkrypt.avro.TransactionReceipt.Builder(other);
  }

  /**
   * Creates a new TransactionReceipt RecordBuilder by copying an existing TransactionReceipt instance.
   * @param other The existing instance to copy.
   * @return A new TransactionReceipt RecordBuilder
   */
  public static io.enkrypt.avro.TransactionReceipt.Builder newBuilder(io.enkrypt.avro.TransactionReceipt other) {
    return new io.enkrypt.avro.TransactionReceipt.Builder(other);
  }

  /**
   * RecordBuilder for TransactionReceipt instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<TransactionReceipt>
    implements org.apache.avro.data.RecordBuilder<TransactionReceipt> {

    private java.nio.ByteBuffer postTxState;
    private java.nio.ByteBuffer cumulativeGas;
    private java.nio.ByteBuffer bloomFilter;
    private java.nio.ByteBuffer gasUsed;
    private java.nio.ByteBuffer executionResult;
    private java.util.List<io.enkrypt.avro.LogInfo> logs;
    private java.lang.CharSequence error;
    private java.nio.ByteBuffer txHash;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(io.enkrypt.avro.TransactionReceipt.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.postTxState)) {
        this.postTxState = data().deepCopy(fields()[0].schema(), other.postTxState);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.cumulativeGas)) {
        this.cumulativeGas = data().deepCopy(fields()[1].schema(), other.cumulativeGas);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.bloomFilter)) {
        this.bloomFilter = data().deepCopy(fields()[2].schema(), other.bloomFilter);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.gasUsed)) {
        this.gasUsed = data().deepCopy(fields()[3].schema(), other.gasUsed);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.executionResult)) {
        this.executionResult = data().deepCopy(fields()[4].schema(), other.executionResult);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.logs)) {
        this.logs = data().deepCopy(fields()[5].schema(), other.logs);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.error)) {
        this.error = data().deepCopy(fields()[6].schema(), other.error);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.txHash)) {
        this.txHash = data().deepCopy(fields()[7].schema(), other.txHash);
        fieldSetFlags()[7] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing TransactionReceipt instance
     * @param other The existing instance to copy.
     */
    private Builder(io.enkrypt.avro.TransactionReceipt other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.postTxState)) {
        this.postTxState = data().deepCopy(fields()[0].schema(), other.postTxState);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.cumulativeGas)) {
        this.cumulativeGas = data().deepCopy(fields()[1].schema(), other.cumulativeGas);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.bloomFilter)) {
        this.bloomFilter = data().deepCopy(fields()[2].schema(), other.bloomFilter);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.gasUsed)) {
        this.gasUsed = data().deepCopy(fields()[3].schema(), other.gasUsed);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.executionResult)) {
        this.executionResult = data().deepCopy(fields()[4].schema(), other.executionResult);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.logs)) {
        this.logs = data().deepCopy(fields()[5].schema(), other.logs);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.error)) {
        this.error = data().deepCopy(fields()[6].schema(), other.error);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.txHash)) {
        this.txHash = data().deepCopy(fields()[7].schema(), other.txHash);
        fieldSetFlags()[7] = true;
      }
    }

    /**
      * Gets the value of the 'postTxState' field.
      * @return The value.
      */
    public java.nio.ByteBuffer getPostTxState() {
      return postTxState;
    }

    /**
      * Sets the value of the 'postTxState' field.
      * @param value The value of 'postTxState'.
      * @return This builder.
      */
    public io.enkrypt.avro.TransactionReceipt.Builder setPostTxState(java.nio.ByteBuffer value) {
      validate(fields()[0], value);
      this.postTxState = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'postTxState' field has been set.
      * @return True if the 'postTxState' field has been set, false otherwise.
      */
    public boolean hasPostTxState() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'postTxState' field.
      * @return This builder.
      */
    public io.enkrypt.avro.TransactionReceipt.Builder clearPostTxState() {
      postTxState = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'cumulativeGas' field.
      * @return The value.
      */
    public java.nio.ByteBuffer getCumulativeGas() {
      return cumulativeGas;
    }

    /**
      * Sets the value of the 'cumulativeGas' field.
      * @param value The value of 'cumulativeGas'.
      * @return This builder.
      */
    public io.enkrypt.avro.TransactionReceipt.Builder setCumulativeGas(java.nio.ByteBuffer value) {
      validate(fields()[1], value);
      this.cumulativeGas = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'cumulativeGas' field has been set.
      * @return True if the 'cumulativeGas' field has been set, false otherwise.
      */
    public boolean hasCumulativeGas() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'cumulativeGas' field.
      * @return This builder.
      */
    public io.enkrypt.avro.TransactionReceipt.Builder clearCumulativeGas() {
      cumulativeGas = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'bloomFilter' field.
      * @return The value.
      */
    public java.nio.ByteBuffer getBloomFilter() {
      return bloomFilter;
    }

    /**
      * Sets the value of the 'bloomFilter' field.
      * @param value The value of 'bloomFilter'.
      * @return This builder.
      */
    public io.enkrypt.avro.TransactionReceipt.Builder setBloomFilter(java.nio.ByteBuffer value) {
      validate(fields()[2], value);
      this.bloomFilter = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'bloomFilter' field has been set.
      * @return True if the 'bloomFilter' field has been set, false otherwise.
      */
    public boolean hasBloomFilter() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'bloomFilter' field.
      * @return This builder.
      */
    public io.enkrypt.avro.TransactionReceipt.Builder clearBloomFilter() {
      bloomFilter = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'gasUsed' field.
      * @return The value.
      */
    public java.nio.ByteBuffer getGasUsed() {
      return gasUsed;
    }

    /**
      * Sets the value of the 'gasUsed' field.
      * @param value The value of 'gasUsed'.
      * @return This builder.
      */
    public io.enkrypt.avro.TransactionReceipt.Builder setGasUsed(java.nio.ByteBuffer value) {
      validate(fields()[3], value);
      this.gasUsed = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'gasUsed' field has been set.
      * @return True if the 'gasUsed' field has been set, false otherwise.
      */
    public boolean hasGasUsed() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'gasUsed' field.
      * @return This builder.
      */
    public io.enkrypt.avro.TransactionReceipt.Builder clearGasUsed() {
      gasUsed = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'executionResult' field.
      * @return The value.
      */
    public java.nio.ByteBuffer getExecutionResult() {
      return executionResult;
    }

    /**
      * Sets the value of the 'executionResult' field.
      * @param value The value of 'executionResult'.
      * @return This builder.
      */
    public io.enkrypt.avro.TransactionReceipt.Builder setExecutionResult(java.nio.ByteBuffer value) {
      validate(fields()[4], value);
      this.executionResult = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'executionResult' field has been set.
      * @return True if the 'executionResult' field has been set, false otherwise.
      */
    public boolean hasExecutionResult() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'executionResult' field.
      * @return This builder.
      */
    public io.enkrypt.avro.TransactionReceipt.Builder clearExecutionResult() {
      executionResult = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'logs' field.
      * @return The value.
      */
    public java.util.List<io.enkrypt.avro.LogInfo> getLogs() {
      return logs;
    }

    /**
      * Sets the value of the 'logs' field.
      * @param value The value of 'logs'.
      * @return This builder.
      */
    public io.enkrypt.avro.TransactionReceipt.Builder setLogs(java.util.List<io.enkrypt.avro.LogInfo> value) {
      validate(fields()[5], value);
      this.logs = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'logs' field has been set.
      * @return True if the 'logs' field has been set, false otherwise.
      */
    public boolean hasLogs() {
      return fieldSetFlags()[5];
    }


    /**
      * Clears the value of the 'logs' field.
      * @return This builder.
      */
    public io.enkrypt.avro.TransactionReceipt.Builder clearLogs() {
      logs = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    /**
      * Gets the value of the 'error' field.
      * @return The value.
      */
    public java.lang.CharSequence getError() {
      return error;
    }

    /**
      * Sets the value of the 'error' field.
      * @param value The value of 'error'.
      * @return This builder.
      */
    public io.enkrypt.avro.TransactionReceipt.Builder setError(java.lang.CharSequence value) {
      validate(fields()[6], value);
      this.error = value;
      fieldSetFlags()[6] = true;
      return this;
    }

    /**
      * Checks whether the 'error' field has been set.
      * @return True if the 'error' field has been set, false otherwise.
      */
    public boolean hasError() {
      return fieldSetFlags()[6];
    }


    /**
      * Clears the value of the 'error' field.
      * @return This builder.
      */
    public io.enkrypt.avro.TransactionReceipt.Builder clearError() {
      error = null;
      fieldSetFlags()[6] = false;
      return this;
    }

    /**
      * Gets the value of the 'txHash' field.
      * @return The value.
      */
    public java.nio.ByteBuffer getTxHash() {
      return txHash;
    }

    /**
      * Sets the value of the 'txHash' field.
      * @param value The value of 'txHash'.
      * @return This builder.
      */
    public io.enkrypt.avro.TransactionReceipt.Builder setTxHash(java.nio.ByteBuffer value) {
      validate(fields()[7], value);
      this.txHash = value;
      fieldSetFlags()[7] = true;
      return this;
    }

    /**
      * Checks whether the 'txHash' field has been set.
      * @return True if the 'txHash' field has been set, false otherwise.
      */
    public boolean hasTxHash() {
      return fieldSetFlags()[7];
    }


    /**
      * Clears the value of the 'txHash' field.
      * @return This builder.
      */
    public io.enkrypt.avro.TransactionReceipt.Builder clearTxHash() {
      txHash = null;
      fieldSetFlags()[7] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public TransactionReceipt build() {
      try {
        TransactionReceipt record = new TransactionReceipt();
        record.postTxState = fieldSetFlags()[0] ? this.postTxState : (java.nio.ByteBuffer) defaultValue(fields()[0]);
        record.cumulativeGas = fieldSetFlags()[1] ? this.cumulativeGas : (java.nio.ByteBuffer) defaultValue(fields()[1]);
        record.bloomFilter = fieldSetFlags()[2] ? this.bloomFilter : (java.nio.ByteBuffer) defaultValue(fields()[2]);
        record.gasUsed = fieldSetFlags()[3] ? this.gasUsed : (java.nio.ByteBuffer) defaultValue(fields()[3]);
        record.executionResult = fieldSetFlags()[4] ? this.executionResult : (java.nio.ByteBuffer) defaultValue(fields()[4]);
        record.logs = fieldSetFlags()[5] ? this.logs : (java.util.List<io.enkrypt.avro.LogInfo>) defaultValue(fields()[5]);
        record.error = fieldSetFlags()[6] ? this.error : (java.lang.CharSequence) defaultValue(fields()[6]);
        record.txHash = fieldSetFlags()[7] ? this.txHash : (java.nio.ByteBuffer) defaultValue(fields()[7]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<TransactionReceipt>
    WRITER$ = (org.apache.avro.io.DatumWriter<TransactionReceipt>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<TransactionReceipt>
    READER$ = (org.apache.avro.io.DatumReader<TransactionReceipt>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
