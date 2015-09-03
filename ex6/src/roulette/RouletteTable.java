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
	private static final int ROLLING = -1;
	private static final int CUT_OFF_TIME = 10000;
	private Map<Field,List<Bet>> betsByFields=new HashMap<Field,List<Bet>>();
	private int maxChipsOnTable;
	private RandomNumberGenerator rng;
	private int ballPosition=0;
	private Map<Player, Colour> players=new HashMap<Player,Colour>();
	private Set<Player> waitingForPlayersToComplete=new HashSet<Player>();
	private Timer timer;
	private long ballStartedRolling=0;
	public void placeBet(Player p,Field field, int value) throws TooManyChipsException, TableFullException, NoMoreBetsException {
		placeBet(p,new Field[]{field}, value);
	}
	public void placeBet(Player p, Field fields[], int value) throws TooManyChipsException, TableFullException, NoMoreBetsException {
		if (currentChipsOnTable()+value>maxChipsOnTable) throw new TooManyChipsException();
		if (!players.keySet().contains(p) && players.size()==MAX_PLAYERS) throw new TableFullException();
		if (isBallRolling() && (timer.getTimeInMillis()-ballStartedRolling>CUT_OFF_TIME)) throw new NoMoreBetsException();
		players.put(p,Colour.values()[players.size()]);
		for (Field field:fields){
			if (betsByFields.get(field)==null){
				betsByFields.put(field, new ArrayList<Bet>());
			}
			betsByFields.get(field).add(new Bet(p,value, getBetType(fields)));
		}
		waitingForPlayersToComplete.add(p);
	}
	private BetType getBetType(Field[] fields) {
		if (fields.length==1) return BetType.SINGLE;
		if (fields.length==2) return BetType.SPLIT;
		throw new IllegalArgumentException("Unsupported bet type for "+ fields + " fields");
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
	public List<Bet> betsByField(Field field) {
		if (betsByFields.get(field)==null) return EMPTY;
		return betsByFields.get(field);
	}
	public RouletteTable(int maxChipsOnTable, RandomNumberGenerator rng, Timer timer) {
		this.maxChipsOnTable=maxChipsOnTable;
		this.rng=rng;
		this.timer=timer;
	}
	public Colour getColour(Player player) {
		return players.get(player);
	}
	public void done(Player p) {
		waitingForPlayersToComplete.remove(p);
		if (waitingForPlayersToComplete.size()==0) roll();
	}
	void roll() {
		int timeToRoll=rng.generate(30, 40);
		ballPosition=ROLLING;
		ballStartedRolling=timer.getTimeInMillis();
		timer.callBack(timeToRoll*1000, new Runnable(){
			public void run(){
				stopRolling();
			}
		});
		
	}
	public Object getBallPosition() {
		return ballPosition;
	}
	public Set<Bet> getWinningBets() {
		Set<Bet> winningBets=new HashSet<Bet>();
		for (Field f: betsByFields.keySet()){
			if (f.winningStrategy().winsOn(ballPosition)){
				winningBets.addAll(betsByField(f));
			}
		}
		return winningBets;
	}
	public void stopRolling() {
		ballPosition=rng.generate(0, 36);		
	}
	public boolean isBallRolling() {
		return ballPosition==ROLLING;
	}
	
	
}
