package lotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomUniqueNumbersInRangeTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoTest {
    @DisplayName("로또 번호의 개수가 6개가 넘어가면 예외가 발생한다.")
    @Test
    void 로또_번호의_개수가_6개가_넘어가면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 6, 7)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    @Test
    void 로또_번호에_중복된_숫자가_있으면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호가 범위를 벗어나면 예외가 발생한다.")
    @Test
    void rangeCheck() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호는 오름차순으로 정렬돼야한다.")
    @Test
    void ascendingCheck() {
        assertSimpleTest(() -> {
            Lotto lotto = new Lotto(List.of(6, 5, 4, 3, 2, 1));
            assertThat(lotto.getNumbers()).containsExactly(1, 2, 3, 4, 5, 6);
        });
    }

    @DisplayName("로또 번호는 무작위 수를 가질 수 있어야한다.")
    @Test
    void randomCheck() {
        assertRandomUniqueNumbersInRangeTest(
                () -> {
                    Lotto lotto = new Lotto();
                    assertThat(lotto.getNumbers()).containsExactly(3, 4, 9, 21, 29, 33);
                },
                List.of(3, 4, 9, 21, 29, 33)
        );
    }
}
