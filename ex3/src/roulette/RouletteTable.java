package roulette;

import java.util.*;

public class RouletteTable {
    private static final List<Bet> EMPTY = Collections.unmodifiableList(new ArrayList<Bet>());
    private static final int MAX_PLAYERS = 8;
    private Map<Integer, List<Bet>> betsByFields = new HashMap<>();
    private Map<TypeLocationIndex, List<Bet>> betsByTypeLocations = new HashMap<>();
    private int maxChipsOnTable;

    private Map<Player, Colour> players = new HashMap<>();


    public void placeBet(Player p, int field, int value) throws TooManyChipsException, TableFullException {
        betPlacingValidation(p, value);
        if (betsByFields.get(field) == null) {
            betsByFields.put(field, new ArrayList<>());
        }
        betsByFields.get(field).add(new Bet(p, value));
    }

    private void betPlacingValidation(Player p, int value) throws TooManyChipsException, TableFullException {
        if (currentChipsOnTable() + value > maxChipsOnTable) throw new TooManyChipsException();
        if (!players.keySet().contains(p) && players.size() == MAX_PLAYERS) throw new TableFullException();
        if (!players.keySet().contains(p)) players.put(p, Colour.values()[players.size()]);
    }

    public void placeBet(Player p, Category c, int value) throws TooManyChipsException, TableFullException {
        placeBet(p, c.getFieldValue(), value);
    }

    private int currentChipsOnTable() {
        int total = 0;
        for (List<Bet> bets : betsByFields.values()) {
            for (Bet bet : bets) {
                total = total + bet.getValue();
            }
        }
        return total;
    }

    public List<Bet> betsByField(int field) {
        if (betsByFields.get(field) == null) return EMPTY;
        return betsByFields.get(field);
    }

    public RouletteTable(int maxChipsOnTable) {
        this.maxChipsOnTable = maxChipsOnTable;
    }

    public Colour getColour(Player player) {
        return players.get(player);
    }

    public List<Bet> betsByField(Category category) {
        return betsByField(category.getFieldValue());
    }

    public void placeBet(Player p, Integer[] splitLocations, int value) throws TooManyChipsException, TableFullException {
        betPlacingValidation(p, value);

        TypeLocationIndex index = new TypeLocationIndex(Category.SPLIT, new HashSet<>(Arrays.asList(splitLocations)));
        if (betsByTypeLocations.get(index) == null) {
            betsByTypeLocations.put(index, new ArrayList<>());
        }
        betsByFields.get(index);
        betsByTypeLocations.get(index).add(new Bet(p, value));

    }

    public List<Bet> betsByField(Integer[] splitLocations) {
        TypeLocationIndex index = new TypeLocationIndex(Category.SPLIT, new HashSet<>(Arrays.asList(splitLocations)));
        if (betsByTypeLocations.get(index) == null) return EMPTY;
        return betsByTypeLocations.get(index);
    }
}
