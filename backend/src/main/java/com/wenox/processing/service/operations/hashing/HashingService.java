package com.wenox.processing.service.operations.hashing;

import com.wenox.anonymisation.domain.Hashing;
import com.wenox.anonymisation.domain.HashingMode;
import com.wenox.processing.domain.Pair;
import com.wenox.processing.service.operations.AnonymisationService;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.bouncycastle.util.encoders.Hex;

public class HashingService implements AnonymisationService<Hashing> {

  private final Keccak.Digest256 digestSha3 = new Keccak.Digest256();
  private static MessageDigest digestSha2;

  static {
    try {
      digestSha2 = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean altersTypeToText(String type) {
    return !String.valueOf(Types.VARCHAR).equals(type);
  }

  @Override
  public List<Pair<String, String>> anonymise(List<Pair<String, String>> rows, Hashing hashing) {
    List<String> unhashedValues = rows.stream().map(Pair::getSecond).toList();

    List<String> hashedValues = new ArrayList<>();
    for (var value : unhashedValues) {
      if (value == null) {
        hashedValues.add(null);
        continue;
      }
      byte[] bytes;
      if (hashing.getHashingMode() == HashingMode.SHA3) {
        bytes = digestSha3.digest(value.getBytes(StandardCharsets.UTF_8));
      } else {
        bytes = digestSha2.digest(value.getBytes(StandardCharsets.UTF_8));
      }
      String hashed = new String(Hex.encode(bytes));
      hashedValues.add(hashed);
    }

    return IntStream.range(0, rows.size())
        .mapToObj(i -> Pair.of(rows.get(i).getFirst(), hashedValues.get(i)))
        .toList();
  }
}