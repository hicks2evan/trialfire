package hicks2evan.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import hicks2evan.web.rest.TestUtil;

public class TrialTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Trial.class);
        Trial trial1 = new Trial();
        trial1.setId(1L);
        Trial trial2 = new Trial();
        trial2.setId(trial1.getId());
        assertThat(trial1).isEqualTo(trial2);
        trial2.setId(2L);
        assertThat(trial1).isNotEqualTo(trial2);
        trial1.setId(null);
        assertThat(trial1).isNotEqualTo(trial2);
    }
}
