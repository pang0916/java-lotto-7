package lotto;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomUniqueNumbersInRangeTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationTest extends NsTest {
    private static final String ERROR_MESSAGE = "[ERROR]";

    @DisplayName("전체 기능 테스트")
    @Test
    void 기능_테스트() {
        assertRandomUniqueNumbersInRangeTest(
                () -> {
                    run("8000", "1,2,3,4,5,6", "7");
                    assertThat(output()).contains(
                            "8개를 구매했습니다.",
                            "[8, 21, 23, 41, 42, 43]",
                            "[3, 5, 11, 16, 32, 38]",
                            "[7, 11, 16, 35, 36, 44]",
                            "[1, 8, 11, 31, 41, 42]",
                            "[13, 14, 16, 38, 42, 45]",
                            "[7, 11, 30, 40, 42, 43]",
                            "[2, 13, 22, 32, 38, 45]",
                            "[1, 3, 5, 14, 22, 45]",
                            "3개 일치 (5,000원) - 1개",
                            "4개 일치 (50,000원) - 0개",
                            "5개 일치 (1,500,000원) - 0개",
                            "5개 일치, 보너스 볼 일치 (30,000,000원) - 0개",
                            "6개 일치 (2,000,000,000원) - 0개",
                            "총 수익률은 62.5%입니다."
                    );
                },
                List.of(8, 21, 23, 41, 42, 43),
                List.of(3, 5, 11, 16, 32, 38),
                List.of(7, 11, 16, 35, 36, 44),
                List.of(1, 8, 11, 31, 41, 42),
                List.of(13, 14, 16, 38, 42, 45),
                List.of(7, 11, 30, 40, 42, 43),
                List.of(2, 13, 22, 32, 38, 45),
                List.of(1, 3, 5, 14, 22, 45)
        );
    }

    @DisplayName("구매 금액이 숫자가 아니면 예외가 발생한다.")
    @Test
    void 예외_테스트() {
        assertSimpleTest(() -> {
            runException("1000j");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @DisplayName("구매 금액이 1000원 단위가 아니면 예외가 발생한다.")
    @Test
    void budgetUnitTest() {
        assertSimpleTest(() -> {
            runException("1500");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @DisplayName("구매 금액이 int 범위를 벗어나면 예외가 발생한다.")
    @Test
    void budgetOverflowTest() {
        assertSimpleTest(() -> {
            runException("2200000000");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @DisplayName("구매 금액이 음수면 예외가 발생한다.")
    @Test
    void negativeIntegerTest() {
        assertSimpleTest(() -> {
            runException("-300");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @DisplayName("구매 갯수 및 내역 출력 테스트.")
    @Test
    void lottoPickerUnitTest() {
        assertRandomUniqueNumbersInRangeTest(
                () -> {
                    run("8000", "1,2,3,4,5,6", "7");
                    assertThat(output()).contains(
                            "8개를 구매했습니다.",
                            "[8, 21, 23, 41, 42, 43]",
                            "[3, 5, 11, 16, 32, 38]",
                            "[7, 11, 16, 35, 36, 44]",
                            "[1, 8, 11, 31, 41, 42]",
                            "[13, 14, 16, 38, 42, 45]",
                            "[7, 11, 30, 40, 42, 43]",
                            "[2, 13, 22, 32, 38, 45]",
                            "[1, 3, 5, 14, 22, 45]"
                    );
                },
                List.of(8, 21, 23, 41, 42, 43),
                List.of(3, 5, 11, 16, 32, 38),
                List.of(7, 11, 16, 35, 36, 44),
                List.of(1, 8, 11, 31, 41, 42),
                List.of(13, 14, 16, 38, 42, 45),
                List.of(7, 11, 30, 40, 42, 43),
                List.of(2, 13, 22, 32, 38, 45),
                List.of(1, 3, 5, 14, 22, 45)
        );
    }

    @DisplayName("당첨 번호 중복 테스트")
    @Test
    void winNumberTest1() {
        assertSimpleTest(() -> {
            runException("8000", "1, 2, 3, 4, 5, 5", "7");
            assertThat(output()).contains("[ERROR] 로또 번호는 중복되지 않아야 합니다.");
        });
    }

    @DisplayName("당첨 번호 부족 테스트")
    @Test
    void winNumberTest2() {
        assertSimpleTest(() -> {
            runException("8000", "1, 2, 3, 4, 5", "7");
            assertThat(output()).contains("[ERROR] 로또 번호는 6개여야 합니다.");
        });
    }

    @DisplayName("당첨 번호 범위 테스트")
    @Test
    void winNumberTest3() {
        assertSimpleTest(() -> {
            runException("8000", "1, 2, 3, 4, 5, 46", "7");
            assertThat(output()).contains("[ERROR] 로또 번호는 1에서 45 사이의 정수여야 합니다.");
        });
    }

    @DisplayName("당첨 번호 숫자 테스트")
    @Test
    void winNumberTest4() {
        assertSimpleTest(() -> {
            runException("8000", "1, 2, 3, 4, 5, a", "7");
            assertThat(output()).contains("[ERROR] 로또 번호는 45 이하의 숫자여야 합니다.");
        });
    }

    @DisplayName("보너스 번호 중복 테스트")
    @Test
    void bonusNumberTest() {
        assertSimpleTest(() -> {
            runException("8000", "1, 2, 3, 4, 5, 6", "6");
            assertThat(output()).contains("[ERROR] 보너스 번호는 당첨 번호와 중복되지 않아야 합니다.");
        });
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
