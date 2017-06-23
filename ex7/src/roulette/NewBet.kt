package roulette

/**
 * Created by eapopei on 23.06.17.
 */

data class NewBet (val type : BetType, val value: Int, val owner : Player, val locations : Set<Int>)
