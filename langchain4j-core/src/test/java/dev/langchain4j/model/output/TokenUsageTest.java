package dev.langchain4j.model.output;

import static dev.langchain4j.model.output.TokenUsage.sum;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

class TokenUsageTest implements WithAssertions {
    @Test
    void constructors() {
        assertThat(new TokenUsage()).isEqualTo(new TokenUsage(null, null, null));

        assertThat(new TokenUsage(1)).isEqualTo(new TokenUsage(1, null, 1));

        assertThat(new TokenUsage(1, 2)).isEqualTo(new TokenUsage(1, 2, 3));

        assertThat(new TokenUsage(1, 2, 3)).isEqualTo(new TokenUsage(1, 2, 3));
    }

    @Test
    void accessors() {
        {
            TokenUsage tu = new TokenUsage(1, 2, 3);
            assertThat(tu.inputTokenCount()).isEqualTo(1);
            assertThat(tu.outputTokenCount()).isEqualTo(2);
            assertThat(tu.totalTokenCount()).isEqualTo(3);
        }
        {
            TokenUsage tu = new TokenUsage(null, null, null);
            assertThat(tu.inputTokenCount()).isNull();
            assertThat(tu.outputTokenCount()).isNull();
            assertThat(tu.totalTokenCount()).isNull();
        }
    }

    @Test
    void equals_hash() {
        TokenUsage tu1 = new TokenUsage(1, 2, 3);
        TokenUsage tu2 = new TokenUsage(1, 2, 3);

        assertThat(tu1)
                .isEqualTo(tu1)
                .isNotEqualTo(null)
                .isNotEqualTo(new Object())
                .isEqualTo(tu2)
                .hasSameHashCodeAs(tu2);

        assertThat(new TokenUsage(null, 2, 3)).isNotEqualTo(tu1).doesNotHaveSameHashCodeAs(tu1);

        assertThat(new TokenUsage(1, null, 3)).isNotEqualTo(tu1).doesNotHaveSameHashCodeAs(tu1);

        assertThat(new TokenUsage(1, 2, null)).isNotEqualTo(tu1).doesNotHaveSameHashCodeAs(tu1);
    }

    @Test
    void to_string() {
        assertThat(new TokenUsage(1, 2, 3))
                .hasToString("TokenUsage { inputTokenCount = 1, outputTokenCount = 2, totalTokenCount = 3 }");
        assertThat(new TokenUsage(null, null, null))
                .hasToString("TokenUsage { inputTokenCount = null, outputTokenCount = null, totalTokenCount = null }");
    }

    @Test
    void test_sum() {
        assertThat(sum(new TokenUsage(1, 2, 3), new TokenUsage(4, 5, 6))).isEqualTo(new TokenUsage(5, 7, 9));

        assertThat(sum(new TokenUsage(1, 2, 3), new TokenUsage(null, null, null)))
                .isEqualTo(new TokenUsage(1, 2, 3));

        assertThat(sum(new TokenUsage(null, null, null), new TokenUsage(4, 5, 6)))
                .isEqualTo(new TokenUsage(4, 5, 6));

        assertThat(sum(new TokenUsage(null, null, null), new TokenUsage(null, null, null)))
                .isEqualTo(new TokenUsage(null, null, null));

        assertThat(sum(new TokenUsage(1, 2, 3), null)).isEqualTo(new TokenUsage(1, 2, 3));

        assertThat(sum(null, new TokenUsage(4, 5, 6))).isEqualTo(new TokenUsage(4, 5, 6));
    }
}
