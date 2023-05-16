package lotto.domain;

import java.util.Arrays;
import java.util.function.BiFunction;

public enum Rank {
    FIRST((matchCount, bonus) -> matchCount == 6, 2_000_000_000),
    SECOND((matchCount, bonus) -> matchCount == 5 && bonus, 30_000_000),
    THIRD((matchCount, bonus) -> matchCount == 5 && !bonus, 1_500_000),
    FOURTH((matchCount, bonus) -> matchCount == 4, 50_000),
    FIFTH((matchCount, bonus) -> matchCount == 3, 5000),
    LOSE((matchCount, bonus) -> matchCount == 0, 0);

    private final BiFunction<Integer, Boolean, Boolean> matchFunction;
    private final long prize;

    Rank(BiFunction<Integer, Boolean, Boolean> matchFunction, long prize) {
        this.matchFunction = matchFunction;
        this.prize = prize;
    }

    public static Rank of(Lotto lotto, WinNumbers winNumbers) {
        return Arrays.stream(Rank.values())
                .filter(rank -> rank.matchFunction.apply(winNumbers.getMatchCount(lotto), winNumbers.isBonusIncludedIn(lotto)))
                .findFirst()
                .orElse(Rank.LOSE);
    }

    public long getPrize() {
        return this.prize;
    }
}
