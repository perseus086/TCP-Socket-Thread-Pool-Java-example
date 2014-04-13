/**
 * @author Paul Hinojosa, paulhl, 601499
 */

//Needs RitaWordnet jar
import rita.wordnet.RiWordnet;

public class Search{
	
	private Definition definition = new Definition();
	//Create an object wordnet from RitaWordnet
	
	private RiWordnet wordnet = new RiWordnet();
	
	public Definition searchFor(String word)
	{
		try {
			String pos = wordnet.getBestPos(word);
			definition.setDefinition(wordnet.getAllGlosses(word, pos));
			definition.setStatus(1); //1=definition found
		} catch (Exception e) {
			definition.setStatus(0); //0=definition not found
			definition.setDefinition(null);
		}
		return definition;
	}	
}