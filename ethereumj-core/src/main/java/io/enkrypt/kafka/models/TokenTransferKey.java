package io.enkrypt.kafka.models;

import org.ethereum.util.RLPList;

import static org.ethereum.util.ByteUtil.toHexString;
import static org.ethereum.util.RLP.*;

public class TokenTransferKey {

  private final byte[] txHash;

  private final int logIdx;

  public TokenTransferKey(byte[] txHash, int logIdx) {
    this.txHash = txHash;
    this.logIdx = logIdx;
  }

  public TokenTransferKey(byte[] rlpEncoded) {
    final RLPList list = unwrapList(rlpEncoded);

    this.txHash = list.get(0).getRLPData();
    this.logIdx = decodeInt(list.get(1).getRLPData(), 0);
  }

  public byte[] getTxHash() {
    return txHash;
  }

  public int getLogIdx() {
    return logIdx;
  }

  public byte[] getEncoded() {
    return encodeList(
      encodeElement(txHash),
      encodeElement(encodeInt(logIdx))
    );
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("TokenTransferKey{");
    sb.append("txHash=").append(toHexString(txHash));
    sb.append(", logIdx=").append(logIdx);
    sb.append('}');
    return sb.toString();
  }
}
