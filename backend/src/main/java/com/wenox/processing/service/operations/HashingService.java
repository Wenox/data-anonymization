package com.wenox.processing.service.operations;

import com.wenox.anonymisation.domain.Hashing;
import com.wenox.processing.domain.Pair;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.bouncycastle.util.encoders.Hex;

public class HashingService {

  private final Keccak.Digest256 digest256 = new Keccak.Digest256();

  public List<Pair<String, String>> hash(List<Pair<String, String>> rows, Hashing hashing) {
    List<String> unhashedValues = rows.stream().map(Pair::getSecond).toList();

    List<String> hashedValues = new ArrayList<>();
    for (var value : unhashedValues) {
      if (value == null) {
        hashedValues.add(null);
        continue;
      }
      String hashed = new String(Hex.encode(digest256.digest(value.getBytes(StandardCharsets.UTF_8))));
      hashedValues.add(hashed);
    }

    return IntStream.range(0, rows.size())
        .mapToObj(i -> Pair.of(rows.get(i).getFirst(), hashedValues.get(i)))
        .toList();
  }
}