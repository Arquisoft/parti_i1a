package uniovi.asw.persistence.model.types;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Topic {
    ENVIROMENT, POLITICS, SPORTS, HEALTHCARE, SECURITY;

    private static final List<Topic> TOPICS = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = TOPICS.size();
    private static final Random RANDOM = new Random();

    public static Topic randomTopic() {
	return TOPICS.get(RANDOM.nextInt(SIZE));
    }
}
