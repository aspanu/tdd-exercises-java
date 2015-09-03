package roulette;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RouletteTable {
	private static final List<Bet> EMPTY = Collections.unmodifiableList(new ArrayList<Bet>());
	private static final int MAX_PLAYERS = 8;
	private Map<Integer,List<Bet>> betsByFields=new HashMap<Integer,List<Bet>>();
	private int maxChipsOnTable;
	
	private Map<Player, Colour> players=new HashMap<Player,Colour>();
	public void placeBet(Player p, int field, int value) throws TooManyChipsException, TableFullException {
		if (currentChipsOnTable()+value>maxChipsOnTable) throw new TooManyChipsException();
		if (!players.keySet().contains(p) && players.size()==MAX_PLAYERS) throw new TableFullException();
		players.put(p,Colour.values()[players.size()]);
		if (betsByFields.get(field)==null){
			betsByFields.put(field, new ArrayList<Bet>());
		}
		betsByFields.get(field).add(new Bet(p,value));
	}
	private int currentChipsOnTable() {
		int total=0;
		for (List<Bet> bets:betsByFields.values()){
			for (Bet bet:bets){
				total=total+bet.getValue();
			}
		}
		return total;
	}
	public List<Bet> betsByField(int field) {
		if (betsByFields.get(field)==null) return EMPTY;
		return betsByFields.get(field);
	}
	public RouletteTable(int maxChipsOnTable) {
		this.maxChipsOnTable=maxChipsOnTable;
	}
	public Colour getColour(Player player) {
		return players.get(player);
	}

}
