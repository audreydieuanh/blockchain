import java.util.Arrays;

/**
 * Wrapper class for byte[] along with operations for hashing
 *
 * @author Audrey Trinh, Jinny Eo
 */

public class Hash {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+
  byte[] hash;

  // +-------------+--------------------------------------------------
  // | Constructor |
  // +-------------+

  /**
   * Create a new hash contained the given data
   */
  public Hash(byte[] data) {
    this.hash = Arrays.copyOf(data, data.length);
  } // constructor

  // +---------+------------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Returns the hash contained in this object
   */
  public byte[] getData() {
    return this.hash;
  } // getData

  /**
   * Return true if this hash meets the criteria for validity
   * (its first three indices contain zeroes)
   */
  public boolean isValid() {
    return this.hash[0] == 0 && this.hash[1] == 0
            && this.hash[2] == 0;
  } // isValid

  /**
   * Returns the string representation of the hash as a string
   * of hexadecimal digits, 2 digits per byte
   */
  public String toString() {
    // create new int[] with same size as this.hash
    int[] intArr = new int[this.hash.length];
    StringBuilder str = new StringBuilder();

    // for loop
    for (int i = 0; i < this.hash.length; i++) {
      // change byte to unsigned int
      intArr[i] = Byte.toUnsignedInt(this.hash[i]);
      if (intArr[i] < 16) {
        // add 0 to the left printing for one-digit number
        str.append(String.format("0%x", intArr[i]));
      } else {
        str.append(String.format("%x", intArr[i]));
      }
    } // for loop
    return str.toString();
  } // toString

  /**
   * return trues if this hash is structurally equal to the argument
   */
  public boolean equals(Object other) {
    // if other is an instance of Hash
    if (other instanceof Hash) {
      // cast other to type Hash
      Hash o = (Hash) other;
      return Arrays.equals(o.getData(), this.hash);
    } else {
      return false;
    }
  } // equals
}
