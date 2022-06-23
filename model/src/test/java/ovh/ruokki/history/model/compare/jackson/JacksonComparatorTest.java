package ovh.ruokki.history.model.compare.jackson;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JacksonComparatorTest {
    
    private JacksonComparator jacksonComparator;

    @BeforeEach
    public void init() {
        jacksonComparator = new JacksonComparator();
    }

    @Test
    public void itShouldCompareJson() {
        var json1 ="{\"a\":12}";
        var json2 ="{\"a\":13}";
        
        jacksonComparator.compare(json1, json2);
    }
}
