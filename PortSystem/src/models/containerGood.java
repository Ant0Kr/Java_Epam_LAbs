/*
 * 
 */
package models;

// TODO: Auto-generated Javadoc
/**
 * The Class containerGood.
 */
@SuppressWarnings("unused")
public class containerGood {

	/** The good name. */
	private String goodName;
	
	/** The good count. */
	private int goodCount;

	/**
	 * Instantiates a new container good.
	 *
	 * @param name the name
	 * @param count the count
	 */
	public containerGood(String name, int count) {
		goodName = name;
		goodCount = count;
	}

	/**
	 * Gets the good name.
	 *
	 * @return the good name
	 */
	public String getGoodName() {
		return goodName;
	}

	/**
	 * Gets the good count.
	 *
	 * @return the good count
	 */
	public int getGoodCount() {
		return goodCount;
	}
}
