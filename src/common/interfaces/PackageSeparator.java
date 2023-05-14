package common.interfaces;

import java.util.Arrays;
import java.util.List;

public interface PackageSeparator {
    int BUFFER = 2048;

    default byte[][] split(byte[] data) {
        int chunkSize = BUFFER;
        final int length = data.length;
        final byte[][] dest = new byte[(length + chunkSize - 1) / chunkSize][];
        int destIndex = 0;
        int stopIndex = 0;

        for (int startIndex = 0; startIndex + chunkSize <= length; startIndex += chunkSize) {
            stopIndex += chunkSize;
            dest[destIndex++] = Arrays.copyOfRange(data, startIndex, stopIndex);
        }

        if (stopIndex < length)
            dest[destIndex] = Arrays.copyOfRange(data, stopIndex, length);
        return dest;
    }


    default byte[] merge(List<byte[]> data) {
        byte[] res = new byte[data.size() * BUFFER];
        int curpos = 0;
        for (byte[] x : data) {
            for (byte j : x) {
                res[curpos] = j;
                curpos++;
            }
        }
        return res;
    }
}
