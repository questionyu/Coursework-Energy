/**
 * Title        IDInterface.java
 * Description  IDGenerator interface.
 */
interface IDInterface {
	/**
	 * This function generates a random ID.
	 *
	 * @param length ID's length.
	 * @return The generated random ID.
	 */
	long get(int length);
}
